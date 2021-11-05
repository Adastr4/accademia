package net.accademia.dolibarr;

public class AccademiaDolibarrBridge extends WebinarDolibarrBridge {

    public AccademiaDolibarrBridge(AccademiaDemoneMediator dm) {
        super(dm);
        uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php";
        DolibarrKey = "VR576iFzqo5Q4Y6CEUgz01Ag0QQmelt0";
        headers.set("DOLAPIKEY", DolibarrKey);
    }
}
