package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AccademiaDemoneMediatorMock extends AccademiaDemoneMediator {

    List<Registrant> r = new ArrayList<Registrant>();

    @Override
    public List<Registrant> getAllRegistrantsForWebinar(String webinarKey) throws ApiException {
        return r;
    }

    public AccademiaDemoneMediatorMock() {
        super();
        for (int i = 0; i < 100; i++) {
            Registrant re = new Registrant();
            re.setEmail("pippo" + i + "@gmail.com");
            r.add(re);
        }
    }

    public void fillWebinars() {
        gtb.getWebinars();
        ((AccademiaDolibarrBridge) bg).insertWebinar();
    }

    /**
     * verifica delle importazioni parziali
     *
     * @return
     */
    int SyncIscrittitoDolibarr() {
        // SyncIscrittitoDolibarrODS();
        // SyncIscrittitoDolibarrXLS();
        SyncIscrittitoDolibarrGOOGLESHEET();

        // SyncIscrittitoDolibarrGotowebinar();
        return 0;
    }

    private int SyncIscrittitoDolibarrGotowebinar() {
        /**
         * Poi i dati di goto webinar
         */
        gtb.getIscritti();

        bg.InsertCustomers();

        return 0;
    }

    private int SyncIscrittitoDolibarrGOOGLESHEET() {
        String[] iscritti = {
            "10hI-OeiU1huDcO2Z0Aq6ibwVPQlz3jbG2aQhJcMn-AY",
            "1S0m1x5j9sxZyCtflqJs8pV2NIFMrwUb3GlLhGzDRGEQ",
            "1pAT4iJZISaSjdjWUczjpQ8Kb0p31hekvhiEBzS4yj_w",
            "11ozxzipNGmx5GK2gLXaYFpZOlBMx7KQ30aQsuX74RWA",
            "1xDb7EBPP2iawB24-0P_1uYVjH6pbKnFAl3VnVzP9HCU",
            "11R9B7bB1fK0851jehQLsL4TeMEbUfywbSlb5zy49qpc",
            "1RrSjh4wdBiJOQUJUnpOF1sWY_xwrWfu3jFnn5XVwbqM",
        };
        for (String element : iscritti) {
            clienti.clear();
            gs.getIscritti(element, null);
        }

        String[] iscrittiv2 = {
            "1crjWiXjIKsT5PHkM_Nh8onbkGLf8ZRIK6VHYzMfyKuQ",
            "1MbsoIz64GQb6IuauBfPVW6xciFGfK9Eq7ILfliherQc",
            "11R9B7bB1fK0851jehQLsL4TeMEbUfywbSlb5zy49qpc",
        };
        int formato[] = { 14, 11, 15, 13, 5 }; // sono cambiate le colonne del fil e di goobgle
        for (String element : iscrittiv2) {
            clienti.clear();
            gs.getIscritti(element, formato);
        }

        bg.InsertCustomers();

        return 0;
    }

    int SyncIscrittitoDolibarrXLS() {
        xb.readXLS(new File("/home/adastra/ISCRIZIONE WEBINAR20210224.xlsx"));

        bg.InsertCustomers();

        return 0;
    }

    int SyncIscrittitoDolibarrODS() {
        /**
         * Prima i dati legacy
         *
         */
        // odb.readODS(new File("/home/adastra/iscrizionewebinar.ods"));
        odb.readODS(new File("/home/adastra/iscrizionewebinar.ods"));

        bg.InsertCustomers();

        return 0;
    }

    public void insertWebinar() {
        gtb.getWebinars();
        ((AccademiaDolibarrBridge) bg).insertWebinar();
    }
}
