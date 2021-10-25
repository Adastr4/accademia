package net.accademia.dolibarr;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class DolibarrTest {

    /**
     * Andrea 25/10/2021
     * nella prima versione legge le fatture
     * poi inserisce un cliente
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
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        //HttpEntity<String> entity = new HttpEntity<>("body", headers);

        restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
    }
}
