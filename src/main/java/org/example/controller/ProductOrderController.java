package org.example.controller;

import org.example.domain.Product;
import org.example.service.AddCartService;

import java.util.List;

public class ProductOrderController implements Controller {

    private final AddCartService addCartService;

    public ProductOrderController(AddCartService addCartService) {
        this.addCartService = addCartService;
    }

    @Override
    public void process() {
        printProductList();
    }

    private void printProductList() {
        List<Product> list = addCartService.findAllProduct();
        System.out.printf("%-8s%-53s%-8s%s%n", "상품번호", "상품명", "판매가격", "재고수");
        list.forEach(p -> System.out.printf("%-10d%-53s%-10s%s\n", p.getProductId(), p.getProductName(), p.getProductPrice(), p.getAvailableStock()));
    }

}