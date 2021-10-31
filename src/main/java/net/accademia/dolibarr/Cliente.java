package net.accademia.dolibarr;

public class Cliente extends Contatto {

    String codiceunivoco;
    String id = null;

    Cliente(String mail, String firstname, String vat, String cu, String f) {
        super(mail, firstname, "", vat, f);
        if (this.firstname.length() > 80) this.firstname = this.firstname.substring(0, 80);
        if (piva.length() > 11) piva = piva.substring(0, 11);
        codiceunivoco = cu;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVat() {
        // TODO Auto-generated method stub
        return piva;
    }
}
