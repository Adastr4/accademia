package net.accademia.dolibarr;

public class Contatto {

    protected String piva;

    protected String f;

    protected String firstname;

    protected String lastname;
    String _mail;

    Contatto(String mail, String firstname, String lastname, String piva, String f) {
        _mail = mail == "" ? null : mail;
        this.f = f;
        this.firstname = firstname;
        this.lastname = lastname;
        this.piva = piva;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getJson() {
        // TODO Auto-generated method stub
        return (
            "{\"lastname\":\"" +
            lastname +
            "\", \"firstname\":\"" +
            firstname +
            "\", \"email\":\"" +
            _mail +
            "\"" +
            ",\"note_private\":\"" +
            f +
            "\"}"
        );
    }

    public String getLastname() {
        return lastname;
    }

    public String getmail() {
        // TODO Auto-generated method stub
        return _mail;
    }

    public String getPiva() {
        return piva;
    }
}
