package top.cuteworld.show.cutestore.model.domain.metrics;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IpParseResult {

    private String dns;
    private String ip;
    private Long timestamp;


}
