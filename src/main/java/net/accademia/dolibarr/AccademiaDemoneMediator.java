package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import com.logmein.gotowebinar.api.model.Webinar;
import com.taskadapter.redmineapi.RedmineException;
import com.taskadapter.redmineapi.bean.Issue;
import eu.cartsc.demone.RedmineBridge;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.accademia.demone.AccademiaRedmineBridge;

public class AccademiaDemoneMediator extends DemoneMediator implements IWebinarMediator {

    List<Webinar> webinars = new ArrayList<>();

    public AccademiaDemoneMediator() {
        super();
        try {
            bg = new AccademiaDolibarrBridge(this);
            gtb = new AccademiaGotoWebinarBridge(this);
            gs = new AccademiaGoogleSheet(this);
            rmb = new AccademiaRedmineBridge();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    @Override
    public List<Registrant> getAllRegistrantsForWebinar(String webinarKey) throws ApiException {
        return gtb.getAllRegistrantsForWebinar(webinarKey);
    }

    @Override
    public List<Webinar> getWebinars() {
        return webinars;
    }

    @Override
    public int insertInvoices() {
        gtb.getWebinars();
        return bg.insertInvoices();
    }

    /**
     * 1) sincronizza i clienti e i contatti tra le fonti sottoelencate e il DB 1.1)
     * File Ods, File Excel, Google Sheet e Gotowebinar 2) prende i webinar da
     * gotowebinar e li inserisce su Dolibarr 3) crea le fatture
     */
    @Override
    public int SyncIscrittitoDolibarr() {
        super.SyncIscrittitoDolibarr();
        gtb.getWebinars();
        ((WebinarDolibarrBridge) bg).insertWebinar();
        bg.insertInvoices();
        return 1;
    }

    @Override
    public Cliente getMe() {
        if (me == null) {
            me = new Cliente("accademiaeuropea@pec.it", "Accademia Europea Soc. Coop", "04533430403", "", "", "autogenerato");
            me.setId("1622");
        }
        return me;
    }

    /**
     *
     */
    @Override
    public int syncProspect() {
        return 0;
    }

    public void syncWebinar() {
        gtb.getWebinars();
        ((WebinarDolibarrBridge) bg).insertWebinar();
    }

    /**
     * esegue l'importazione di tutti i file inseriti nel ticket i ticket devono
     * essere assegnati a demone accademia
     *
     */
    @Override
    protected void importTasks() {
        List<Issue> ret = rmb.getMyIssue();
        if (ret == null) {
            return;
        }
        if (ret.size() == 0) {
            return;
        }
        try {
            for (Issue issue : ret) {
                if (importTask(issue) != 0) continue;

                issue.setStatusId(5);
                issue.setNotes("\r\neseguito con successo");

                issue.update();
            }
        } catch (RedmineException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /** importa la lista specificata nella tabella,
     * quello importato lo inserisce come fatto,
     * quello che non capisce non lo fa
     * @param issue
     * @return
     */
    private int importTask(Issue issue) {
        try {
            String descrizione = issue.getDescription();
            if (descrizione == null) return 1;
            descrizione = descrizione.substring(descrizione.indexOf('|'), descrizione.lastIndexOf('|'));

            Map<String, String> map = new LinkedHashMap<String, String>();
            for (String keyValue : descrizione.split("\\n")) {
                String[] pairs = keyValue.split("\\|", -1);
                map.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
            }
            String[] array = descrizione.split("\\|", -1);
            issue.setNotes("\r\npippo");
            issue.update();
            return 0;
        } catch (RedmineException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 1;
    }
}
