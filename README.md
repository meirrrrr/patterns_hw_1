# E-commerce Shop - SOLID Principles Implementation

This project demonstrates all five SOLID principles through an e-commerce system implementation.

## Domain: E-commerce Shop

### System Flow

1. Customer browses products
2. Customer adds items to cart
3. Customer applies discount codes
4. Customer proceeds to checkout
5. Payment is processed
6. Order is confirmed
7. Inventory is updated
8. Notifications are sent
9. Invoice is generated

### SOLID Principles

#### 1. SRP (Single Responsibility Principle)

- `Product` class: Only handles product data
- `Cart` class: Only manages cart items
- `PaymentProcessor` class: Only processes payments
- `NotificationService` class: Only sends notifications
- `InvoiceGenerator` class: Only generates invoices

#### 2. OCP (Open/Closed Principle)

- `DiscountStrategy` interface: New discount types can be added without modifying existing code
- `PaymentMethod` interface: New payment methods can be added without changing payment processing logic
- `NotificationChannel` interface: New notification channels can be added easily

#### 3. LSP (Liskov Substitution Principle)

- All discount strategies can be used interchangeably
- All payment methods work seamlessly with the payment processor
- All notification channels can substitute each other

#### 4. ISP (Interface Segregation Principle)

- Separate interfaces for different concerns: `Discountable`, `Payable`, `Notifiable`
- Clients only depend on methods they actually use

#### 5. DIP (Dependency Inversion Principle)

- High-level modules depend on abstractions (interfaces) not concrete implementations
- Dependency injection is used throughout the system

## Project Structure

```
src/main/java/com/ecommerce/
├── interfaces/           # Abstractions and contracts
│   ├── PaymentMethod.java
│   ├── DiscountStrategy.java
│   ├── NotificationChannel.java
│   └── ISPInterfaces.java
├── models/              # Domain models
│   ├── Product.java
│   └── Cart.java
├── services/            # Business logic services
│   └── PaymentService.java
├── strategies/          # Strategy pattern implementations
│   ├── PercentageDiscount.java
│   └── FixedAmountDiscount.java
├── implementations/     # Concrete implementations
│   ├── CreditCardPayment.java
│   └── PayPalPayment.java
└── ECommerceDemo.java  # System demonstration
```

## Run the application

### Step 1: Compile

```bash
mkdir -p out

javac -d out src/main/java/com/ecommerce/*.java src/main/java/com/ecommerce/*/*.java
```

### Step 2: Run

```bash
java -cp out com.ecommerce.ECommerceDemo
```

### Alternative: One-line Rebuild and Run

```bash
rm -rf out && mkdir out && javac -d out src/main/java/com/ecommerce/*.java src/main/java/com/ecommerce/*/*.java && java -cp out com.ecommerce.ECommerceDemo
```
