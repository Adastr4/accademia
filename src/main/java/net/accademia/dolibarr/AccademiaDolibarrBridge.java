package net.accademia.dolibarr;

public class AccademiaDolibarrBridge extends WebinarDolibarrBridge {

    public AccademiaDolibarrBridge(AccademiaDemoneMediator dm) throws Exception {
        super(dm);
        uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php";
        DolibarrKey = "qI12t4QRktb9NAMSTVs7qkhU6cvZ3812";
        headers.set("DOLAPIKEY", DolibarrKey);
    }
}
