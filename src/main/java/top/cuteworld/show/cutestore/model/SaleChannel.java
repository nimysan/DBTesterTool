package top.cuteworld.show.cutestore.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 销售渠道
 */
@Data
@Builder
public class SaleChannel {
    private int id;
    private String channel;
    private Date createdAt;
    private Date lastModifiedAt;
    private String description;
}
