package net.accademia.dolibarr;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class InvoiceLine {

    Map idservizio;
    String idpartecipante;

    public InvoiceLine(String mail, Map servizio) {
        idservizio = servizio;
        idpartecipante = mail;
    }

    public String toJson(InvoiceLine idservizi) {
        Map<String, String> json = new HashMap<>();

        json.put("ref", (String) idservizio.get("ref"));
        json.put("qty", "1");
        json.put("fk_product", (String) idservizio.get("id"));
        json.put("subprice", (String) idservizio.get("price"));

        Gson gson = new Gson();
        return gson.toJson(json);
    }
}
