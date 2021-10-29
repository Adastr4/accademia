package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class AccademiaDemoneMediator extends DemoneMediator {

    @Override
    int SyncIscrittitoDolibarr() {
        super.SyncIscrittitoDolibarr();
        ((AccademiaDolibarrBridge) bg).insertWebinar();
        bg.insertInvoices();
        return 1;
    }

    List<Webinar> webinars = new ArrayList<Webinar>();

    public List<Webinar> getWebinars() {
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

    public List<Registrant> getAllRegistrantsForWebinar(String webinarKey) throws ApiException {
        return gtb.getAllRegistrantsForWebinar(webinarKey);
    }

    public int insertInvoices() {
        gtb.getWebinars();
        return bg.insertInvoices();
    }
}
