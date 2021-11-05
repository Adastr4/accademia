package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import java.util.List;

public interface IWebinarMediator {
    List<Registrant> getAllRegistrantsForWebinar(String webinarKey) throws ApiException;

    List<Webinar> getWebinars();

    int insertInvoices();

    /**
     * 1) sincronizza i clienti e i contatti tra le fonti sottoelencate e il DB 1.1)
     * File Ods, File Excel, Google Sheet e Gotowebinar 2) prende i webinar da
     * gotowebinar e li inserisce su Dolibarr 3) crea le fatture
     */
    int SyncIscrittitoDolibarr();

    Cliente getMe();
}
