package top.cuteworld.show.cutestore.datagen;

import top.cuteworld.show.cutestore.annotations.TestComponent;
import top.cuteworld.show.cutestore.application.UserBehaviorApplication;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 随机产生点击数据
 */
@TestComponent
public class UBAGenerator {

    private Random random = new Random();
    private RandomString randomString = new RandomString();

    private final UserBehaviorApplication userBehaviorApplication;

    public UBAGenerator(UserBehaviorApplication userBehaviorApplication) {
        this.userBehaviorApplication = userBehaviorApplication;
    }


    /**
     * 随机产生指定数量的记录
     *
     * @param count
     */
    public void gen(long count) {
        int poolSize = 10;
//        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
//
//
//        for (int i = 0; i < poolSize; i++) {
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
//        }
        Long[] products = products(100);
        String[] sessions = sessions(1000);
        int generated = 0;
        while (generated < count) {
            generateUBARecords(products, sessions);
            generated++;
        }
    }

    private Long[] products(int size) {
        Long[] productArray = new Long[size];
        for (int i = 0; i < size; i++) {
            productArray[i] = random.nextLong();
        }
        return productArray;
    }

    private String[] sessions(int size) {
        String[] sessionArray = new String[size];
        for (int i = 0; i < size; i++) {
            sessionArray[i] = randomString.getAlphaNumericString(25);
        }
        return sessionArray;
    }

    private void generateUBARecords(Long[] productIds, String[] sessionIds) {
        Double v = Math.random() * productIds.length;
        int pickProduct = v.intValue();

        Double s = Math.random() * sessionIds.length;
        int pickSession = s.intValue();
        Long productId = productIds[pickProduct];
        String sessionId = sessionIds[pickSession];
        UBADto uba = new UBADto.UBADtoBuilder().sessionId(sessionId).productId(productId).viewTime(System.currentTimeMillis()).build();
        userBehaviorApplication.viewProduct(uba.getProductId(), uba.getSessionId(), uba.getViewTime());
    }
}
