package top.cuteworld.show.cutestore.model.domain.httptrace;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.transform.Settings;
import co.elastic.clients.util.ObjectBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;
import top.cuteworld.show.cutestore.model.domain.IdGenerator;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Repository
@Slf4j
public class ESHttpTraceRepository implements HttpTraceRepository {

    private final ElasticsearchClient esClient;

    ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private final IdGenerator idGenerator;


    public ESHttpTraceRepository(ElasticsearchClient esClient, IdGenerator idGenerator) {
        this.esClient = esClient;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<HttpTrace> findAll() {
        return Collections.emptyList();
    }

    @Override
    public void add(HttpTrace trace) {

        try {
            IndexResponse response = esClient.index(i -> i.index("http-logs-2").document(new HttpTraceWarp(trace))

            );
//            log.info("Indexed with version " + response.version());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class HttpTraceWarp {

        private volatile Date timestamp;

        private volatile HttpTrace.Principal principal;

        private volatile HttpTrace.Session session;

        private volatile HttpTrace.Request request;

        private volatile HttpTrace.Response response;

        private volatile Long timeTaken;

        public Date getTimestamp() {
            return timestamp;
        }

        public HttpTrace.Principal getPrincipal() {
            return principal;
        }

        public void setPrincipal(HttpTrace.Principal principal) {
            this.principal = principal;
        }

        public HttpTrace.Session getSession() {
            return session;
        }

        public void setSession(HttpTrace.Session session) {
            this.session = session;
        }

        public HttpTrace.Request getRequest() {
            return request;
        }

        public HttpTrace.Response getResponse() {
            return response;
        }

        public void setResponse(HttpTrace.Response response) {
            this.response = response;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public void setRequest(HttpTrace.Request request) {
            this.request = request;
        }

        public Long getTimeTaken() {
            return timeTaken;
        }

        public void setTimeTaken(Long timeTaken) {
            this.timeTaken = timeTaken;
        }

        public HttpTraceWarp(HttpTrace sourceTrace) {
            this.timestamp = new Date(sourceTrace.getTimestamp().toEpochMilli());
            this.principal = sourceTrace.getPrincipal();
            this.request = sourceTrace.getRequest();
            this.response = sourceTrace.getResponse();
            this.timeTaken = sourceTrace.getTimeTaken();
//            this.startNanoTime = sourceTrace.get
        }
    }

}
