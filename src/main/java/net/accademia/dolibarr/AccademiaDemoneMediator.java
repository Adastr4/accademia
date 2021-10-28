package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.model.Webinar;
import java.util.List;

public class AccademiaDemoneMediator extends DemoneMediator {

    @Override
    int SyncIscrittitoDolibarr() {
        // TODO Auto-generated method stub
        super.SyncIscrittitoDolibarr();
        ((AccademiaDolibarrBridge) bg).insertWebinar();
        bg.insertInvoices();
        return 1;
    }

    List<Webinar> webinars;

    public List<Webinar> getWebinars() {
        // TODO Auto-generated method stub
        return webinars;
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

    public AccademiaDemoneMediator() {
        super();
        bg = new AccademiaDolibarrBridge(this);
        gtb = new GotoWebinarBridge(this);
    }
}
