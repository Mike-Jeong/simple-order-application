package org.example.repository;

import org.example.domain.Product;
import org.example.exception.CustomException;
import org.example.type.CustomError;
import org.example.util.DBTransactionManager;
import org.example.util.HibernateManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductRepositoryTest {

    ProductRepository productRepository = new ProductRepositoryImpl(HibernateManager.getInstance());

    @BeforeEach
    void setUp() {
        DBTransactionManager dbTransactionManager = HibernateManager.getInstance();
        dbTransactionManager.transactionBegin();

        try {
            Product product1 = new Product(1, "aaa", 12345, 20);
            Product product2 = new Product(2, "bbb", 67890, 30);
            Product product3 = new Product(3, "ccc", 13579, 100);
            dbTransactionManager.getEntityManager().persist(product1);
            dbTransactionManager.getEntityManager().persist(product2);
            dbTransactionManager.getEntityManager().persist(product3);

            dbTransactionManager.transactionCommit();

        } catch (Exception e) {
            dbTransactionManager.transactionRollback();
        }
    }

    @Test
    void findAllTest() {

        List<Product> list = productRepository.findAll();

        assertEquals(3, list.size());
    }

    @Test
    void find() {
        Product product = productRepository.find(1).orElseThrow();

        assertEquals(1, product.getProductId());
        assertEquals("aaa", product.getProductName());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void updateProducts(int input) {

        Map<Integer, Integer> cart = new HashMap<>();

        cart.put(1, 2);
        cart.put(2, 3);

        productRepository.updateProducts(cart);

        Product product1 = productRepository.find(input).orElseThrow();

        assertEquals(input, product1.getProductId());


    }

    @Test
    void updateProducts_WhenProductIsNotExist() {
        Map<Integer, Integer> cart = new HashMap<>();

        cart.put(1234, 2);

        CustomException exception = Assertions.assertThrows(CustomException.class,
                () -> productRepository.updateProducts(cart));

        assertEquals(CustomError.PRODUCT_NOT_FOUND.getDescription(), exception.getErrorDescription());

    }

    @Test
    void updateProducts_WhenProductStockIsLowerThanOrder() {
        Map<Integer, Integer> cart = new HashMap<>();

        cart.put(1, 200);

        CustomException exception = Assertions.assertThrows(CustomException.class,
                () -> productRepository.updateProducts(cart));

        assertEquals(CustomError.SOLD_OUT.getDescription(), exception.getErrorDescription());
    }


    //해당 테스트는 따로 실행해 주세요!
    @Test
    void updateProducts_WithMultiThread() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExcute = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExcute);

        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                try {
                    Map<Integer, Integer> cart = new HashMap<>();
                    cart.put(2, 2);
                    cart.put(3, 3);
                    productRepository.updateProducts(cart);
                    successCount.getAndIncrement();
                } catch (CustomException e) {
                    System.out.println(e.getErrorDescription());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        CountDownLatch latch2 = new CountDownLatch(numberOfExcute);
        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                try {
                    Map<Integer, Integer> cart = new HashMap<>();
                    cart.put(2, 2);
                    cart.put(3, 50);
                    productRepository.updateProducts(cart);
                    successCount.getAndIncrement();
                } catch (CustomException e) {
                    System.out.println(e.getErrorDescription());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                latch2.countDown();
            });
        }
        latch2.await();

        Product product2 = productRepository.find(2).orElseThrow();

        assertEquals(8, product2.getAvailableStock());
    }

}