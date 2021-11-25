package net.accademia.dolibarr;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.Test;

class GoogleSheetTest {

    GoogleSheet gs = null;

    {
        try {
            gs = new AccademiaGoogleSheet(new AccademiaDemoneMediator());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void testgetCredential() {
        // Build a new authorized API client service.
        Credential c = null;
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            c = AccademiaGoogleSheet.getCredentials(HTTP_TRANSPORT);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testgetNewToken() {
        try {
            testgetCredential();
            AccademiaGoogleSheet.getNewToken(
                AccademiaGoogleSheet.refreshToken,
                AccademiaGoogleSheet.idclient,
                AccademiaGoogleSheet.clientsecret
            );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void testGetIscrittiv2() {
        String[] iscrittiv2 = {
            "1MbsoIz64GQb6IuauBfPVW6xciFGfK9Eq7ILfliherQc", // ottobre 2021
        };
        int formato[] = { 14, 11, 15, 13, 5 }; // sono cambiate le colonne del fil e di goobgle
        gs.getIscritti(iscrittiv2[0], formato);
    }

    @Test
    void testGetIscritti() {
        String[] iscritti = { "11ozxzipNGmx5GK2gLXaYFpZOlBMx7KQ30aQsuX74RWA" };

        gs.getIscritti(iscritti[0], null);
    }
}
