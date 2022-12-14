package org.example.service;

import java.util.Map;

public interface PaymentService {
    Integer purchase(Map<Integer, Integer> cart, StringBuilder sb);

    Integer createBill(Integer amount, StringBuilder sb);

    String checkOut(Integer charge);
}
