🛒 Online Shop – Spring Boot Project

Java 25 + Spring Boot (Maven) prototype for an online shop with UI pages and REST APIs.

✅ Features (UI + API)
- Register / login
- Product listing + search
- Cart add/remove
- Checkout with payment selection
- Admin product creation + order view

🚀 Run
```powershell
cd "C:\Users\vac\OnlineShop"
.\mvnw.cmd spring-boot:run
```

Open:
- `http://localhost:8080/`
- `http://localhost:8080/products`
- `http://localhost:8080/cart`
- `http://localhost:8080/checkout`

🧪 Tests
```powershell
.\mvnw.cmd test
```

💡 Notes
- Data is stored in-memory for a simple prototype.
- Default demo users: `admin/admin123` and `customer/password123`.
