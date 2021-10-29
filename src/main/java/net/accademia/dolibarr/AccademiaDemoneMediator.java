package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import java.util.ArrayList;
import java.util.List;

public class AccademiaDemoneMediator extends DemoneMediator {

    List<Webinar> webinars = new ArrayList<Webinar>();

    public AccademiaDemoneMediator() {
        super();
        bg = new AccademiaDolibarrBridge(this);
        gtb = new GotoWebinarBridge(this);
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

    public List<Registrant> getAllRegistrantsForWebinar(String webinarKey) throws ApiException {
        return gtb.getAllRegistrantsForWebinar(webinarKey);
    }

    public List<Webinar> getWebinars() {
        return webinars;
    }

    public int insertInvoices() {
        gtb.getWebinars();
        return bg.insertInvoices();
    }

    @Override
    int SyncIscrittitoDolibarr() {
        super.SyncIscrittitoDolibarr();
        gtb.getWebinars();
        ((AccademiaDolibarrBridge) bg).insertWebinar();
        bg.insertInvoices();
        return 1;
    }
}
