package net.accademia.dolibarr;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Contatto {

    String codiceunivoco;
    String id = null;
    String piva = null;
    protected List<Contatto> contatti;

    public Cliente(String mail, String firstname, String vat, String cu, String tel, String f) {
        super(mail, firstname, "", tel, null, f);
        piva = vat == null || vat.length() < 9 ? "" : vat;
        if (this.firstname.length() > 80) this.firstname = this.firstname.substring(0, 80);
        if (vat.length() > 11) piva = vat.substring(0, 11);
        codiceunivoco = cu.length() > 7 || cu.length() < 6 ? "" : cu.trim();
        contatti = new ArrayList<>();
        this.f = f;
    }

    public List<Contatto> getContatti() {
        return contatti;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getJson() {
        return (
            "{\"name\": \"" +
            firstname.replace('\"', '\'') +
            "\",\"client\": \"1\",\"prospect\": 0,\"fournisseur\": \"0\"	" +
            (id == null ? ",\"code_client\": \"auto\"" : "") +
            ",\"email\":\"" +
            _mail +
            "\",\"tva_intra\":\"" +
            getVat() +
            "\",\"note_private\":\"" +
            f +
            "\" ,\"array_options\": { \"options_codiceunivoco\": \"" +
            codiceunivoco +
            "\" }}"
        );
    }

    public String getVat() {
        return piva;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean equals(Cliente c) {
        if ((_mail.length() != 0 && c._mail.equalsIgnoreCase(_mail)) || (piva.length() != 0 && c.piva.equalsIgnoreCase(piva))) return true;
        // il codice univoco non è univoco
        //		if (codiceunivoco.length() != 0 && c.codiceunivoco.equalsIgnoreCase(codiceunivoco))
        //			return true;
        return false;
    }
}
