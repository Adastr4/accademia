package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.RegistrantsApi;
import com.logmein.gotowebinar.api.WebinarsApi;
import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.ReportingWebinarsResponse;
import com.logmein.gotowebinar.api.model.Webinar;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class DolibarrBridge {

    final String uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC&limit=100";
    final String oaut =
        "https://api.getgo.com/oauth/v2/authorize?client_id=6d8b54b0-787c-418c-b20a-e1009247d124&response_type=code&redirect_uri=https://www.accademiaeuropea.net/gotowebinar/oauth";
    // eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsInVyaSI6Imh0dHBzOi8vd3d3LmFjY2FkZW1pYWV1cm9wZWEubmV0L2dvdG93ZWJpbmFyL29hdXRoIiwic2MiOiJjb2xsYWI6IiwiYXVkIjoiNmQ4YjU0YjAtNzg3Yy00MThjLWIyMGEtZTEwMDkyNDdkMTI0Iiwic3ViIjoiODg5ODg1MDQ5MzE1NDE5Nzg4OCIsImp0aSI6IjI5YjNjMjJkLTMzZWEtNGM5My05ZTRkLThlODU4Mjk5YTMzMSIsImV4cCI6MTYzNTE4ODk0NCwiaWF0IjoxNjM1MTg4MzQ0LCJ0eXAiOiJjIn0.RNDPRLowb3LACc573Pyume0tDOeOrnbpjXHlfcs4h-kdJp9VELDn96mpObBMp3m_AmOHRqDgS504Qt2E87_vMwXY-6WdfXufr6rQOUFMkA4TXAJXxigkdIwgFiYUJaauG-yXQlqFeWaqpZBgACMFho4roVVB5B_d_q5ZCCQjjqVpVdE5rc2qF42Dhw-5eASxkLyRruA7z9aGp48jFKlbjT8H7-dBVsqzjd0-TbIzNLLQSzRknnfWOiMH4BMOFZZHFyeej1KZPJoMG-PzJmiIdSpE-MMB6_ZScFM7LomtLST9aBleZaVm-g6qX_zceV329ZRykT5FVxaoHEFeYGUasA
    // final String uri =
    // "https://localhost:8181/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC&limit=100";
    String DolibarrKey = "VR576iFzqo5Q4Y6CEUgz01Ag0QQmelt0";
    String GotoWebinarKeyClient = "6d8b54b0-787c-418c-b20a-e1009247d124";
    String GotoWebinarKeyClientSecret = "5mbvleugSuVgAg46qSrA0ueQ";
    String authheader = "NmQ4YjU0YjAtNzg3Yy00MThjLWIyMGEtZTEwMDkyNDdkMTI0OjVtYnZsZXVnU3VWZ0FnNDZxU3JBMHVlUQ==";
    String accesstoken =
        "eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsImF1ZCI6IjZkOGI1NGIwLTc4N2MtNDE4Yy1iMjBhLWUxMDA5MjQ3ZDEyNCIsInN1YiI6Ijg4OTg4NTA0OTMxNTQxOTc4ODgiLCJqdGkiOiI0ZGUwMjg2Ni01YTRkLTQ2MmUtOThiMC1lYjdmNGExMDg0MWMiLCJleHAiOjE2MzUyMzQzMTQsImlhdCI6MTYzNTIzMDcxNCwidHlwIjoiYSJ9.Lnv-0Ce1Jei7JPxFcHGxkjB_PZLzxd6ZNEuVbpyfvpiAqhD9VrzH35Tfuc22HEU_NeFvduiH5DW9Ha7AtR0H3ijT7499Tk8jNgltNG66en6MFpA8M1BT9EDwauRFZPhGdoWvVdUPLVj0KSZVfqGGzEXJzJf5yn5ls0GMiX2iGmy9QoMpRvOVmtzW8RYpxYwJrEYR2jJIgvu4drgqM7-wOtJg08VIFEcujKqftH8fbNCteEnT09I-guuX4MTsvAtzY2o2bR7CgfsO5jgOCLz2lLUUckvm6ayqSggt2TP2ayOvx73xe0EdaVYxEZOhW0AISI3aeXEFzbmQcsJppVer4A";

    public int insertWebinar() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/products";

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        // restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        headers.set("DOLAPIKEY", DolibarrKey);
        String input = "{\"ref\":\"WEBINAR.2021.02.05\", \"label\":\"prova23234\", \"status\":\"1\",\"status_buy\":\"1\", \"type\":\"1\" }";

        HttpEntity<String> entity = new HttpEntity<String>(input, headers);

        ResponseEntity<String> ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
        JSONArray json = null;
        try {
            JsonParser parser = new BasicJsonParser();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Calendar myCalendar = new GregorianCalendar(2021, 1, 1);
        Date da = myCalendar.getTime(); // "2020-03-13T10:00:00Z"
        Date a = new GregorianCalendar(2021, 12, 1).getTime();
        try {
            RegistrantsApi registrantapi = new RegistrantsApi();
            WebinarsApi webinarapi = new WebinarsApi();

            ReportingWebinarsResponse webinar = webinarapi.getWebinars(accesstoken, 8348404185963954557L, da, a, 0L, 200L);
            int i = 0;
            int j = 0;
            for (Webinar m : webinar.getEmbedded().getWebinars()) {}
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;
    }

    int getCustomers() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/thirdparties?sortfield=t.rowid&sortorder=ASC";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("DOLAPIKEY", DolibarrKey);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        // HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> ret = restTemplate.exchange(insertapi, HttpMethod.GET, entity, String.class);
        // ResponseEntity<String> ret = restTemplate.exchange(uri, HttpMethod.POST,
        // entity, String.class);
        // Object result = restTemplate.getForObject(uri, Object.class);
        JSONArray json = null;
        try {
            JsonParser parser = new BasicJsonParser();
            json = (JSONArray) parser.parseMap(ret.getBody());
            System.out.println(json.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;
    }

    public int InsertCustomer() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/thirdparties";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("DOLAPIKEY", DolibarrKey);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        // HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
        // ResponseEntity<String> ret = restTemplate.exchange(uri, HttpMethod.POST,
        // entity, String.class);
        // Object result = restTemplate.getForObject(uri, Object.class);
        JSONArray json = null;
        try {
            JsonParser parser = new BasicJsonParser();
            json = (JSONArray) parser.parseMap(ret.getBody());
            System.out.println(json.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 1;
    }

    public int getFatture() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("DOLAPIKEY", DolibarrKey);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        // HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> ret = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        // ResponseEntity<String> ret = restTemplate.exchange(uri, HttpMethod.POST,
        // entity, String.class);
        // Object result = restTemplate.getForObject(uri, Object.class);
        JSONArray json = null;
        try {
            JsonParser parser = new BasicJsonParser();
            json = (JSONArray) parser.parseMap(ret.getBody());
            System.out.println(json.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 1;
    }

    String getIscritti() {
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
                }
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return DolibarrKey;
    }
}
