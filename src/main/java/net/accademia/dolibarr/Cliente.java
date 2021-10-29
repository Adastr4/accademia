package net.accademia.dolibarr;

public class Cliente extends Contatto {

    String codiceunivoco;

    Cliente(String mail, String firstname, String vat, String cu, Fonte f) {
        super(mail, firstname, "", vat, f);
        codiceunivoco = cu;
    }

    @Override
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

    public String getVat() {
        // TODO Auto-generated method stub
        return piva;
    }
}
