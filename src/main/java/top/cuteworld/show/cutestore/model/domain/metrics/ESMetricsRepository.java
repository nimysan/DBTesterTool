package top.cuteworld.show.cutestore.model.domain.metrics;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class ESMetricsRepository {

    public static final String IP_INDEX_NAME = "ip-parse-java";
    public static final String DB_INDEX_NAME = "db-info";


    private final ElasticsearchClient esClient;

    public ESMetricsRepository(ElasticsearchClient esClient) {
        this.esClient = esClient;
    }

    public void save(IpParseResult parseResult) {
        try {
            IndexResponse response = esClient.index(i -> i.index(IP_INDEX_NAME).document(parseResult));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDBInfo(DatabaseMetric metric) {
        try {
            IndexResponse response = esClient.index(i -> i.index(DB_INDEX_NAME).document(metric));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
