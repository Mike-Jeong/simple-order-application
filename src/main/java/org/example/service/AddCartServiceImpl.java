package org.example.service;

import org.example.domain.Product;
import org.example.exception.CustomException;
import org.example.repository.ProductRepository;
import org.example.type.CustomError;

import java.util.List;
import java.util.Map;

public class AddCartServiceImpl implements AddCartService {

    private final ProductRepository productRepository;

    public AddCartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findProduct(Integer productNumber) {
        return productRepository.find(productNumber)
                .orElseThrow(() -> new CustomException(CustomError.PRODUCT_NOT_FOUND));
    }

    @Override
    public void addCart(Map<Integer, Integer> cart, Product product, Integer quantity) {

        validateInput(quantity);
        validateQuantity(cart, product, quantity);
        cart.put(product.getProductId(), cart.getOrDefault(product.getProductId(), 0) + quantity);
    }

    private void validateInput(Integer quantity) {
        if (quantity < 1) {
            throw new CustomException(CustomError.QUANTITY_NOT_ACCEPTABLE);
        }
    }

    private void validateQuantity(Map<Integer, Integer> cart, Product product, Integer quantity) {
        if (product.getAvailableStock() < cart.getOrDefault(product.getProductId(), 0) + quantity) {
            throw new CustomException(CustomError.SOLD_OUT);
        }
    }

}