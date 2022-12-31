package top.cuteworld.show.cutestore.endpoint.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.cuteworld.show.cutestore.application.UserBehaviorApplication;
import top.cuteworld.show.cutestore.datagen.UBAGenerator;
import top.cuteworld.show.cutestore.model.domain.behavior.vo.ProductCount;

import java.util.List;

@RestController
public class RestOperation {

    private final UserBehaviorApplication userBehaviorApplication;

    public RestOperation(UserBehaviorApplication userBehaviorApplication, UBAGenerator ubaGenerator) {
        this.userBehaviorApplication = userBehaviorApplication;
        this.ubaGenerator = ubaGenerator;
    }

    private final UBAGenerator ubaGenerator;

    @GetMapping("/sample/read")
    public List<ProductCount> readSample() {
        return userBehaviorApplication.report();
    }

    @GetMapping("/sample/write")
    public int wirteSample() {
        int size = 10;
        ubaGenerator.gen(size);
        return size;
    }

    @GetMapping("/uba/datagen")
    public void generate(@RequestParam int size) {
        ubaGenerator.gen(size);
//        userBehaviorApplication.viewProduct(1l, "2", System.currentTimeMillis());
    }

    @GetMapping("report/product/top10")
    public List<ProductCount> report() {
        return userBehaviorApplication.report();
    }
}
