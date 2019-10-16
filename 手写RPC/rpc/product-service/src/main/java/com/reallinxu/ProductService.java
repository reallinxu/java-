package com.reallinxu;

import com.reallinxu.bean.Product;

public class ProductService implements IProductService {
    public Product queryById(long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("heihei");
        product.setPrice(1.0);
        return product;
    }
}
