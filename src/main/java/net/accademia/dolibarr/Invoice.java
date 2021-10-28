package net.accademia.dolibarr;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

    List<String> idservizi = new ArrayList<String>();
    List<String> partecipanti = new ArrayList<String>();

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
}
