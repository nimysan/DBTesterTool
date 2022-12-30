package top.cuteworld.show.cutestore.model.domain.behavior.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCount {
    private Long count;
    private Long productId;
}
