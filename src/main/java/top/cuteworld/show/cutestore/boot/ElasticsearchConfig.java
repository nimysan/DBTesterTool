package top.cuteworld.show.cutestore.boot;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String elasticsearchHost;

    @Bean
    public ElasticsearchClient client() {


// Create the low-level client
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();
        JacksonJsonpMapper jacksonJsonpMapper = new JacksonJsonpMapper();
        JavaTimeModule module = new JavaTimeModule();
        jacksonJsonpMapper.objectMapper().registerModule(module);
// Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, jacksonJsonpMapper);

// And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;

    }


}