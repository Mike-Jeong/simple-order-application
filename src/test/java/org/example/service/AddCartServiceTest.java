package org.example.service;


import org.example.domain.Product;
import org.example.exception.CustomException;
import org.example.repository.ProductRepository;
import org.example.type.CustomError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCartServiceTest {

    AddCartService addCartService = new AddCartServiceImpl(
            new ProductRepository() {
                @Override
                public List<Product> findAll() {
                    return null;
                }

                @Override
                public Optional<Product> find(Integer id) {
                    return Optional.empty();
                }

                @Override
                public List<Product> updateProducts(Map<Integer, Integer> cart) {
                    List<Product> list = new ArrayList<>();

                    for (Integer i : cart.keySet()) {
                        list.add(new Product(i, "aaa", i, i));
                    }

                    return list;
                }
            }
    );

    @Test
    void findAllProduct() {
        addCartService = new AddCartServiceImpl(
                new ProductRepository() {
                    @Override
                    public List<Product> findAll() {
                        List<Product> list = new ArrayList<>();

                        for (int i = 1; i <= 3; i++) {
                            Product product = new Product(i, "aaa", i, i);
                            list.add(product);
                        }

                        return list;
                    }

                    @Override
                    public Optional<Product> find(Integer id) {
                        return Optional.empty();
                    }

                    @Override
                    public List<Product> updateProducts(Map<Integer, Integer> cart) {
                        return null;
                    }
                }
        );

        List<Product> list = addCartService.findAllProduct();

        assertEquals(3, list.size());
        assertEquals(1, list.get(0).getProductId());
    }

    @Test
    void findProduct() {
        addCartService = new AddCartServiceImpl(
                new ProductRepository() {
                    @Override
                    public List<Product> findAll() {
                        return null;
                    }

                    @Override
                    public Optional<Product> find(Integer id) {
                        return Optional.of(new Product(id, "aaa", id, id));
                    }

                    @Override
                    public List<Product> updateProducts(Map<Integer, Integer> cart) {
                        return null;
                    }
                }
        );

        Product product = addCartService.findProduct(5);

        assertEquals(5, product.getProductId());
        assertEquals(5, product.getAvailableStock());

    }

    @Test
    void findProduct_ProductNotFound() {
        addCartService = new AddCartServiceImpl(
                new ProductRepository() {
                    @Override
                    public List<Product> findAll() {
                        return null;
                    }

                    @Override
                    public Optional<Product> find(Integer id) {
                        return Optional.empty();
                    }

                    @Override
                    public List<Product> updateProducts(Map<Integer, Integer> cart) {
                        return null;
                    }
                }
        );

        CustomException exception = Assertions.assertThrows(CustomException.class,
                () -> addCartService.findProduct(5));

        assertEquals(CustomError.PRODUCT_NOT_FOUND.getDescription(), exception.getErrorDescription());

    }

    @Test
    void addCart_QuantityNotAcceptable() {
        addCartService = new AddCartServiceImpl(
                new ProductRepository() {
                    @Override
                    public List<Product> findAll() {
                        return null;
                    }

                    @Override
                    public Optional<Product> find(Integer id) {
                        return Optional.empty();
                    }

                    @Override
                    public List<Product> updateProducts(Map<Integer, Integer> cart) {
                        return null;
                    }
                }
        );

        Map<Integer, Integer> cart = new HashMap<>();
        Integer quantity = -7;
        Product product = new Product(5, "aaa", 5, -7);

        CustomException exception = Assertions.assertThrows(CustomException.class,
                () -> addCartService.addCart(cart, product, quantity));

        assertEquals(CustomError.QUANTITY_NOT_ACCEPTABLE.getDescription(), exception.getErrorDescription());
    }

    @Test
    void addCart_SoldOut() {
        addCartService = new AddCartServiceImpl(
                new ProductRepository() {
                    @Override
                    public List<Product> findAll() {
                        return null;
                    }

                    @Override
                    public Optional<Product> find(Integer id) {
                        return Optional.empty();
                    }

                    @Override
                    public List<Product> updateProducts(Map<Integer, Integer> cart) {
                        return null;
                    }
                }
        );

        Map<Integer, Integer> cart = new HashMap<>();
        Integer quantity = 7;
        Product product = new Product(5, "aaa", 5, 5);

        CustomException exception = Assertions.assertThrows(CustomException.class,
                () -> addCartService.addCart(cart, product, quantity));

        assertEquals(CustomError.SOLD_OUT.getDescription(), exception.getErrorDescription());
    }
}