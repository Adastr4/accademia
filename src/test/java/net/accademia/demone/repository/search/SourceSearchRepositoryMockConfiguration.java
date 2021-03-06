package net.accademia.demone.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SourceSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SourceSearchRepositoryMockConfiguration {

    @MockBean
    private SourceSearchRepository mockSourceSearchRepository;
}
