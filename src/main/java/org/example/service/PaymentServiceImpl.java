package org.example.service;

import org.example.domain.Product;
import org.example.exception.CustomException;
import org.example.repository.ProductRepository;

import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {

    private static final int MINIMUM_ORDER_VALUE = 50000;
    private static final int DELIVERY_FEE = 2500;

    private final ProductRepository productRepository;

    public PaymentServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Integer purchase(Map<Integer, Integer> cart, StringBuilder resultBuilder) {

        try {
            List<Product> list = productRepository.updateProducts(cart);
            int amount = 0;
            for (Product product : list) {

                amount += (product.getProductPrice() * cart.get(product.getProductId()));
                resultBuilder.append(String.format("%s - %d개", product.getProductName(), cart.get(product.getProductId())));
            }

            return amount;

        } catch (CustomException e) {
            System.out.println(e.getErrorDescription());
            return null;
        }
    }

    @Override
    public Integer createBill(Integer amount, StringBuilder resultBuilder) {

        Integer charge = amount;

        resultBuilder.append(String.format("주문 금액 : %,d원", amount));
        if (amount < MINIMUM_ORDER_VALUE) {
            charge += DELIVERY_FEE;
            resultBuilder.append(String.format("배송비 : %,d원", DELIVERY_FEE));
        }

        return charge;
    }

    @Override
    public String checkOut(Integer charge) {
        return String.format("지불 금액 : %,d원", charge);
    }
}
