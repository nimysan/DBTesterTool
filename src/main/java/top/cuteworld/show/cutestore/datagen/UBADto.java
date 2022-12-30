package top.cuteworld.show.cutestore.datagen;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UBADto {
    private Long productId;
    private String sessionId;
    private Long viewTime;
}
