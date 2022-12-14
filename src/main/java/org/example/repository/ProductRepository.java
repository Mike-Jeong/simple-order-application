package org.example.repository;

import org.example.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> find(Integer id);

    List<Product> updateProducts(Map<Integer, Integer> cart);
}
