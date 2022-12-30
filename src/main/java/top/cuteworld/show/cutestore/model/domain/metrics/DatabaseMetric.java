package top.cuteworld.show.cutestore.model.domain.metrics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatabaseMetric {
    private String hostName;
    private String ip;
    private int threadCounts;
    private Long timestamp;
}
