package cloud.adastraiot.demone;

import net.accademia.dolibarr.DemoneMediator;
import net.accademia.dolibarr.DolibarrBridge;

public class AdastraDolibarrBridge extends DolibarrBridge {

    public AdastraDolibarrBridge(DemoneMediator dm) {
        super(dm);
        uri = "https://www.adastraiot.cloud/erp/api/index.php";
        DolibarrKey = "A6P7I24pVHD27jVz0b6owzRHs2j0aNsC";
        headers.set("DOLAPIKEY", DolibarrKey);
    }

    @Override
    public int insertInvoices() {
        // TODO Auto-generated method stub
        return 0;
    }
}
