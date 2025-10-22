package com.ecommerce;

import com.ecommerce.interfaces.PaymentMethod;
import com.ecommerce.interfaces.DiscountStrategy;
import com.ecommerce.interfaces.NotificationChannel;

import com.ecommerce.implementations.CreditCardPayment;
import com.ecommerce.implementations.PayPalPayment;
import com.ecommerce.implementations.EmailNotification;
import com.ecommerce.implementations.SMSNotification;
import com.ecommerce.implementations.PushNotification;

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

// Adapter Pattern imports
import com.ecommerce.thirdparty.StripePaymentGateway;
import com.ecommerce.thirdparty.SquarePaymentProcessor;
import com.ecommerce.adapters.StripePaymentAdapter;
import com.ecommerce.adapters.SquarePaymentAdapter;

// Bridge Pattern imports
import com.ecommerce.bridge.Notification;
import com.ecommerce.bridge.OrderConfirmationNotification;
import com.ecommerce.bridge.PaymentFailureNotification;
import com.ecommerce.bridge.PromotionalNotification;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class ECommerceDemo {
    public static void main(String[] args) {
        // instance of our logger
        Logger logger = Logger.getInstance();
        // out self designed logger
        logger.log("=== E-commerce Design Patterns Demo ===\n");

        // Create products using Builder pattern
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

        logger.log("\n=== Applying Discounts (Strategy + Factory Patterns) ===");
        DiscountStrategyFactory percentageFactory = new PercentageDiscountFactory(new BigDecimal("10"));
        DiscountStrategy percentDiscount = percentageFactory.getDiscountStrategy();
        BigDecimal discountedTotal1 = cart.applyDiscount(percentDiscount);
        logger.log("After " + percentDiscount.getDiscountDescription() + ": $" + discountedTotal1);

        DiscountStrategyFactory fixedFactory = new FixedDiscountFactory(new BigDecimal("50"));
        DiscountStrategy fixedDiscount = fixedFactory.getDiscountStrategy();
        BigDecimal discountedTotal2 = cart.applyDiscount(fixedDiscount);
        logger.log("After " + fixedDiscount.getDiscountDescription() + ": $" + discountedTotal2);

        logger.log("\n=== Payment Processing with Adapter Pattern ===");

        // original payment
        PaymentMethod creditCard = new CreditCardPayment();
        PaymentService paymentService = new PaymentService(creditCard);

        Map<String, Object> creditCardDetails = new HashMap<>();
        creditCardDetails.put("cardNumber", "1234-5678-9012-3456");
        creditCardDetails.put("cvv", "123");
        paymentService.processPayment(cart, creditCardDetails);

        // adapter Pattern - Stripe payment gateway
        logger.log("\n--- Using Stripe via Adapter Pattern ---");
        StripePaymentGateway stripeGateway = new StripePaymentGateway();
        PaymentMethod stripeAdapter = new StripePaymentAdapter(stripeGateway);
        paymentService.setPaymentMethod(stripeAdapter);

        Map<String, Object> stripeDetails = new HashMap<>();
        stripeDetails.put("cardNumber", "4242-4242-4242-4242");
        stripeDetails.put("cvv", "456");
        stripeDetails.put("expiryMonth", "12");
        stripeDetails.put("expiryYear", "2025");
        paymentService.processPayment(cart, stripeDetails);

        // adapter Pattern - Square payment processor
        logger.log("\n--- Using Square via Adapter Pattern ---");
        SquarePaymentProcessor squareProcessor = new SquarePaymentProcessor();
        PaymentMethod squareAdapter = new SquarePaymentAdapter(squareProcessor, "MERCHANT_123");
        paymentService.setPaymentMethod(squareAdapter);

        Map<String, Object> squareDetails = new HashMap<>();
        squareDetails.put("cardNumber", "5555-5555-5555-4444");
        squareDetails.put("cvv", "789");
        paymentService.processPayment(cart, squareDetails);

        // Bridge Pattern
        logger.log("\n=== Notification System with Bridge Pattern ===");

        NotificationChannel emailChannel = new EmailNotification();
        NotificationChannel smsChannel = new SMSNotification();
        NotificationChannel pushChannel = new PushNotification();

        logger.log("\n--- Order Confirmation Notifications ---");

        // Email order confirmation
        Notification emailOrderConfirmation = new OrderConfirmationNotification(emailChannel);
        Map<String, Object> orderContext = new HashMap<>();
        orderContext.put("orderId", "ORD-12345");
        orderContext.put("totalAmount", cart.getTotalAmount().toString());
        emailOrderConfirmation.send("customer@example.com", orderContext);

        // SMS order confirmation
        Notification smsOrderConfirmation = new OrderConfirmationNotification(smsChannel);
        smsOrderConfirmation.send("+1234567890", orderContext);

        // Push order confirmation
        Notification pushOrderConfirmation = new OrderConfirmationNotification(pushChannel);
        pushOrderConfirmation.send("device_token_123", orderContext);

        // Payment failure notifications
        logger.log("\n--- Payment Failure Notifications ---");

        Notification emailPaymentFailure = new PaymentFailureNotification(emailChannel);
        Map<String, Object> failureContext = new HashMap<>();
        failureContext.put("orderId", "ORD-12346");
        failureContext.put("failureReason", "Insufficient funds");
        emailPaymentFailure.send("customer@example.com", failureContext);

        // Promotional notifications
        logger.log("\n--- Promotional Notifications ---");

        Notification smsPromo = new PromotionalNotification(smsChannel);
        Map<String, Object> promoContext = new HashMap<>();
        promoContext.put("promoCode", "SAVE20");
        promoContext.put("discount", "20%");
        promoContext.put("expiryDate", "Dec 31, 2024");
        smsPromo.send("+1234567890", promoContext);

        // Demonstrate runtime channel switching (Bridge pattern flexibility)
        logger.log("\n--- Runtime Channel Switching ---");
        Notification flexibleNotification = new PromotionalNotification(emailChannel);
        flexibleNotification.send("customer@example.com", promoContext);

        // Switch to push notification at runtime
        flexibleNotification.setChannel(pushChannel);
        flexibleNotification.send("device_token_456", promoContext);

        logger.log("\n=== Demo Complete ===");
        logger.log("Patterns demonstrated:");
        logger.log("✓ Builder Pattern (Product creation)");
        logger.log("✓ Strategy Pattern (Discount strategies)");
        logger.log("✓ Factory Pattern (Discount factories)");
        logger.log("✓ Singleton Pattern (Logger)");
        logger.log("✓ Adapter Pattern (Third-party payment gateways)");
        logger.log("✓ Bridge Pattern (Notification system)");
    }
}
