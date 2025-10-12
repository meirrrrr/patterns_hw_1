package com.ecommerce;

import com.ecommerce.interfaces.PaymentMethod;
import com.ecommerce.interfaces.DiscountStrategy;

import com.ecommerce.implementations.CreditCardPayment;
import com.ecommerce.implementations.PayPalPayment;

import com.ecommerce.models.Product;
import com.ecommerce.models.Cart;
import com.ecommerce.models.ProductBuilder;
import com.ecommerce.models.PercentageDiscount;
import com.ecommerce.models.FixedAmountDiscount;

import com.ecommerce.factories.DiscountStrategyFactory;
import com.ecommerce.factories.PercentageDiscountFactory;
import com.ecommerce.factories.FixedDiscountFactory;

import com.ecommerce.services.PaymentService;
import com.ecommerce.utils.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class ECommerceDemo {
    public static void main(String[] args) {
        // instance of our logger
        Logger logger = Logger.getInstance();
        // out self designed logger
        logger.log("=== E-commerce SOLID Principles Demo ===\n");

        // now it's very easy to create new Product with our new ProductBuilder()
        // cause, it's all in one format, and easy to identify how it works, and will be informative for other developers
        // ))))
        Product laptop = new ProductBuilder()
                .setId("1")
                .setName("Laptop")
                .setPrice(new BigDecimal("999.99"))
                .build();
        Product mouse = new ProductBuilder()
                .setId("2")
                .setName("Mouse")
                .setPrice(new BigDecimal("29.99"))
                .build();
        Product keyboard = new ProductBuilder()
                .setId("3")
                .setName("Keyboard")
                .setPrice(new BigDecimal("79.99"))
                .build();


        Cart cart = new Cart();
        cart.addItem(laptop);
        cart.addItem(mouse);
        cart.addItem(keyboard);

        logger.log("Cart items: " + cart.getItemCount());
        logger.log("Original total: $" + cart.getTotalAmount());

        logger.log("\n=== Applying Discounts ===");
        DiscountStrategyFactory percentageFactory = new PercentageDiscountFactory(new BigDecimal("10"));
        DiscountStrategy percentDiscount = percentageFactory.getDiscountStrategy();
        BigDecimal discountedTotal1 = cart.applyDiscount(percentDiscount);
        logger.log("After " + percentDiscount.getDiscountDescription() + ": $" + discountedTotal1);

        DiscountStrategyFactory fixedFactory = new FixedDiscountFactory(new BigDecimal("50"));
        DiscountStrategy fixedDiscount = fixedFactory.getDiscountStrategy();
        BigDecimal discountedTotal2 = cart.applyDiscount(fixedDiscount);
        logger.log("After " + fixedDiscount.getDiscountDescription() + ": $" + discountedTotal2);
        logger.log("\n=== Processing Payments ===");

        PaymentMethod creditCard = new CreditCardPayment();
        PaymentService paymentService = new PaymentService(creditCard);

        Map<String, Object> creditCardDetails = new HashMap<>();
        creditCardDetails.put("cardNumber", "1234-5678-9012-3456");
        creditCardDetails.put("cvv", "123");

        paymentService.processPayment(cart, creditCardDetails);

        PaymentMethod paypal = new PayPalPayment();
        paymentService.setPaymentMethod(paypal);

        Map<String, Object> paypalDetails = new HashMap<>();
        paypalDetails.put("email", "user@example.com");

        paymentService.processPayment(cart, paypalDetails);
    }
}
