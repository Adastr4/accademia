package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.model.Webinar;
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
        clienti = new ArrayList<Cliente>();
        contatti = new ArrayList<Contatto>();

        bg = new DolibarrBridge(this);
        gs = new GoogleSheet(this);

        odb = new OdsBridge(this);
    }

    /**
     * prende la lista degli iscritti da google sheet e li inserisce su Dolibarr
     * verifica che gli iscritti siano gli stessi di gotowebinar
     *
     * @return
     */
    int SyncIscrittitoDolibarr() {
        String[] iscritti = {
            "1crjWiXjIKsT5PHkM_Nh8onbkGLf8ZRIK6VHYzMfyKuQ",
            "10hI-OeiU1huDcO2Z0Aq6ibwVPQlz3jbG2aQhJcMn-AY",
            "1MbsoIz64GQb6IuauBfPVW6xciFGfK9Eq7ILfliherQc",
        };
        for (int i = 0; i < iscritti.length; i++) {
            gs.getIscritti(iscritti[i]);
        }

        gtb.getIscritti();

        odb.readODS(new File("/home/adastra/iscrizionewebinar.ods"));
        bg.InsertCustomers();
        bg.insertContacts();

        return 0;
    }
}
