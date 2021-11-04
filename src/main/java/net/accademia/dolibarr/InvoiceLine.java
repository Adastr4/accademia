package net.accademia.dolibarr;

public class InvoiceLine {

    String idservizio;
    String idpartecipante;
    int prezzo;

    public InvoiceLine(String mail, String webinarKey) {
        idservizio = webinarKey;
        idpartecipante = mail;
    }

    public String toJson(InvoiceLine idservizi) {
        return null;
    }
}
