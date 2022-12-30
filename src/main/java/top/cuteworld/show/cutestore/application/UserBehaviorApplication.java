package top.cuteworld.show.cutestore.application;

import lombok.extern.slf4j.Slf4j;
import top.cuteworld.show.cutestore.annotations.CuteApplication;
import top.cuteworld.show.cutestore.model.domain.IdGenerator;
import top.cuteworld.show.cutestore.model.domain.behavior.UserProductBehavior;
import top.cuteworld.show.cutestore.model.domain.behavior.UserProductBehaviorRepository;
import top.cuteworld.show.cutestore.model.domain.behavior.vo.ProductCount;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户统计程序
 */
@CuteApplication
@Slf4j
public class UserBehaviorApplication {

    private final UserProductBehaviorRepository userProductBehaviorRepository;
    private final IdGenerator idGenerator;

    public UserBehaviorApplication(UserProductBehaviorRepository userProductBehaviorRepository, IdGenerator idGenerator) {
        this.userProductBehaviorRepository = userProductBehaviorRepository;
        this.idGenerator = idGenerator;
    }

    /**
     * 记录用户查看商品行为
     *
     * @param productId
     * @param sessionId
     */
    public UserProductBehavior viewProduct(Long productId, String sessionId, Long viewTime) {
        UserProductBehavior userProductBehavior = new UserProductBehavior(productId, sessionId, viewTime);
        userProductBehavior.setId(idGenerator.nextLong());
        UserProductBehavior persistedEntity = userProductBehaviorRepository.save(userProductBehavior);
        return persistedEntity;
    }

    /**
     * 读取请求，从uba库中读取数据
     */
    public List<ProductCount> report() {
        List<Object> objects = userProductBehaviorRepository.queryTop10Products();
        return objects.stream().map(new Function<Object, ProductCount>() {
            @Override
            public ProductCount apply(Object o) {
                Object[] array = (Object[]) o;
                return ProductCount.builder().count(((BigInteger)array[0]).longValue()).productId(((BigInteger)array[1]).longValue()).build();
            }
        }).collect(Collectors.toList());
    }
}
