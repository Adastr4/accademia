package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.model.Webinar;
import java.util.ArrayList;
import java.util.List;

public class DemoneMediator {

    List<Cliente> clienti;
    List<Contatto> contatti;
    List<Webinar> webinars;
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

    DemoneMediator() {
        clienti = new ArrayList<Cliente>();
        contatti = new ArrayList<Contatto>();

        bg = new DolibarrBridge(this);
        gs = new GoogleSheet(this);
        gtb = new GotoWebinarBridge(this);
        odb = new OdsBridge(this);
    }

    /**
     * prende la lista degli iscritti da google sheet e li inserisce su Dolibarr
     * verifica che gli iscritti siano gli stessi di gotowebinar
     *
     * @return
     */
    int SyncIscrittitoDolibarr() {
        String[] iscritti = { "1crjWiXjIKsT5PHkM_Nh8onbkGLf8ZRIK6VHYzMfyKuQ", "10hI-OeiU1huDcO2Z0Aq6ibwVPQlz3jbG2aQhJcMn-AY" };
        for (int i = 0; i < iscritti.length; i++) {
            gs.getIscritti(iscritti[i]);
        }
        return 0;
    }

    /**
     * crea una nuova form di google inserisce i dati nel calendario crea il nuovo
     * webinar inserisce gli iscritti su gotowebianr
     *
     * @return
     */
    int creaWebinar() {
        return 0;
    }

    public List<Webinar> getWebinars() {
        // TODO Auto-generated method stub
        return webinars;
    }
}
