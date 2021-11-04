package net.accademia.dolibarr;

/**
 * @author adastra
 *
 */
public class Contatto {

    Cliente c;

    /**
     *
     */
    protected String f;

    /**
     *
     */
    protected String firstname;

    /**
     *
     */
    protected String lastname;

    /**
     *
     */
    String _mail;
    /**
     *
     */
    String tel;
    /**
     *
     */
    String error = null;

    /**
     * @param mail
     * @param firstname
     * @param lastname
     * @param piva
     * @param f
     */
    Contatto(String mail, String firstname, String lastname, String tel, Cliente c, String fonte) {
        _mail = mail;

        this.firstname = firstname;
        this.lastname = lastname;
        this.c = c;
        this.tel = tel;
        f = fonte;
        /**
         * se la mail del cliente non è valida usiamo quella del primo contatto
         */
        if (c == null) return;
        if (c._mail == null) {
            c._mail = mail;
            return;
        }
        if (!c._mail.contains("@")) {
            c._mail = mail;
            return;
        }
    }

    public Cliente getCliente() {
        return c;
    }

    /**
     * @return
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return
     */
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
            ",\"phone_pro\":\"" +
            tel +
            "\",\"note_private\":\"" +
            f +
            "\"}"
        );
    }

    /**
     * @return
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @return
     */
    public String getmail() {
        // TODO Auto-generated method stub
        return _mail;
    }

    public String getTel() {
        return tel;
    }

    /**
     * @param message
     */
    public void setError(String message) {
        error = message;
    }
}
