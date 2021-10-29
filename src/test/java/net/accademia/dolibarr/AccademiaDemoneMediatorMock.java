package net.accademia.dolibarr;

import com.logmein.gotowebinar.api.common.ApiException;
import com.logmein.gotowebinar.api.model.Registrant;
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
}
