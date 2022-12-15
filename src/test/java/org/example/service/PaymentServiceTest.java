package org.example.service;

import org.example.domain.Product;
import org.example.exception.CustomException;
import org.example.repository.ProductRepository;
import org.example.type.CustomError;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentServiceTest {

    PaymentService paymentService;

    @Test
    void purchase() {
        paymentService = new PaymentServiceImpl(
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

        Map<Integer, Integer> cart = new HashMap<>();
        cart.put(1, 1);
        cart.put(2, 1);
        Integer amount = paymentService.purchase(cart, new StringBuilder());

        assertEquals(3, amount);
    }

    @Test
    void purchase_WhenProductIsNotExist() {
        paymentService = new PaymentServiceImpl(
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
                        throw new CustomException(CustomError.PRODUCT_NOT_FOUND);
                    }
                }
        );

        Map<Integer, Integer> cart = new HashMap<>();
        cart.put(1, 1);
        cart.put(2, 1);
        Integer amount = paymentService.purchase(cart, new StringBuilder());

        assertNull(amount);
    }

    @Test
    void purchase_WhenProductStockIsLowerThanOrder() {
        paymentService = new PaymentServiceImpl(
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
                        throw new CustomException(CustomError.SOLD_OUT);
                    }
                }
        );

        Map<Integer, Integer> cart = new HashMap<>();
        cart.put(1, 1);
        cart.put(2, 1);
        Integer amount = paymentService.purchase(cart, new StringBuilder());

        assertNull(amount);
    }

    @Test
    void createBill_LessThanMinimumOrderValue() {
        paymentService = new PaymentServiceImpl(
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

        Integer amount = paymentService.createBill(10000, new StringBuilder());
        assertEquals(12500, amount);
    }

    @Test
    void createBill_MoreThanMinimumOrderValue() {
        paymentService = new PaymentServiceImpl(
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

        Integer amount = paymentService.createBill(100000, new StringBuilder());
        assertEquals(100000, amount);
    }
}