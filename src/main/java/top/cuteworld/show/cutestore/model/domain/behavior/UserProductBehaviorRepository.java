package top.cuteworld.show.cutestore.model.domain.behavior;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import top.cuteworld.show.cutestore.model.domain.behavior.vo.ProductCount;

import java.util.List;

public interface UserProductBehaviorRepository extends CrudRepository<UserProductBehavior, Long> {
    /**
     * 访问统计
     *
     * @return
     */
    @Query(value = "select count(product_id) as count, product_id from user_behavior group by product_id order by  count(product_id) desc limit 10", nativeQuery = true)
    List<Object> queryTop10Products();
}

