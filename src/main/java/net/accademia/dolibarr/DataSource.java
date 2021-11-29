package net.accademia.dolibarr;

import org.springframework.http.ResponseEntity;

public class DataSource {

    protected ResponseEntity<String> ret;
    protected DemoneMediator dm;

    public DataSource(DemoneMediator dm) throws Exception {
        if (dm == null) throw new Exception("DemoneMediator could not be null");
        this.dm = dm;
    }
}
