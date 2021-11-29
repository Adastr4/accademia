package net.accademia.demone.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.stream.Stream;
import net.accademia.demone.domain.Error;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Error} entity.
 */
public interface ErrorSearchRepository extends ElasticsearchRepository<Error, Long>, ErrorSearchRepositoryInternal {}

interface ErrorSearchRepositoryInternal {
    Stream<Error> search(String query);
}

class ErrorSearchRepositoryInternalImpl implements ErrorSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ErrorSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Stream<Error> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return elasticsearchTemplate.search(nativeSearchQuery, Error.class).map(SearchHit::getContent).stream();
    }
}
