package org.example.service;

import org.example.domain.Product;

import java.util.List;
import java.util.Map;


public interface AddCartService {
    List<Product> findAllProduct();

    Product findProduct(Integer productNumber);

    void addCart(Map<Integer, Integer> cart, Product product, Integer quantity);
}
