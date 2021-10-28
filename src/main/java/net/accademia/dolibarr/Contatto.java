package net.accademia.dolibarr;

public class Contatto {

    public String getPiva() {
        return piva;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    protected String piva;
    protected Fonte f;
    protected String firstname;
    protected String lastname;

    String _mail;

    Contatto(String mail, String firstname, String lastname, String piva, Fonte f) {
        _mail = mail;
        this.f = f;
        this.firstname = firstname;
        this.lastname = lastname;
        this.piva = piva;
    }

    public String getJson() {
        // TODO Auto-generated method stub
        return "{\"lastname\":\"" + lastname + "\", \"firstname\":\"" + firstname + "\", \"email\":\"" + _mail + "\"}";
    }

    public String getmail() {
        // TODO Auto-generated method stub
        return _mail;
    }
}
