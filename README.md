# Course Management System - Erasmus+ Application

Spring Boot application για διαχείριση μαθημάτων πανεπιστημίου υποδοχής (Host University).

## 🚀 Γρήγορη Εκκίνηση

### Απαιτήσεις
- Java 17+
- Maven 3.x
- MySQL Database

### Εγκατάσταση

1. **Clone το repository**
   ```bash
   git clone <repository-url>
   cd course-management
   ```

2. **Ρύθμιση Database Credentials**
   
   Αντέγραψε το template file:
   ```bash
   cp src/main/resources/application-secrets.yaml.template src/main/resources/application-secrets.yaml
   ```
   
   Επεξεργάσου το `application-secrets.yaml` και συμπλήρωσε τα credentials της βάσης:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://YOUR_HOST:3306/YOUR_DATABASE?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
       username: YOUR_USERNAME
       password: YOUR_PASSWORD
   ```

3. **Εκτέλεση εφαρμογής**
   ```bash
   mvn spring-boot:run
   ```

4. **Πρόσβαση στην εφαρμογή**
   ```
   URL: http://localhost:8081/course-management
   ```

## 🔐 Login Credentials

Test users που δημιουργούνται αυτόματα:

| Username | Password | Τύπος |
|----------|----------|-------|
| host     | host123  | Host University Representative |
| testhost | test123  | Host University Representative |

## 📁 Δομή Project

```
src/
├── main/
│   ├── java/
│   │   └── com/example/course_management/
│   │       ├── config/          # Configuration & Data Initialization
│   │       ├── controller/      # REST & MVC Controllers
│   │       ├── model/          # JPA Entities
│   │       ├── repository/     # Data Access Layer
│   │       └── service/        # Business Logic
│   └── resources/
│       ├── templates/          # Thymeleaf Templates
│       ├── static/            # CSS, JS, Images
│       ├── application.yaml   # Main Configuration
│       └── application-secrets.yaml  # DB Credentials (gitignored)
```

## 🎯 Λειτουργίες

### Course Management
1. **Προβολή Μαθημάτων** - Λίστα με όλα τα διαθέσιμα μαθήματα
2. **Προσθήκη Μαθήματος** - Καταχώρηση νέου μαθήματος
3. **Επεξεργασία Μαθήματος** - Ενημέρωση στοιχείων μαθήματος
4. **Διαγραφή Μαθήματος** - Αφαίρεση μαθήματος με confirmation

## 🗄️ Database

Η εφαρμογή χρησιμοποιεί MySQL database. Οι πίνακες δημιουργούνται αυτόματα με το Hibernate (`ddl-auto: update`).

### Πίνακες
- `users` - Χρήστες συστήματος
- `host_universities` - Πανεπιστήμια υποδοχής
- `courses` - Μαθήματα

## ⚠️ Security Note

**ΣΗΜΑΝΤΙΚΟ**: Τα database credentials βρίσκονται στο `application-secrets.yaml` που είναι στο `.gitignore`. 

**ΜΗΝ** κάνεις commit το αρχείο `application-secrets.yaml` στο Git!

## 🛠️ Technologies

- **Spring Boot 4.0.2** - Framework
- **Spring Data JPA** - ORM
- **Hibernate** - JPA Implementation
- **MySQL** - Database
- **Thymeleaf** - Template Engine
- **Maven** - Build Tool

## 📝 License

[Προσθέστε το license σας εδώ]

