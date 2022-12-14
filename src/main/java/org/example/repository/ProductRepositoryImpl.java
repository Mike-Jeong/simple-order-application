package org.example.repository;

import org.example.domain.Product;
import org.example.exception.CustomException;
import org.example.type.CustomError;
import org.example.util.DBTransactionManager;

import javax.persistence.LockModeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private final DBTransactionManager dbTransactionManager;

    public ProductRepositoryImpl(DBTransactionManager dbTransactionManager) {
        this.dbTransactionManager = dbTransactionManager;
    }

    @Override
    public List<Product> findAll() {
        return dbTransactionManager.getEntityManager()
                .createQuery("select p from Product p", Product.class)
                .getResultList();
    }

    @Override
    public Optional<Product> find(Integer id) {

        return Optional.ofNullable(dbTransactionManager.getEntityManager()
                .find(Product.class, id));
    }

    @Override
    public synchronized List<Product> updateProducts(Map<Integer, Integer> cart) {

        List<Product> list = new ArrayList<>();

        dbTransactionManager.transactionBegin();

        try {
            for (Integer productNumber : cart.keySet()) {

                Product product = find(productNumber)
                        .orElseThrow(() -> new CustomException(CustomError.PRODUCT_NOT_FOUND));

                dbTransactionManager.getEntityManager().lock(product, LockModeType.PESSIMISTIC_READ);

                validateQuantity(cart, product);

                product.updateAvailableStock(product.getAvailableStock() - cart.get(productNumber));
                list.add(product);
            }
            dbTransactionManager.transactionCommit();

        } catch (CustomException e) {
            dbTransactionManager.transactionRollback();
            throw e;
        }
        return list;
    }

    private void validateQuantity(Map<Integer, Integer> cart, Product product) {
        if (product.getAvailableStock() < cart.get(product.getProductId())) {
            throw new CustomException(CustomError.SOLD_OUT);
        }
    }

}