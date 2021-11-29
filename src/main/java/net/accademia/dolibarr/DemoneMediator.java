package net.accademia.dolibarr;

import eu.cartsc.demone.OwnCloudBridge;
import java.util.ArrayList;
import java.util.List;

/**
 * @author adastra
 *
 */
public abstract class DemoneMediator {

    /**
     *
     */

    protected String csvinvoicefolder = null;
    protected String odsfolder = null;
    protected String xlsfolder = null;

    protected Cliente me = null;
    /**
     *
     */
    protected List<Cliente> clienti;

    /**
     *
     */
    protected List<Invoice> fatture;
    /**
     *
     */
    protected DolibarrBridge bg = null;
    /**
     *
     */
    protected GoogleSheet gs = null;
    /**
     *
     */
    protected GotoWebinarBridge gtb = null;
    /**
     *
     */
    protected OdsBridge odb = null;

    /**
     *
     */
    protected XLSBridge xb = null;

    /**
     *
     */

    protected OwnCloudBridge oc = null;

    protected DemoneMediator() {
        try {
            clienti = new ArrayList<>();

            fatture = new ArrayList<>();

            odb = new OdsBridge(this);
            xb = new XLSBridge(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public List<Cliente> getClienti() {
        return clienti;
    }

    /**
     * cerca tra tutti i contatti dei clienti se cè un contatto con quella mail
     *
     * @param email
     * @param lastname
     * @param firstname
     * @param prov
     * @return
     */

    public Contatto searchContattofromCliente(String email, String lastname, String firstname, String prov) {
        Contatto c = null;
        Cliente trovato = null;
        for (Cliente cli : clienti) {
            c = cli.getContatti().stream().filter(Contatto -> email.equals(Contatto.getmail())).findAny().orElse(null);
            if (c != null) {
                c = new Contatto(email, firstname, lastname, "", cli, prov);
                cli.getContatti().add(c);
                trovato = cli;
                break;
            }
        }
        if (trovato != null) return c;

        // trovato = new Cliente(email, lastname, "", "", "", prov);
        // clienti.add(trovato);
        // c = new Contatto(email, lastname, firstname, "", trovato);
        // trovato.getContatti().add(c);

        return c;
    }

    /**
     * @return
     */
    public List<Invoice> getFatture() {
        return fatture;
    }

    /**
     * @return
     */
    public abstract int insertInvoices();

    /**
     * @param clienti
     */
    public void setClienti(List<Cliente> clienti) {
        this.clienti = clienti;
    }

    /**
     * prende la lista degli iscritti da google sheet e li inserisce su Dolibarr
     * verifica che gli iscritti siano gli stessi di gotowebinar
     *
     * @return
     */

    public int SyncIscrittitoDolibarr() {
        /**
         * Prima i dati legacy
         *
         */
        odb.readData("/home/nodejs/ownCloud/2021 - Accademia Europea/amministrazione/erpdata/iscrizionewebinar.ods");
        xb.readData("/home/adastra/ISCRIZIONE WEBINAR20210224.xlsx");

        String[] iscritti = {
            "10hI-OeiU1huDcO2Z0Aq6ibwVPQlz3jbG2aQhJcMn-AY", // 18-25/03/2021
            "1S0m1x5j9sxZyCtflqJs8pV2NIFMrwUb3GlLhGzDRGEQ", //27/04/2021-04-11/05/2021
            "1pAT4iJZISaSjdjWUczjpQ8Kb0p31hekvhiEBzS4yj_w", //15-22-29/06/2021
            "11ozxzipNGmx5GK2gLXaYFpZOlBMx7KQ30aQsuX74RWA", // 14-21-28/07/2021
            "1X_zBaQKaNXiQPuSbjWIXb1yWmOcavcbIuZix7eSB8Ew", //09-16-23/07/2021
            "1xDb7EBPP2iawB24-0P_1uYVjH6pbKnFAl3VnVzP9HCU", //29/09/2021-06-13/10/2021
            "11R9B7bB1fK0851jehQLsL4TeMEbUfywbSlb5zy49qpc", //21-28/10/2021
            "1RrSjh4wdBiJOQUJUnpOF1sWY_xwrWfu3jFnn5XVwbqM", //14-21-28/09/2021
        };
        String[] iscrittiv2 = {
            "1crjWiXjIKsT5PHkM_Nh8onbkGLf8ZRIK6VHYzMfyKuQ", //20-27/10/2021 09/11/2021
            "1MbsoIz64GQb6IuauBfPVW6xciFGfK9Eq7ILfliherQc", //15-22-29/10/2021
            "11R9B7bB1fK0851jehQLsL4TeMEbUfywbSlb5zy49qpc", //21-28/10/2021
        };
        int formato[] = { 14, 11, 15, 13, 5 }; // sono cambiate le colonne del fil e di goobgle

        for (String element : iscritti) {
            gs.getIscritti(element, null);
        }

        for (String element : iscrittiv2) {
            gs.getIscritti(element, formato);
        }

        /**
         * Poi i dati di goto webinar
         */
        gtb.getIscritti();

        bg.InsertCustomers();

        return 0;
    }

    /**
     * @return
     */
    public abstract Cliente getMe();

    /**
     * carica i clienti potenziali.
     * @return
     */
    @Deprecated
    public abstract int syncProspect();
}
