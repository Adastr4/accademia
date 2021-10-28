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

public class GotoWebinarBridge extends DataSource {

    public GotoWebinarBridge(AccademiaDemoneMediator dm) {
        super(dm);
        // TODO Auto-generated constructor stub
    }

    String GotoWebinarKeyClient = "6d8b54b0-787c-418c-b20a-e1009247d124";
    String GotoWebinarKeyClientSecret = "5mbvleugSuVgAg46qSrA0ueQ";
    String authheader = "NmQ4YjU0YjAtNzg3Yy00MThjLWIyMGEtZTEwMDkyNDdkMTI0OjVtYnZsZXVnU3VWZ0FnNDZxU3JBMHVlUQ==";
    // String refreshtoken =
    // "eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsImF1ZCI6IjZkOGI1NGIwLTc4N2MtNDE4Yy1iMjBhLWUxMDA5MjQ3ZDEyNCIsInN1YiI6Ijg4OTg4NTA0OTMxNTQxOTc4ODgiLCJqdGkiOiJlYTI1NDYzNi1jMTgwLTQ1OTctODBkMS00OWJlYThiNDFkZGUiLCJleHAiOjE2Mzc4Mjk3NzgsImlhdCI6MTYzNTIzNzc3OCwidHlwIjoiciJ9.Q0aYIq0LNHxWllRQuUCau6z27q-ep5I0sGxo3LiwAwL6CqUPKSVmlQ4ZVDluuYtqhLRamWPxa2tgOg-LJoVHcQKv-etKq4mX7HEImEzfhlgWaq64uIHHM-pz0JlqjZCdGbMEmg1WMwQlDvQF1VD-B1OoXOleWW64qmJfb6NsCvu68q0V9st-lmheErXabfeAXNokvV5OyI0f6y0ft6MnSw6w_bvlEgQa97Bf9pcKGo6m6cOGQ-qvkVMVFU5bGKoc2Zj9CAMVlpZV2ri63OD8WE6841CS8eAvZUg1tJWLpZB_4rm_qTDncTyY39q80HmhrXeIxRVfubObmm3ItLkdaQ";
    String refreshtoken =
        "eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsImF1ZCI6IjZkOGI1NGIwLTc4N2MtNDE4Yy1iMjBhLWUxMDA5MjQ3ZDEyNCIsInN1YiI6Ijg4OTg4NTA0OTMxNTQxOTc4ODgiLCJqdGkiOiJlYTI1NDYzNi1jMTgwLTQ1OTctODBkMS00OWJlYThiNDFkZGUiLCJleHAiOjE2Mzc4Mjk3NzgsImlhdCI6MTYzNTIzNzc3OCwidHlwIjoiciJ9.Q0aYIq0LNHxWllRQuUCau6z27q-ep5I0sGxo3LiwAwL6CqUPKSVmlQ4ZVDluuYtqhLRamWPxa2tgOg-LJoVHcQKv-etKq4mX7HEImEzfhlgWaq64uIHHM-pz0JlqjZCdGbMEmg1WMwQlDvQF1VD-B1OoXOleWW64qmJfb6NsCvu68q0V9st-lmheErXabfeAXNokvV5OyI0f6y0ft6MnSw6w_bvlEgQa97Bf9pcKGo6m6cOGQ-qvkVMVFU5bGKoc2Zj9CAMVlpZV2ri63OD8WE6841CS8eAvZUg1tJWLpZB_4rm_qTDncTyY39q80HmhrXeIxRVfubObmm3ItLkdaQ";
    String accesstoken =
        "eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJvZ24iOiJwd2QiLCJhdWQiOiI2ZDhiNTRiMC03ODdjLTQxOGMtYjIwYS1lMTAwOTI0N2QxMjQiLCJzdWIiOiI4ODk4ODUwNDkzMTU0MTk3ODg4IiwianRpIjoiZDE4YTgxNjUtMTBmNy00NzM0LWJmMjMtNzRjZTk0MmE4MDMzIiwiZXhwIjoxNjM1Mjc2MTUzLCJpYXQiOjE2MzUyNzI1NTMsInR5cCI6ImEifQ.dII63cG1UMiSCnR4HOs_DfxNwzbvA-I4A0FQ-te0bqBBhe0Yy6zYrrCfKgb_jVq0VbzphkOIpLV0PSPMLqXlL68b7ho5kZsRdWCCELt1WNoeRzP2pYCJMiLFUNeH0f8mlPZJjGJmakl6WbTag3Lqak9Ygrc5965_nPNkhM-C6BT0V5JL2K6ApuldQNAMLywaliBE2gz61j1dm-Ori3g3VrQZeAhrClkeanwzJvsgiczUpMcyTjlnOco9ugU753kuB3KxWW76lWMHOZeAfd7zuI3Ut7xRtLg6RnE-onYHJQNJOCpjZzvZ6Ex-02vxkt8XJe1KlNO18NWcmKXFkd7Clg";

