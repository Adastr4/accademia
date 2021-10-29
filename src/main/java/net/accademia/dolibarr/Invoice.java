package net.accademia.dolibarr;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice {

    List<String> idservizi = new ArrayList<String>();
    List<String> partecipanti = new ArrayList<String>();
    String idcliente = "574";

    public Invoice(String webinarKey, String email) {
        idservizi.add(webinarKey);
    }

    public boolean isIdServizio(String idservizio) {
        String ret = idservizi.stream().filter(servizio -> servizio == idservizio).findAny().orElse(null);
        return ret == null ? false : true;
    }

    public boolean isPartecipante(String email) {
        String ret = partecipanti.stream().filter(part -> part == email).findAny().orElse(null);
        return ret == null ? false : true;
    }

    /**
     * "socid": "574", "type": "0", "date": 1614639600, "paye": "1"
     *
     */
    public String getJson() {
        Map<String, String> json = new HashMap<String, String>();

        json.put("socid", idcliente);
        json.put("date", "1614639600");
        json.put("type", "0");
        json.put("paye", "1");

        Gson gson = new Gson();
        return gson.toJson(json);
    }

    public List<String> getIdservizi() {
        return idservizi;
    }

    public List<String> getPartecipanti() {
        return partecipanti;
    }

    /**
 * 
 * 	"ref": "WEBINAR.2021.1019932687784993804", "qty": "1", "fk_product":
		     * "12" }
	
 * */
    public String getIdservizioJson(String idservizi2) {
        Map<String, String> json = new HashMap<String, String>();

        json.put("ref", "WEBINAR.2021.1019932687784993804");
        json.put("qty", "1");
        json.put("fk_product", "12");

        Gson gson = new Gson();
        return gson.toJson(json);
    }
}
