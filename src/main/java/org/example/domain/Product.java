package org.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    private Integer productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private int productPrice;

    @Column(nullable = false)
    private int availableStock;

    public Product() {}

    public Product(Integer productId, String productName, Integer productPrice, Integer availableStock) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.availableStock = availableStock;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getAvailableStock() {
        return availableStock;
    }

}
