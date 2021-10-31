package net.accademia.dolibarr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
public abstract class DolibarrBridge extends DataSource {

    final String uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC&limit=100";
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> entity = null;
    protected String DolibarrKey = "VR576iFzqo5Q4Y6CEUgz01Ag0QQmelt0";
    RestTemplate restTemplate = new RestTemplate();
    List<Object> CustomerList = null;
    JsonParser parser = new BasicJsonParser();

    {
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("DOLAPIKEY", DolibarrKey);
    }

    public DolibarrBridge(DemoneMediator dm) {
        super(dm);
        // TODO Auto-generated constructor stub
    }

    public boolean deleteInvoices() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices?limit=1000";
        boolean moreinvoice = false;

        entity = new HttpEntity<>(null, headers);

        try {
            ret = restTemplate.exchange(insertapi, HttpMethod.GET, entity, String.class);
            moreinvoice = true;
            List json = new ArrayList();

            try {
                Type listType = new TypeToken<List<Object>>() {}.getType();

                List<Object> yourList = new Gson().fromJson(ret.getBody(), listType);

                for (Object map : yourList) {
                    String id = (String) ((Map) map).get("id");
                    if (id.equalsIgnoreCase("5")) continue;

                    ret = restTemplate.exchange(insertapi + "/" + id, HttpMethod.DELETE, entity, String.class);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return moreinvoice;
    }

    private String getClienteID(String piva) {
        if ((piva == null) || (piva == "")) return null;
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/thirdparties";

        entity = new HttpEntity<>(null, headers);
        List json = null;
        try {
            ret = restTemplate.exchange(insertapi + "?sqlfilters=(t.tva_intra:like:'" + piva + "%')", HttpMethod.GET, entity, String.class);

            if (ret.getStatusCode() != HttpStatus.OK) return null;

            json = parser.parseList(ret.getBody());
            return (String) ((Map<?, ?>) json.get(0)).get("ref");
        } catch (RestClientException e) {
            // contatto non presente
            System.err.println(e.getMessage());
        }
        return null;
    }

    protected String getCodiceCliente(String email) {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/contacts?limit=1000";

        entity = new HttpEntity<>(null, headers);

        if (CustomerList == null) {
            try {
                ret = restTemplate.exchange(insertapi, HttpMethod.GET, entity, String.class);

                try {
                    Type listType = new TypeToken<List<Object>>() {}.getType();

                    CustomerList = new Gson().fromJson(ret.getBody(), listType);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        for (Object map : CustomerList) {
            String id = (String) ((Map) map).get("email");
            if (id.equalsIgnoreCase(email)) return (String) ((Map) map).get("socid");
        }

        return null;
    }

    protected String getCodiceCliente(String lastname, String email) {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/contacts?limit=1000";

        try {
            entity = new HttpEntity<>(null, headers);
            ret = null;
            ret = restTemplate.exchange(insertapi + "?sqlfilters=(t.email:like:'" + email + "%')", HttpMethod.GET, entity, String.class);
            if (ret.getStatusCode() == HttpStatus.OK) {
                List jsonl = parser.parseList(ret.getBody());
                String conid = (String) ((Map<?, ?>) jsonl.get(0)).get("socid");

                return conid;
            }
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    int getCustomers() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/thirdparties?sortfield=t.rowid&sortorder=ASC";

        entity = new HttpEntity<>(null, headers);
        // HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ret = restTemplate.exchange(insertapi, HttpMethod.GET, entity, String.class);

        JSONArray json = null;
        try {
            json = (JSONArray) parser.parseMap(ret.getBody());
            System.out.println(json.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;
    }

    public int getFatture() {
        final String uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC";

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("DOLAPIKEY", DolibarrKey);
        entity = new HttpEntity<>(null, headers);

        ret = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        List json = new ArrayList();
        try {
            json = parser.parseList(ret.getBody());
            System.out.println(json.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

        entity = new HttpEntity<>(c.getJson(), headers);
        ret = null;
        String jsonString = c.getJson();
        /**
         * Associa il contatto al cliente
         */

        Map<String, Object> json = parser.parseMap(c.getJson());
        String cid = getClienteID(c.getPiva());
        if (cid != null) {
            json.put("socid", cid);
            json.put("fk_soc", cid);
            Gson gson = new Gson();
            jsonString = gson.toJson(json);
        }
        entity = new HttpEntity<>(jsonString, headers);
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
                String conid = (String) ((Map<?, ?>) jsonl.get(0)).get("id");

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

    public int insertContacts() {
        dm.getContatti();
        for (Contatto c : dm.getContatti()) {
            insertContact(c);
        }
        return 1;
    }

    /**
     * se il cliente esiste lo asggiorna con le info disponiblli
     *
     * @param cliente
     * @return
     */
    int insertCustomer(Cliente cliente) {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/thirdparties";

        entity = new HttpEntity<>(cliente.getJson(), headers);
        try {
            ret =
                restTemplate.exchange(
                    insertapi +
                    "?sqlfilters=((t.tva_intra:like:'" +
                    cliente.getVat() +
                    "%')or (t.email:like:'" +
                    cliente.getmail() +
                    "%'))",
                    HttpMethod.GET,
                    entity,
                    String.class
                );
            if (ret.getStatusCode() == HttpStatus.OK) {
                List jsonl = parser.parseList(ret.getBody());
                String conid = (String) ((Map<?, ?>) jsonl.get(0)).get("ref");
                cliente.setId(conid);
                entity = new HttpEntity<>(cliente.getJson(), headers);

                ret = restTemplate.exchange(insertapi + "/" + conid, HttpMethod.PUT, entity, String.class);
                return 1;
            }
        } catch (RestClientException e) {
            // contatto già presente
            System.err.println(e.getMessage());
        }

        try {
            ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
        } catch (RestClientException e) {
            System.err.println(e.getMessage());
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

    int insertInvoice(Invoice fattura) {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices";

        try {
            String input = fattura.getJson();
            entity = new HttpEntity<>(input, headers);
            ret = restTemplate.exchange(insertapi, HttpMethod.POST, entity, String.class);
            String idfattura = ret.getBody();
            for (String idservizi : fattura.getIdservizi()) {
                entity = new HttpEntity<>(fattura.getIdservizioJson(idservizi), headers);
                ret = restTemplate.exchange(insertapi + "/" + idfattura + "/lines", HttpMethod.POST, entity, String.class);
            }
            // ret = restTemplate.exchange(insertapi+"/"+idfattura+"/validate",
            // HttpMethod.POST, entity, String.class);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public abstract int insertInvoices();
}
