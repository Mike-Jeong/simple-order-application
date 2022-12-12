package org.example.repository;

import org.example.domain.Product;
import org.example.util.DBTransactionManager;

import java.util.List;
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

}