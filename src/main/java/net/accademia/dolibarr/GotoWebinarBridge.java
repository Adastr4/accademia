package net.accademia.dolibarr;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.logmein.gotocorelib.api.OAuth2Api;
import com.logmein.gotocorelib.api.model.TokenResponse;
import com.logmein.gotowebinar.api.RegistrantsApi;
import com.logmein.gotowebinar.api.WebinarsApi;
import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.ReportingWebinarsResponse;
import com.logmein.gotowebinar.api.model.Webinar;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public abstract class GotoWebinarBridge extends DataSource {

    protected String GotoWebinarKeyClient = null;

    protected String GotoWebinarKeyClientSecret = null;
    protected String authheader = null;
    protected String refreshtoken = null;
    protected String accesstoken = null;
    RegistrantsApi registrantapi = new RegistrantsApi();
    protected String oaut = null;
    TokenResponse response = null;
    OAuth2Api oauth = null;

    public GotoWebinarBridge(DemoneMediator dm) {
        super(dm);
    }

    /**
     * Andrea 26/10/2021 not implemented yet
     */
    @Deprecated
    void connectGotoWebinar() {
        try {
            OAuth2Api oauth = new OAuth2Api(GotoWebinarKeyClient, GotoWebinarKeyClientSecret);
            String ret = oauth.getOAuth2AuthorisationUrl(GotoWebinarKeyClient);
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getCache().setMaxSize(0);
            webClient.getOptions().setRedirectEnabled(true);
            HtmlPage page = webClient.getPage(ret);

            URL url = page.getUrl();
            List<NameValuePair> params = URLEncodedUtils.parse(url.toURI(), Charset.forName("UTF-8"));
            for (NameValuePair nameValuePair : params) {
                if (!nameValuePair.getName().equalsIgnoreCase("service")) continue;
                page = webClient.getPage(nameValuePair.getValue());
            }

            url = page.getUrl();
            page = webClient.getPage(url);
            url = page.getUrl();
            page = webClient.getPage(url);
            url = page.getUrl();
            System.out.println(url);
            String responseKey = oauth.getResponseKey(url); // => "a2647d1379894cc2001eb31689cacccc"

            TokenResponse response = oauth.getAccessTokenResponse(responseKey);
            String accessToken = response.getAccessToken(); // => "RlUe11faKeyCWxZToK3nk0uTKAL"

            System.out.println(ret);
            webClient.close();
        } catch (FailingHttpStatusCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (com.logmein.gotocorelib.api.common.ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Registrant> getAllRegistrantsForWebinar(String WebinarKey) throws ApiException {
        List<Registrant> registrants = registrantapi.getAllRegistrantsForWebinar(
            accesstoken,
            8348404185963954557L,
            Long.parseLong(WebinarKey)
        );

        return registrants;
    }

    public int getIscritti() {
        getToken();
        Calendar myCalendar = new GregorianCalendar(2021, 1, 1);
        Date da = myCalendar.getTime(); // "2020-03-13T10:00:00Z"
        Date a = new GregorianCalendar(2021, 12, 1).getTime();
        Cliente cl = dm.getMe();
        dm.getClienti().add(cl);
        try {
            WebinarsApi webinarapi = new WebinarsApi();

            ReportingWebinarsResponse webinar = webinarapi.getWebinars(accesstoken, 8348404185963954557L, da, a, 0L, 200L);
            int i = 0;
            for (Webinar m : webinar.getEmbedded().getWebinars()) {
                i = 0;
                for (Registrant registrant : getAllRegistrantsForWebinar(m.getWebinarKey())) {
                    i++;
                    Contatto c = dm.searchContattofromCliente(
                        registrant.getEmail(),
                        registrant.getLastName(),
                        registrant.getFirstName(),
                        "webinar:" + m.getWebinarKey() + ":" + i
                    );
                    if (c == null) {
                        c =
                            new Contatto(
                                registrant.getEmail(),
                                registrant.getFirstName(),
                                registrant.getLastName(),
                                "",
                                cl,
                                m.getWebinarKey() + ":" + i
                            );
                        cl.getContatti().add(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    void getAccess() {
        try {
            oauth = new OAuth2Api(GotoWebinarKeyClient, GotoWebinarKeyClientSecret);
            String url = oauth.getOAuth2AuthorisationUrl(GotoWebinarKeyClient);
            response = oauth.getAccessTokenResponse(accesstoken);
        } catch (com.logmein.gotocorelib.api.common.ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void getToken() {
        try {
            oauth = new OAuth2Api(GotoWebinarKeyClient, GotoWebinarKeyClientSecret);
            response = oauth.getAccessTokenUsingRefreshToken(refreshtoken);
            accesstoken = response.getAccessToken();
        } catch (com.logmein.gotocorelib.api.common.ApiException e) {
            e.printStackTrace();
        }
    }

    public int getWebinars() {
        getToken();
        Calendar myCalendar = new GregorianCalendar(2020, 1, 1);
        Date da = myCalendar.getTime(); // "2020-03-13T10:00:00Z"
        Date a = new GregorianCalendar(2021, 12, 1).getTime();

        try {
            WebinarsApi webinarapi = new WebinarsApi();

            ReportingWebinarsResponse webinar = webinarapi.getWebinars(accesstoken, 8348404185963954557L, da, a, 0L, 200L);

            for (Webinar m : webinar.getEmbedded().getWebinars()) {
                (((IWebinarMediator) dm).getWebinars()).add(m);
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param input
     * @param email
     */
    // TODO non implemetata
    private void insertContact(String input, String email) {}

    /**
     * Inserisce gli iscritti su dolibarr, deve poi associare l'azienda a ciascun
     * iscritto
     *
     * @return
     */
    public int insertContacts() {
        try {
            Calendar myCalendar = new GregorianCalendar(2021, 1, 1);
            Date da = myCalendar.getTime(); // "2020-03-13T10:00:00Z"
            Date a = new GregorianCalendar(2021, 12, 1).getTime();

            RegistrantsApi registrantapi = new RegistrantsApi();
            WebinarsApi webinarapi = new WebinarsApi();

            ReportingWebinarsResponse webinar = webinarapi.getWebinars(accesstoken, 8348404185963954557L, da, a, 0L, 200L);

            String input = null;

            for (Webinar m : webinar.getEmbedded().getWebinars()) {
                List<Registrant> registrants = registrantapi.getAllRegistrantsForWebinar(
                    accesstoken,
                    8348404185963954557L,
                    Long.parseLong(m.getWebinarKey())
                );

                for (Registrant registrant : registrants) {
                    input =
                        "{\"lastname\":\"" +
                        registrant.getLastName() +
                        "\", \"firstname\":\"" +
                        registrant.getFirstName() +
                        "\", \"email\":\"" +
                        registrant.getEmail() +
                        "\"}";
                    insertContact(input, registrant.getEmail());
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
