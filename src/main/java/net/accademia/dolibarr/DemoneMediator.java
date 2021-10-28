package net.accademia.dolibarr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class DemoneMediator {

    List<Cliente> clienti;
    List<Contatto> contatti;

    DolibarrBridge bg = null;
    GoogleSheet gs = null;
    GotoWebinarBridge gtb = null;
    OdsBridge odb = null;
    XLSBridge xb = null;

    public List<Cliente> getClienti() {
        return clienti;
    }

    public void setClienti(List<Cliente> clienti) {
        this.clienti = clienti;
    }

    public List<Contatto> getContatti() {
        return contatti;
    }

    public void setContatti(List<Contatto> contatti) {
        this.contatti = contatti;
    }

    protected DemoneMediator() {
        clienti = new ArrayList<>();
        contatti = new ArrayList<>();

        bg = new DolibarrBridge(this);
        gs = new GoogleSheet(this);

        odb = new OdsBridge(this);
        xb = new XLSBridge(this);
    }

    /**
     * prende la lista degli iscritti da google sheet e li inserisce su Dolibarr
     * verifica che gli iscritti siano gli stessi di gotowebinar
     *
     * @return
     */
    int SyncIscrittitoDolibarr() {
        /**
         * Prima i dati legacy
         *
         * */
        odb.readODS(new File("/home/adastra/iscrizionewebinar.ods"));
        xb.readXLS(new File("/home/adastra/ISCRIZIONE WEBINAR20210224.xlsx"));

        /**
         * Poi i dati dei form di Google
         *
         * */

        String[] iscritti = {
            "10hI-OeiU1huDcO2Z0Aq6ibwVPQlz3jbG2aQhJcMn-AY",
            "1S0m1x5j9sxZyCtflqJs8pV2NIFMrwUb3GlLhGzDRGEQ",
            "1crjWiXjIKsT5PHkM_Nh8onbkGLf8ZRIK6VHYzMfyKuQ",
            "1pAT4iJZISaSjdjWUczjpQ8Kb0p31hekvhiEBzS4yj_w",
            "11ozxzipNGmx5GK2gLXaYFpZOlBMx7KQ30aQsuX74RWA",
            "1xDb7EBPP2iawB24-0P_1uYVjH6pbKnFAl3VnVzP9HCU",
            "11R9B7bB1fK0851jehQLsL4TeMEbUfywbSlb5zy49qpc",
        };
        for (String element : iscritti) {
            gs.getIscritti(element, null);
        }

        String[] iscrittiv2 = { "1MbsoIz64GQb6IuauBfPVW6xciFGfK9Eq7ILfliherQc" };
        int formato[] = { 12, 10, 15 };
        for (String element : iscrittiv2) {
            gs.getIscritti(element, formato);
        }

        /**
         * Poi i dati di goto webinar
         * */
        gtb.getIscritti();

        bg.InsertCustomers();
        bg.insertContacts();

        return 0;
    }
}
