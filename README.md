🛒 Online Shop – Spring Boot Project

Java 25 + Spring Boot (Maven) prototype for an online shop with UI pages and REST APIs.

✅ Features (UI + API)
- Register / login
- Product listing + search
- Cart add/remove
- Checkout with payment selection
- Admin product creation + order view

🚀 Run
```bash
cd OnlineShop
./mvnw spring-boot:run
```

Windows PowerShell:
```powershell
cd OnlineShop
.\mvnw.cmd spring-boot:run
```

Open:
- `http://localhost:8080/` (login)
- `http://localhost:8080/home`
- `http://localhost:8080/products`
- `http://localhost:8080/cart`
- `http://localhost:8080/checkout`
- `http://localhost:8080/orders`
- `http://localhost:8080/register`
- `http://localhost:8080/about`

🧪 Tests
```bash
./mvnw test
```

Windows PowerShell:
```powershell
.\mvnw.cmd test
```

💡 Notes
- Data is stored in-memory for a simple prototype.
- Default demo users: `admin/admin123` and `customer/password123`.
