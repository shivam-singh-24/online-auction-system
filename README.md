# 🛒 Online Auction System

A full-stack web application for managing online auctions, built with **Spring Boot**, **MySQL**, and **HTML/CSS/JavaScript**. Includes secure login, role-based access, auction control, and bid history.

---

## 🚀 Features

- 🔐 Secure login for Admin, Seller, and Bidder
- 🧑‍💼 Admin panel to extend or close auctions
- 🛍️ Seller dashboard to create and manage listings
- 💰 Bidder interface to place bids and view history
- 🕒 Automated auction expiry and winner logic
- 📊 Role-based access control and data integrity

---

## 🛠️ Tech Stack

| Layer        | Technology            |
|--------------|------------------------|
| Backend      | Java, Spring Boot      |
| Database     | MySQL + JPA (Hibernate)|
| Frontend     | HTML, CSS, JavaScript  |
| Security     | BCrypt password encoding |
| Tools        | Git, GitHub, IntelliJ/VS Code |

---

## 📁 Folder Structure

src/ ├── main/
│ ├── java/com/auction/
│ │ ├── controller/
│ │ ├── model/ 
│ │ ├── repository/ 
│ │ ├── service/
│ ├── resources/
│ │ ├── static/html/ 
│ │ ├── static/css/ 
│ │ ├── application.properties

---

## ⚙️ Setup Instructions

1. Clone the repo  
   `git clone git@github.com:shivam-singh-24/online-auction-system.git`

2. Configure `application.properties` with your MySQL credentials

3. Run the Spring Boot app  
   `mvn spring-boot:run` or use your IDE

4. Access frontend via  
   `http://localhost:8080/html/login.html`

---

## 🧪 Sample Admin Credentials

```text
Username: admin
Password: 1234
