package net.accademia.dolibarr;

import com.google.gson.Gson;
import com.logmein.gotowebinar.api.model.Webinar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * {@code
 * 
 * {
   "access_token":"eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsImF1ZCI6IjZkOGI1NGIwLTc4N2MtNDE4Yy1iMjBhLWUxMDA5MjQ3ZDEyNCIsInN1YiI6Ijg4OTg4NTA0OTMxNTQxOTc4ODgiLCJqdGkiOiIwMzhlYTdjOC1kZDc3LTQ0MjMtYjM2Mi0xYzQ1YzVhMzQxYTgiLCJleHAiOjE2MzUyNDEzNzgsImlhdCI6MTYzNTIzNzc3OCwidHlwIjoiYSJ9.KQ0z5m7v3yC_TF3uY0mNaqWvcGc99y5Uznfy85fI3zahDo8BDsibivi12X1sU0Dj6GciqqthUVW-CcPUFpEQUx4zIRLOq7UMTcsks2BEUVadQzLAqxWqiAzfOJoKs5XkC98CNh_RPdquaXxNJmo40Ml2eH32UxZ24vHyTFpwFyXGKARxZnQiWBY1pCFzefuaCowXfupq_bOVfLzuZbZrffF5W4DmPXehePS2Avw3Z31y0kA_0KVbVZ-7iAgsKyXFnFh485_Mu39dtmuBhFUByv0iCovJcOqxQlJtc09u4ID2rUKu_PjNf30yTXYAZlcdvjJvcIeGO1Qpcg1vbmF2sw",
   "token_type":"Bearer",
   "refresh_token":"eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJzYyI6ImNvbGxhYjoiLCJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsImF1ZCI6IjZkOGI1NGIwLTc4N2MtNDE4Yy1iMjBhLWUxMDA5MjQ3ZDEyNCIsInN1YiI6Ijg4OTg4NTA0OTMxNTQxOTc4ODgiLCJqdGkiOiJlYTI1NDYzNi1jMTgwLTQ1OTctODBkMS00OWJlYThiNDFkZGUiLCJleHAiOjE2Mzc4Mjk3NzgsImlhdCI6MTYzNTIzNzc3OCwidHlwIjoiciJ9.Q0aYIq0LNHxWllRQuUCau6z27q-ep5I0sGxo3LiwAwL6CqUPKSVmlQ4ZVDluuYtqhLRamWPxa2tgOg-LJoVHcQKv-etKq4mX7HEImEzfhlgWaq64uIHHM-pz0JlqjZCdGbMEmg1WMwQlDvQF1VD-B1OoXOleWW64qmJfb6NsCvu68q0V9st-lmheErXabfeAXNokvV5OyI0f6y0ft6MnSw6w_bvlEgQa97Bf9pcKGo6m6cOGQ-qvkVMVFU5bGKoc2Zj9CAMVlpZV2ri63OD8WE6841CS8eAvZUg1tJWLpZB_4rm_qTDncTyY39q80HmhrXeIxRVfubObmm3ItLkdaQ",
   "expires_in":3600,
   "account_key":"8348404185963954557",
   "email":"info@accademiaeuropea.net",
   "firstName":"Accademia",
   "lastName":"Europea Soc. Coop",
   "organizer_key":"8898850493154197888",
   "version":"3",
   "account_type":""
 * 
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class DolibarrBridge extends DataSource {

    public DolibarrBridge(DemoneMediator dm) {
        super(dm);
        // TODO Auto-generated constructor stub
    }

    final String uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC&limit=100";
    final String oaut =
        "https://api.getgo.com/oauth/v2/authorize?client_id=6d8b54b0-787c-418c-b20a-e1009247d124&response_type=code&redirect_uri=https://www.accademiaeuropea.net/gotowebinar/oauth";
    // eyJraWQiOiJvYXV0aHYyLmxtaS5jb20uMDIxOSIsImFsZyI6IlJTNTEyIn0.eyJscyI6IjkwYjZmMTg4LWQ1NjItNDg3MC1hODQ1LWNiYmFhMjg0NDEwZSIsIm9nbiI6InB3ZCIsInVyaSI6Imh0dHBzOi8vd3d3LmFjY2FkZW1pYWV1cm9wZWEubmV0L2dvdG93ZWJpbmFyL29hdXRoIiwic2MiOiJjb2xsYWI6IiwiYXVkIjoiNmQ4YjU0YjAtNzg3Yy00MThjLWIyMGEtZTEwMDkyNDdkMTI0Iiwic3ViIjoiODg5ODg1MDQ5MzE1NDE5Nzg4OCIsImp0aSI6IjI5YjNjMjJkLTMzZWEtNGM5My05ZTRkLThlODU4Mjk5YTMzMSIsImV4cCI6MTYzNTE4ODk0NCwiaWF0IjoxNjM1MTg4MzQ0LCJ0eXAiOiJjIn0.RNDPRLowb3LACc573Pyume0tDOeOrnbpjXHlfcs4h-kdJp9VELDn96mpObBMp3m_AmOHRqDgS504Qt2E87_vMwXY-6WdfXufr6rQOUFMkA4TXAJXxigkdIwgFiYUJaauG-yXQlqFeWaqpZBgACMFho4roVVB5B_d_q5ZCCQjjqVpVdE5rc2qF42Dhw-5eASxkLyRruA7z9aGp48jFKlbjT8H7-dBVsqzjd0-TbIzNLLQSzRknnfWOiMH4BMOFZZHFyeej1KZPJoMG-PzJmiIdSpE-MMB6_ZScFM7LomtLST9aBleZaVm-g6qX_zceV329ZRykT5FVxaoHEFeYGUasA
    // final String uri =
    // "https://localhost:8181/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC&limit=100";
    String DolibarrKey = "VR576iFzqo5Q4Y6CEUgz01Ag0QQmelt0";

    RestTemplate restTemplate = new RestTemplate();

    public int insertWebinar() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/products";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("DOLAPIKEY", DolibarrKey);

        try {
            for (Webinar m : dm.getWebinars()) {
                String input =
                    "{\"ref\":\"WEBINAR.2021." +
                    m.getWebinarKey() +
                    "\", \"label\":\"" +
                    m.getSubject() +
                    "\", \"status\":\"1\",\"status_buy\":\"1\", \"type\":\"1\" }";

                HttpEntity<String> entity = new HttpEntity<String>(input, headers);

                ResponseEntity<String> ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;
    }

    int getCustomers() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/thirdparties?sortfield=t.rowid&sortorder=ASC";

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

    /**
     * Andrea 26/10/2021 {@code
     * leggere i dati degli iscritti dai file di Excel verificare
     * che il contatto sia inserito
     * se non è inserito inserirlo verificare che
     * l'azienda sia inserita
     * se non è inserita inserirla
     * }
     *
     * @return
     */
    public int InsertCustomers() {
        dm.getClienti();
        for (Cliente cliente : dm.getClienti()) {
            insertCustomer(cliente);
        }
        return 1;
    }

    int insertCustomer(Cliente cliente) {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/thirdparties";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("DOLAPIKEY", DolibarrKey);

        HttpEntity<String> entity = new HttpEntity<String>(cliente.getJson(), headers);
        try {
            if (
                restTemplate
                    .exchange(
                        insertapi +
                        "?sqlfilters=((t.tva_intra:like:'" +
                        cliente.getVat() +
                        "%')or (t.email:like:'" +
                        cliente.getmail() +
                        "%'))",
                        HttpMethod.GET,
                        entity,
                        String.class
                    )
                    .getStatusCode() ==
                HttpStatus.OK
            ) return 1;
        } catch (RestClientException e) {
            // contatto già presente
        }

        try {
            ResponseEntity<String> ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
        } catch (RestClientException e) {}
        return 1;
    }

    public int getFatture() {
        final String uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC&limit=100";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("DOLAPIKEY", DolibarrKey);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> ret = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

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

    public int insertContacts() {
        dm.getContatti();
        for (Contatto c : dm.getContatti()) {
            insertContact(c);
        }
        return 1;
    }

    /**
     * se il contatto non esiste lo inserisce controlla che ci sia il cliente e in
     * caso lo associa se il contatto esiste e il cliente esiste allora lo associa
     *
     * @param c
     * @return
     */
    int insertContact(Contatto c) {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/contacts";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("DOLAPIKEY", DolibarrKey);

        HttpEntity<String> entity = new HttpEntity<String>(c.getJson(), headers);
        ResponseEntity<String> ret = null;
        String jsonString = c.getJson();
        /**
         * Associa il contatto al cliente
         */
        JsonParser parser = new BasicJsonParser();
        Map json = parser.parseMap(c.getJson());
        String cid = getClienteID(c.getPiva());
        if (cid != null) {
            json.put("socid", cid);
            json.put("fk_soc", cid);
            Gson gson = new Gson();
            jsonString = gson.toJson(json);
        }
        entity = new HttpEntity<String>(jsonString, headers);
        try {
            ret =
                restTemplate.exchange(
                    insertapi + "?sqlfilters=((t.lastname:like:'" + c.getLastname() + "%') and (t.email:like:'" + c.getmail() + "%'))",
                    HttpMethod.GET,
                    entity,
                    String.class
                );
            if (cid == null) return 1; // inuitle proseguire se il cliente non c'è
            if (ret.getStatusCode() == HttpStatus.OK) {
                List jsonl = parser.parseList(ret.getBody());
                String conid = (String) ((Map) jsonl.get(0)).get("id");

                ret = restTemplate.exchange(insertapi + "/" + conid, HttpMethod.PUT, entity, String.class);
                return 1;
            }
        } catch (RestClientException e) {
            // contatto non presente
        }
        try {
            ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return 0;
        // TODO Auto-generated method stub

    }

    private String getClienteID(String piva) {
        if (piva == null) return null;
        if (piva == "") return null;
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/thirdparties";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("DOLAPIKEY", DolibarrKey);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        List json = null;
        try {
            ResponseEntity<String> ret = restTemplate.exchange(
                insertapi + "?sqlfilters=(t.tva_intra:like:'" + piva + "%')",
                HttpMethod.GET,
                entity,
                String.class
            );

            if (ret.getStatusCode() != HttpStatus.OK) return null;
            JsonParser parser = new BasicJsonParser();
            json = parser.parseList(ret.getBody());
            return (String) ((Map) json.get(0)).get("ref");
        } catch (RestClientException e) {
            // contatto non presente
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Per tutti gli iscritti al webinar dai form on line, inserire fattura alla
     * loro azienda
     *
     *
     * inserisci la fattura
     * https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices
     * {@code
     *
     *       "socid": "574",
     *       "type": "0",
     *       "date": 1614639600,
     *       "paye": "1"
     *       }
     *
     *
     * inserisci la linea
     * https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices/9/lines
     *
     * {@code
     * { "ref": "WEBINAR.2021.1019932687784993804", "qty": "1", "fk_product": "12" }
     * }
     *
     * validi la fattura
     * https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices/9/validate
     */

    public int insertInvoices() {
        dm.getContatti();
        for (Contatto c : dm.getContatti()) {
            insertInvoice(new Invoice(c));
        }
        return 1;
    }

    int insertInvoice(Invoice invoice) {
        return 0;
        // TODO Auto-generated method stub

    }
}
