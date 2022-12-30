package top.cuteworld.show.cutestore.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UtilsTest {

    @Test
    void parseIpByOS() throws IOException {
        String domain = "for-upgrade-test.cypjqpec31mg.ap-southeast-1.rds.amazonaws.com";
        String ip = Utils.parseIpByOS(domain);
        log.info("The ip for {} is {}", domain, ip);
    }
}