# Online Shop

Spring Boot web application for an online shop. Uses dependency injection and a payment service interface with PayPal and Stripe implementations.

## Tech stack

- Java 25
- Spring Boot 3.5.7
- Maven

## How to run

**Prerequisites:** Java 25 or higher, Maven (or use the included wrapper).

1. Open a terminal in the project root.
2. Install dependencies and run tests (optional):
   ```
   ./mvnw clean install
   ```
3. Start the application:
   ```
   ./mvnw spring-boot:run
   ```
   On Windows:
   ```
   mvnw.cmd spring-boot:run
   ```
4. Open a browser and go to: `http://localhost:8080`

The home page is served at `/`. The app uses the configured payment service (e.g. PayPal) and runs a sample order on startup.

## Project structure

```
OnlineShop/
├── pom.xml                          # Maven build and dependencies
├── mvnw, mvnw.cmd                   # Maven wrapper scripts
├── README.md
└── src/
    ├── main/
    │   ├── java/com/codeonlineshop/onlineshop/
    │   │   ├── OnlineShopApplication.java   # Entry point; starts Spring and runs sample order
    │   │   ├── HomeController.java          # Maps GET / to the home page (index.html)
    │   │   ├── OrderService.java            # Places order via injected PaymentService
    │   │   ├── PaymentService.java         # Interface for payment processing
    │   │   ├── PaypalPaymentService.java   # PayPal implementation of PaymentService
    │   │   └── StripePaymentService.java   # Stripe implementation of PaymentService
    │   └── resources/
    │       ├── application.properties      # App name, server/config settings
    │       └── static/
    │           └── index.html              # Home page served at /
    └── test/
        └── java/com/codeonlineshop/onlineshop/
            ├── OnlineShopApplicationTests.java  # Context load and OrderService wiring
            ├── HomeControllerTest.java           # GET / returns 200 and index view
            ├── OrderServiceTest.java             # placeOrder and setPaymentService behaviour
            ├── PaypalPaymentServiceTest.java    # processPayment for PayPal
            └── StripePaymentServiceTest.java    # processPayment for Stripe
```

**Main source (`src/main/`)**

- **OnlineShopApplication** — Boots Spring, gets `OrderService` from the context, runs `placeOrder()` once on startup.
- **HomeController** — Handles `/` and returns the view name for the static home page; uses `@Value` for the application name.
- **OrderService** — Depends on `PaymentService` (constructor injection). `placeOrder()` calls `processPayment(10)`; `setPaymentService()` allows swapping the implementation.
- **PaymentService** — Interface with `processPayment(double amount)`. Used for dependency injection so the order logic does not depend on a concrete provider.
- **PaypalPaymentService** / **StripePaymentService** — Implement `PaymentService`; currently log the amount to the console. Spring picks one as the bean (e.g. PayPal when both are present, depending on configuration).

**Resources**

- **application.properties** — Sets `spring.application.name` and other options. Static assets go in `static/` and are served at the root (e.g. `index.html` at `/`).

**Tests (`src/test/`)**

- Tests mirror the main package. They use JUnit 5 and Mockito; controller test uses `@WebMvcTest`, application test uses `@SpringBootTest`. Run with `./mvnw test`.

## Running tests

```
./mvnw test
```

Tests cover the controller, order service, payment services, and application context wiring.
