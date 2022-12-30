package top.cuteworld.show.cutestore.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.cuteworld.show.cutestore.application.UserBehaviorApplication;

/**
 *
 */
@Component
@Slf4j
@Order(1)
public class WebEndpoint implements ApplicationRunner {

    private final UserBehaviorApplication userBehaviorApplication;

    public WebEndpoint(UserBehaviorApplication userBehaviorApplication) {
        this.userBehaviorApplication = userBehaviorApplication;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Application running for webendpoint");
        userBehaviorApplication.viewProduct(1l, "gell", System.currentTimeMillis());
    }

//    public void
}
