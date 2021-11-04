package net.accademia.dolibarr;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice {

    List<InvoiceLine> servizi = new ArrayList<InvoiceLine>();
    String idcliente = "1622"; // se non cè un cliennte inserscili come contatti di accademia
    Date datafattura;

    public Invoice(String webinarKey, String mail, Date date, String customerid) throws Exception {
        if (customerid == null) throw new Exception("Clietne non valido ");
        servizi.add(new InvoiceLine(mail, webinarKey));

        datafattura = date;
        idcliente = customerid;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return servizi;
    }

    /**
     *
     * "ref": "WEBINAR.2021.1019932687784993804", "qty": "1", "fk_product": "12" }
     *
     */
    public String getIdservizioJson(String idservizi2) {
        Map<String, String> json = new HashMap<String, String>();

        json.put("ref", "WEBINAR.2021.1019932687784993804");
        json.put("qty", "1");
        json.put("fk_product", "12");

        Gson gson = new Gson();
        return gson.toJson(json);
    }

    /**
     * "socid": "574", "type": "0", "date": 1614639600, "paye": "1"
     *
     */
    public String getJson() {
        Map<String, String> json = new HashMap<String, String>();

        json.put("socid", idcliente);
        json.put("date", "\"" + datafattura.getTime() + "\"");
        json.put("type", "0");
        json.put("paye", "1");

        Gson gson = new Gson();
        return gson.toJson(json);
    }

    public boolean isCliente(String clientid) {
        if (idcliente == null) return false;
        return idcliente.equalsIgnoreCase(clientid);
    }

    /** se non ho emesso in quel mese fattura per quel cliente allora torna falso
     * @param date
     * @param clientid
     * @return
     */
    public boolean isDraft(Date date, String clientid) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datafattura);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        GregorianCalendar da = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, 1);
        GregorianCalendar a = (GregorianCalendar) calendar;
        if (!this.idcliente.equalsIgnoreCase(clientid)) return false;
        if (date.after(da.getTime()) && date.before(a.getTime())) return true;
        return false;
    }
}
