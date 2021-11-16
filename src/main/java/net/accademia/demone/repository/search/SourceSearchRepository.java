package net.accademia.demone.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.stream.Stream;
import net.accademia.demone.domain.Source;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Source} entity.
 */
public interface SourceSearchRepository extends ElasticsearchRepository<Source, Long>, SourceSearchRepositoryInternal {}

interface SourceSearchRepositoryInternal {
    Stream<Source> search(String query);
}

class SourceSearchRepositoryInternalImpl implements SourceSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    SourceSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Stream<Source> search(String query) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return elasticsearchTemplate.search(nativeSearchQuery, Source.class).map(SearchHit::getContent).stream();
    }
}
