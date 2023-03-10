package org.example.controller;

import org.example.domain.Product;
import org.example.exception.CustomException;
import org.example.service.AddCartService;
import org.example.service.PaymentService;
import org.example.util.InputManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductOrderController implements Controller {

    private final AddCartService addCartService;
    private final PaymentService paymentService;

    public ProductOrderController(AddCartService addCartService, PaymentService paymentService) {
        this.addCartService = addCartService;
        this.paymentService = paymentService;
    }

    @Override
    public void process() {
        printProductList();

        Map<Integer, Integer> cart = new HashMap<>();
        orderProduct(cart);

        if (cart.size() != 0) {
            purchaseProduct(cart);
        }
    }

    private void printProductList() {
        List<Product> list = addCartService.findAllProduct();
        System.out.printf("%-8s%-53s%-8s%s%n", "상품번호", "상품명", "판매가격", "재고수");
        list.forEach(p -> System.out.printf("%-10d%-53s%-10s%s\n", p.getProductId(), p.getProductName(), p.getProductPrice(), p.getAvailableStock()));
    }

    private void orderProduct(Map<Integer, Integer> cart) {

        while (true) {
            System.out.print("상품번호 : ");
            String productId = InputManager.scanInput();

            if (productId.equals(" ") || productId.equals("")) {
                break;
            }

            try {
                Integer productNumber = Integer.parseInt(productId);

                Product product = addCartService.findProduct(productNumber);

                System.out.print("수량 : ");
                Integer quantity = Integer.parseInt(InputManager.scanInput());

                addCartService.addCart(cart, product, quantity);

            } catch (NumberFormatException e) {
                System.out.println("숫자 형식만 입력해 주세요");
            } catch (CustomException e) {
                System.out.println(e.getErrorDescription());
            }
        }
    }

    private void purchaseProduct(Map<Integer, Integer> cart) {

        StringBuilder resultBuilder = new StringBuilder();
        Integer amount = paymentService.purchase(cart, resultBuilder);

        if (amount != null) {
            System.out.println("주문 내역:");
            System.out.println("------------------------------------------");
            System.out.println(resultBuilder);
            System.out.println("------------------------------------------");
            resultBuilder = new StringBuilder();
            Integer charge = paymentService.createBill(amount, resultBuilder);
            System.out.println(resultBuilder);
            System.out.println("------------------------------------------");
            System.out.println(paymentService.checkOut(charge));
            System.out.println("------------------------------------------");
            System.out.println();

        }
    }

}