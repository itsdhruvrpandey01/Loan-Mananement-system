# Loan Management System 💳🏦

A full-stack Loan Management System built with **Angular** and **Spring Boot** that enables customers to apply for loans, managers to approve or reject based on cities, and admins to manage schemes and track transactions. It includes Stripe integration for dummy payments, Cloudinary for document storage, and automated schedulers for installment tracking.

---

## 🧾 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [User Roles](#user-roles)
- [Tech Stack](#tech-stack)
- [Folder Structure](#folder-structure)
- [Setup Instructions](#setup-instructions)
- [Schedulers](#schedulers)
- [Payment & File Upload](#payment--file-upload)
- [License](#license)
- [Contact](#contact)

---

## 📌 Overview

The system provides a digital solution to manage loan workflows with real-time tracking, automated reminders, and secure document handling.

---

## ⭐ Features

### 👤 **Customer**
- Register & Login
- Apply for loans with document upload
- View loan status & installment schedule
- Pay EMIs via Stripe (test mode)
- Receive email reminders for upcoming and overdue payments

### 🧑‍💼 **Loan Officer / Manager**
- View & update profile
- See loans applied by customers from assigned city
- Review documents (stored on Cloudinary)
- Approve or reject applications

### 🛠️ **Admin**
- Add/update/delete loan schemes
- Assign managers to cities
- Add new cities
- Monitor all transactions, repayments, and overdue statuses

---

## 🧑‍💻 Tech Stack

| Layer       | Technology                     |
|-------------|--------------------------------|
| **Frontend** | Angular (TypeScript, HTML, SCSS) |
| **Backend**  | Java (Spring Boot)             |
| **Database** | MySQL / PostgreSQL             |
| **File Storage** | Cloudinary (for documents)    |
| **Email**    | JavaMail / SMTP                |
| **Payments** | Stripe (test environment)      |

---

## 📁 Folder Structure

```

Loan-Mananement-system/
├── Backend/                      # Spring Boot backend
└── FrontEnd/
└── aurofin-frontend/         # Angular frontend

````

---

## ⚙️ Setup Instructions

### 🔧 Backend Setup

1. Navigate to backend folder:
```bash
cd Backend
````

2. Configure `application.properties`:

```properties
# DB
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_user
spring.datasource.password=your_password

# Cloudinary
cloudinary.cloud_name=your_cloud_name
cloudinary.api_key=your_api_key
cloudinary.api_secret=your_api_secret

# Email config
spring.mail.username=your_email@example.com
spring.mail.password=your_email_password
```

3. Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

---

### 🖥️ Frontend Setup (Angular)

1. Navigate to the frontend folder:

```bash
cd FrontEnd/aurofin-frontend
```

2. Install dependencies:

```bash
npm install
```

3. Serve the application:

```bash
ng serve
```

> App will run on `http://localhost:4200/` by default

---

## ⏱️ Schedulers

Automated schedulers run in the backend:

* **Upcoming Installment Reminder**: sends email 3 days before due date
* **Overdue Reminder**: daily reminder after due date
* **Late Payment Fine Scheduler**: applies fine on overdue EMIs

---

## 💳 Payment & 📂 File Upload

### Stripe Integration

* Dummy Stripe gateway for test payments (installments)
* Uses Stripe test keys (replace in environment or service config)

### Cloudinary Integration

* Customer documents uploaded during loan application are stored in Cloudinary
* Loan Officers can access these documents for review

---
