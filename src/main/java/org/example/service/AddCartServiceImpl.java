package org.example.service;

import org.example.domain.Product;
import org.example.repository.ProductRepository;

import java.util.List;

public class AddCartServiceImpl implements AddCartService {

    private final ProductRepository productRepository;

    public AddCartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }
}