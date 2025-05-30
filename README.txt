# 📚 SiWBooks – Virtual Library System (Spring Boot + JWT + HTML/JS)

**SiWBooks** is a complete web application for managing books and user reviews, built with **Spring Boot**, **JWT authentication**, and a static frontend in HTML/CSS/JavaScript. It implements role-based access control with separate functionality for users and administrators.

---

## ✨ Main Features

### 👤 Registered Users
- Account registration with unique email and encrypted password (BCrypt)
- Login and JWT token generation
- Access to the list of books
- Submit **one review per book**, including:
  - Title
  - Rating (1–5)
  - Text content
- Logout by clearing the JWT token from localStorage

### 🛠️ Admin Panel
- Accessible via `/admin.html`
- Full **CRUD** (Create, Read, Update, Delete) operations for books
- View all user-submitted reviews
- Delete reviews individually

---

## 🔐 Security and Roles

- **JWT-based authentication** (stored in `localStorage`)
- **Role-based authorization** using `SecurityConfig.java`
- Roles:
  - `USER` → can read books and submit reviews
  - `ADMIN` → can manage books and reviews

---

## 🗂️ Project Structure

src/
├── main/
│ ├── java/com/example/siwbooks/
│ │ ├── controller/ # REST Controllers: Auth, Book, Review
│ │ ├── model/ # Entities: Book, User, Review
│ │ ├── repository/ # JPA Repositories
│ │ ├── security/ # JwtUtil, JwtFilter
│ │ ├── service/ # Business Logic & UserDetailsService
│ │ ├── config/ # SecurityConfig + DataInitializer
│ └── resources/
│ ├── static/ # HTML, JS, CSS frontend
│ ├── application.properties

yaml
Copiar
Editar

---

## 📦 Initial Test Data (via `DataInitializer.java`)

The application initializes with:

- **6 users**:
  - 1 admin: `admin@example.com` / `admin`
  - 5 users: `usuario1@example.com` → `usuario5@example.com` (all use `password`)
- **25 sample books**
- **5 reviews** (submitted by `usuario1` on the first 5 books)

---

## 🧪 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Hibernate (JPA)
- PostgreSQL
- Static HTML/CSS + Bootstrap
- Vanilla JavaScript

---

## ▶️ How to Run

```bash
# Clone this repo
git clone https://github.com/yourusername/siwbooks.git
cd siwbooks

# Start PostgreSQL and configure it in application.properties

# Run the application
./mvnw spring-boot:run
👥 Test Credentials
Email	Role	Password
admin@example.com	ADMIN	admin
usuario1@example.com	USER	password
usuario2@example.com	USER	password
usuario3@example.com	USER	password
usuario4@example.com	USER	password
usuario5@example.com	USER	password

📖 Use Cases Covered
Use Case	Implemented
User registration	✅ Yes
Login and JWT token	✅ Yes
Book listing (paginated)	✅ Yes
Review creation (1/user/book)	✅ Yes
Book CRUD (Admin only)	✅ Yes
Admin review moderation	✅ Yes
Static frontend (no templates)	✅ Yes

🎓 Academic Context
This project was developed for the Information Systems on the Web exam at Università degli Studi Roma Tre, Master in Computer Engineering, Academic Year 2024/2025.

yaml
Copiar
Editar

---

✅ You can now copy and paste this directly into your `README.md` on GitHub.

If you'd like a version in **Italian** or to generate a `data.sql` alternative to `DataInitializer`, just let me know!





