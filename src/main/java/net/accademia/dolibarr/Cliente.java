package net.accademia.dolibarr;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Contatto {

    String codiceunivoco;
    String id = null;
    String piva = null;
    protected List<Contatto> contatti;

    Cliente(String mail, String firstname, String vat, String cu, String tel, String f) {
        super(mail, firstname, "", tel, null);
        if (this.firstname.length() > 80) this.firstname = this.firstname.substring(0, 80);
        if (vat.length() > 11) piva = vat.substring(0, 11);
        codiceunivoco = cu;
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
            "\"}"
        );
    }

    public String getVat() {
        // TODO Auto-generated method stub
        return piva;
    }

    public void setId(String id) {
        this.id = id;
    }
}
