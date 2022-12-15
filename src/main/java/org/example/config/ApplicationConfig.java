package org.example.config;

import org.example.controller.ProductOrderController;
import org.example.controller.QuitController;
import org.example.repository.ProductRepository;
import org.example.repository.ProductRepositoryImpl;
import org.example.service.AddCartService;
import org.example.service.AddCartServiceImpl;
import org.example.service.PaymentService;
import org.example.service.PaymentServiceImpl;
import org.example.util.DBTransactionManager;
import org.example.util.HibernateManager;

public class ApplicationConfig {
    private static ProductOrderController productOrderController;
    private static QuitController quitController;
    private static ProductRepository productRepository;
    private static PaymentService paymentService;
    private static AddCartService addCartService;
    private static DBTransactionManager dbTransactionManager;

    public static synchronized ProductOrderController getProductOrderController() {

        if (productOrderController == null) {
            productOrderController = new ProductOrderController(ApplicationConfig.getAddCartService(), ApplicationConfig.getPaymentService());
        }

        return productOrderController;
    }

    public static synchronized QuitController getQuitController() {

        if (quitController == null) {
            quitController = new QuitController();
        }

        return quitController;
    }

    public static synchronized ProductRepository getProductRepository() {

        if (productRepository == null) {
            productRepository = new ProductRepositoryImpl(ApplicationConfig.getDbTransactionManager());
        }

        return productRepository;
    }

    public static synchronized PaymentService getPaymentService() {

        if (paymentService == null) {
            paymentService = new PaymentServiceImpl(ApplicationConfig.getProductRepository());
        }

        return paymentService;
    }

    public static synchronized AddCartService getAddCartService() {

        if (addCartService == null) {
            addCartService = new AddCartServiceImpl(ApplicationConfig.getProductRepository());
        }

        return addCartService;
    }

    public static synchronized DBTransactionManager getDbTransactionManager() {

        if (dbTransactionManager == null) {
            dbTransactionManager = HibernateManager.getInstance();
        }

        return dbTransactionManager;
    }

}