    void getToken() {
        try {
            OAuth2Api oauth = new OAuth2Api(GotoWebinarKeyClient, GotoWebinarKeyClientSecret);
            TokenResponse response = oauth.getAccessTokenUsingRefreshToken(refreshtoken);
            accesstoken = response.getAccessToken();
        } catch (com.logmein.gotocorelib.api.common.ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    int getIscritti() {
        getToken();
        Calendar myCalendar = new GregorianCalendar(2021, 1, 1);
        Date da = myCalendar.getTime(); // "2020-03-13T10:00:00Z"
        Date a = new GregorianCalendar(2021, 12, 1).getTime();
        try {
            RegistrantsApi registrantapi = new RegistrantsApi();
            WebinarsApi webinarapi = new WebinarsApi();

            ReportingWebinarsResponse webinar = webinarapi.getWebinars(accesstoken, 8348404185963954557L, da, a, 0L, 200L);
            int i = 0;
            int j = 0;
            for (Webinar m : webinar.getEmbedded().getWebinars()) {
                List<Registrant> registrants = registrantapi.getAllRegistrantsForWebinar(
                    accesstoken,
                    8348404185963954557L,
                    Long.parseLong(m.getWebinarKey())
                );
                j++;
                for (Registrant registrant : registrants) {
                    i++;
                    System.out.println(j + "." + i + ") " + registrant.getEmail());
                    Contatto c = dm
                        .getContatti()
                        .stream()
                        .filter(Contatto -> registrant.getEmail().equals(Contatto.getmail()))
                        .findAny()
                        .orElse(null);
                    if (c == null) {
                        dm
                            .getContatti()
                            .add(
                                new Contatto(
                                    registrant.getEmail(),
                                    registrant.getLastName(),
                                    registrant.getFirstName(),
                                    "",
                                    Fonte.GOTOWEBINAR
                                )
                            );
                    }
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return 1;
    }

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

    private void insertContact(String input, String email) {
        Calendar myCalendar = new GregorianCalendar(2021, 1, 1);
        Date da = myCalendar.getTime(); // "2020-03-13T10:00:00Z"
        Date a = new GregorianCalendar(2021, 12, 1).getTime();
    }

    int getWebinars() {
        Calendar myCalendar = new GregorianCalendar(2021, 1, 1);
        Date da = myCalendar.getTime(); // "2020-03-13T10:00:00Z"
        Date a = new GregorianCalendar(2021, 12, 1).getTime();

        try {
            WebinarsApi webinarapi = new WebinarsApi();

            ReportingWebinarsResponse webinar = webinarapi.getWebinars(accesstoken, 8348404185963954557L, da, a, 0L, 200L);

            for (Webinar m : webinar.getEmbedded().getWebinars()) {
                (((AccademiaDemoneMediator) dm).getWebinars()).add(m);
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Andrea 26/10/2021 not implemented yet
     */
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
}
