package net.accademia.dolibarr;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import net.accademia.demone.domain.enumeration.Campi;

/**
 * https://developers.google.com/sheets/api/quickstart/java
 * andare su
 * https://console.cloud.google.com/apis/credentials?hl=IT&project=webinar-330307
 *
 * @author adastra
 *
 */
public abstract class GoogleSheet extends DataSource {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * Global instance of the scopes required by this quickstart. If modifying these
     * scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    protected static String CREDENTIALS_FILE_PATH = null;
    protected static String idclient = null;
    protected static String clientsecret = null;
    protected static String refreshToken = null;

    public static String getNewToken(String refreshToken, String clientId, String clientSecret) throws IOException {
        TokenResponse tokenResponse = new GoogleRefreshTokenRequest(
            new NetHttpTransport(),
            new JacksonFactory(),
            refreshToken,
            clientId,
            clientSecret
        )
            .setScopes(SCOPES)
            .setGrantType("refresh_token")
            .execute();

        return tokenResponse.getAccessToken();
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    /**
     * @param HTTP_TRANSPORT
     * @return
     * @throws IOException
     */
    static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleSheet.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        idclient = clientSecrets.getDetails().getClientId();
        clientsecret = clientSecrets.getDetails().getClientSecret();
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        refreshToken = credential.getRefreshToken();

        return credential;
    }

    public GoogleSheet(DemoneMediator dm) throws Exception {
        super(dm);
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     *
     * @param formato
     */

    public int getIscritti(String spreadsheetId, int[] formato) {
        if (formato == null) formato = new int[] { 13, 10, 15, 12, 5 };

        if ((spreadsheetId == null) || (dm == null) || (dm.getClienti() == null)) return 0;
        try {
            // Build a new authorized API client service.
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            final String range = "Risposte del modulo 1!A2:S";
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
            ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                return 0;
            }
            int i = 1;
            for (List<?> row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                i++;
                if (row.size() < 15) continue;
                final int mailindex = formato[0];
                Cliente ctemp = new Cliente(
                    (String) row.get(formato[Campi.EMAIL]),
                    (String) row.get(formato[Campi.DENOMINAZIONE]),
                    (String) row.get(formato[Campi.PIVA]),
                    (String) row.get(formato[Campi.CU]),
                    (String) row.get(formato[Campi.TEL]),
                    spreadsheetId + ":" + i
                );
                Cliente c = dm.getClienti().stream().filter(cliente -> cliente.equals(ctemp)).findAny().orElse(null);
                if (c == null) {
                    c = ctemp;
                    dm.getClienti().add(c);
                }

                Contatto co = c
                    .getContatti()
                    .stream()
                    .filter(Contatto -> ((String) row.get(1)).equals(Contatto.getmail()))
                    .findAny()
                    .orElse(null);
                if (co == null) {
                    c
                        .getContatti()
                        .add(
                            new Contatto(
                                (String) row.get(1),
                                (String) row.get(2),
                                (String) row.get(3),
                                (String) row.get(5),
                                c,
                                spreadsheetId + ":" + i
                            )
                        );
                }
            }
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
}
