package top.cuteworld.show.cutestore.model.domain.product;

import lombok.Builder;
import lombok.Data;
import top.cuteworld.show.cutestore.model.SaleChannel;
import top.cuteworld.show.cutestore.utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * 商品
 */
public class Product {

    public Product(String name, String description) {
        this.productName = name;
        this.description = description;
        this.createdAt = Utils.currentDate();
    }

    private Long id;
    private final String productName;
    private final String description;
    private List<SaleChannel> salesChannels;
    private final Date createdAt;
    private Date lastModifiedAt;
    private Long createdBy;
    /**
     * 在售、停售
     */
    private int status;
}
