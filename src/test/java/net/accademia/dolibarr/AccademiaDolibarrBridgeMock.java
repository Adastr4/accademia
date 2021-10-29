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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class AccademiaDolibarrBridgeMock extends AccademiaDolibarrBridge {

    public AccademiaDolibarrBridgeMock(AccademiaDemoneMediator dm) {
        super(dm);
        // TODO Auto-generated constructor stub
    }

    public void deleteInvoices() {
        final String insertapi = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("DOLAPIKEY", DolibarrKey);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<String> ret = restTemplate.exchange(insertapi, HttpMethod.GET, entity, String.class);

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
    }
}
