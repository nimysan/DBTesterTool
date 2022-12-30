package top.cuteworld.show.cutestore.application;

import top.cuteworld.show.cutestore.model.domain.product.Product;

/**
 * 线上商店应用
 */
public class OnlineStoreApplication {

    /**
     * 发展新品
     */
    public void developNewProduct(String productName) {
        Product product = new Product(productName, "");
    }
}
