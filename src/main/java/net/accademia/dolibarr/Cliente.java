package net.accademia.dolibarr;

public class Cliente extends Contatto {

    Cliente(String mail, String firstname, String vat, Fonte f) {
        super(mail, firstname, vat, f);
        // TODO Auto-generated constructor stub
    }

    public String getVat() {
        // TODO Auto-generated method stub
        return lastname;
    }

    public String getJson() {
        return (
            "{\"name\": \"" +
            firstname.replace('\"', '\'') +
            "\",\"client\": \"1\",\"prospect\": 0,\"fournisseur\": \"0\",	\"code_client\": \"auto\",\"email\":\"" +
            _mail +
            "\",\"tva_intra\":\"" +
            getVat() +
            "\"}"
        );
    }
}
