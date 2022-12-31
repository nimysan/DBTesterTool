package top.cuteworld.show.cutestore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class CutestoreApplication implements ApplicationRunner {

    public static void main(String[] args) {
        //禁用本地DNS缓存
        java.security.Security.setProperty("networkaddress.cache.ttl", "0");
        SpringApplication.run(CutestoreApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("start---");
    }
}
