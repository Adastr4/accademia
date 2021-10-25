package net.accademia.dolibarr;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DolibarrTest {

    /**
     * Andrea 25/10/2021
     * nella prima versione legge le fatture
     * poi inserisce un cliente
     * restituisce le fatture
     *
     */
    @Test
    void InsertCustomer() {
        final String uri = "https://www.accademiaeuropa.it/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC&limit=100";
        //final String uri = "https://localhost:8181/dolibarr/api/index.php/invoices?sortfield=t.rowid&sortorder=ASC&limit=100";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("DOLAPIKEY", "VR576iFzqo5Q4Y6CEUgz01Ag0QQmelt0");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        //HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<String> ret = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        //    Object result = restTemplate.getForObject(uri, Object.class);

        System.out.println(ret);
    }
}
