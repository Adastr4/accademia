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

    IDtype t;

    Calendar calendar = Calendar.getInstance();
    List<InvoiceLine> servizi = new ArrayList<>();
    String idcliente = "1622"; // se non cè un cliennte inserscili come contatti di accademia
    Date datafattura;

    public Invoice(InvoiceLine invoiceLine, Date date, String customerid) {
        datafattura = date;
        idcliente = customerid;
        servizi.add(invoiceLine);
        this.t = IDtype.IDBOLIBARR;
    }

    public Invoice(InvoiceLine invoiceLine, Date date, String customerid, IDtype t) {
        datafattura = date;
        idcliente = customerid;
        servizi.add(invoiceLine);
        this.t = t;
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
        Map<String, String> json = new HashMap<>();

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
        Map<String, String> json = new HashMap<>();

        calendar.setTime(datafattura);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);

        json.put("socid", idcliente);
        // imposto la data al primo del mese successivo
        json.put("date", "" + calendar.getTimeInMillis() / 1000);
        json.put("type", "0");
        json.put("paye", "1");

        Gson gson = new Gson();
        return gson.toJson(json);
    }

    public boolean isCliente(String clientid) {
        if (idcliente == null) return false;
        return idcliente.equalsIgnoreCase(clientid);
    }

    public String getPiva() {
        switch (t) {
            case PIVA:
                return idcliente;
            default:
                return null;
        }
    }

    public void setClientId(String conid, IDtype t) {
        idcliente = conid;
        this.t = t;
    }

    /**
     * ci può essere una sola fattura per un determianto evento e un determianto
     * partecipante
     *
     * se non ho emesso in quel mese fattura per quel cliente allora torna falso
     *
     * @param date
     * @param clientid
     * @return
     */

    public boolean equals(Invoice c) {
        calendar.setTime(datafattura);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        GregorianCalendar da = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, 1);
        GregorianCalendar a = (GregorianCalendar) calendar;
        if (!this.idcliente.equalsIgnoreCase(c.idcliente)) return false;
        if (c.datafattura.after(da.getTime()) && c.datafattura.before(a.getTime())) return true;
        return false;
    }
}
