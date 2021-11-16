package net.accademia.dolibarr;

public class AccademiaDolibarrBridge extends WebinarDolibarrBridge {

    public AccademiaDolibarrBridge(AccademiaDemoneMediator dm) throws Exception {
        super(dm);
        uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php";
        DolibarrKey = "eYcv14S8BXMzfEuhS74u1p59K5JoQu4F";
        headers.set("DOLAPIKEY", DolibarrKey);
    }
}
