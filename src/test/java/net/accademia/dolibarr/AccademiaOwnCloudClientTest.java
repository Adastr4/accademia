package net.accademia.dolibarr;

import eu.cartsc.demone.OwnCloudBridge;
import java.util.List;
import javax.persistence.EntityManager;
import net.accademia.demone.DemoneApp;
import net.accademia.demone.IntegrationTest;
import net.accademia.demone.domain.Source;
import net.accademia.demone.repository.SourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DemoneApp.class })
@IntegrationTest
//@SpringBootApplication()
@ActiveProfiles("test")
class AccademiaOwnCloudClientTest {

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private EntityManager em;

    OwnCloudBridge coct = new AccademiaOwnCloudBridge();

    @Test
    void test() {
        try {
            List<Source> sources = sourceRepository.findAll();

            Source source = new Source();
            source.setId(100l);
            source.setSourceid("2021 - Accademia Europea/amministrazione/erpdata");
            Source result = sourceRepository.save(source);

            coct.getFiles("2021 - Accademia Europea/amministrazione/erpdata");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
