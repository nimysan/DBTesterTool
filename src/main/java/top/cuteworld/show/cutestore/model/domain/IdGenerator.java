package top.cuteworld.show.cutestore.model.domain;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class IdGenerator {

    private Random random = new Random();

    public Long nextLong() {
        return Math.abs(random.nextLong());
    }
}
