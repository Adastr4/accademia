package net.accademia.dolibarr;

import org.springframework.http.ResponseEntity;

public class DataSource {

    protected ResponseEntity<String> ret;
    protected DemoneMediator dm;

    public DataSource(DemoneMediator dm) {
        this.dm = dm;
    }
}
