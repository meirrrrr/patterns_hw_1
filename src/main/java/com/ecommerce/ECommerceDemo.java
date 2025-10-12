package com.ecommerce;

import com.ecommerce.models.Product;
import com.ecommerce.models.Cart;
import com.ecommerce.services.PaymentService;
import com.ecommerce.implementations.CreditCardPayment;
import com.ecommerce.implementations.PayPalPayment;
import com.ecommerce.models.PercentageDiscount;
import com.ecommerce.models.FixedAmountDiscount;
import com.ecommerce.factories.DiscountStrategyFactory;
import com.ecommerce.interfaces.PaymentMethod;
import com.ecommerce.interfaces.DiscountStrategy;
import com.ecommerce.factories.PercentageDiscountFactory;
import com.ecommerce.factories.FixedDiscountFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class ECommerceDemo {
    public static void main(String[] args) {
        System.out.println("=== E-commerce SOLID Principles Demo ===\n");

        Product laptop = new Product("1", "Laptop", new BigDecimal("999.99"));
        Product mouse = new Product("2", "Mouse", new BigDecimal("29.99"));
        Product keyboard = new Product("3", "Keyboard", new BigDecimal("79.99"));


        Cart cart = new Cart();
        cart.addItem(laptop);
        cart.addItem(mouse);
        cart.addItem(keyboard);

        System.out.println("Cart items: " + cart.getItemCount());
        System.out.println("Original total: $" + cart.getTotalAmount());

        System.out.println("\n=== Applying Discounts ===");
        DiscountStrategyFactory percentageFactory = new PercentageDiscountFactory(new BigDecimal("10"));
        DiscountStrategy percentDiscount = percentageFactory.getDiscountStrategy();
        BigDecimal discountedTotal1 = cart.applyDiscount(percentDiscount);
        System.out.println("After " + percentDiscount.getDiscountDescription() + ": $" + discountedTotal1);

        DiscountStrategyFactory fixedFactory = new FixedDiscountFactory(new BigDecimal("50"));
        DiscountStrategy fixedDiscount = fixedFactory.getDiscountStrategy();
        BigDecimal discountedTotal2 = cart.applyDiscount(fixedDiscount);
        System.out.println("After " + fixedDiscount.getDiscountDescription() + ": $" + discountedTotal2);
        System.out.println("\n=== Processing Payments ===");

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
