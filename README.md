# Course Management System

Spring Boot MVC application για τη διαχείριση μαθημάτων Erasmus (Course Management Use Case).

## 📋 Περιγραφή

Το σύστημα επιτρέπει στον εκπρόσωπο του πανεπιστημίου υποδοχής (Host University Representative) να:
- Καταχωρίσει νέα μαθήματα
- Προβάλλει τη λίστα των διαθέσιμων μαθημάτων
- Επεξεργαστεί υπάρχοντα μαθήματα
- Διαγράψει μαθήματα με επιβεβαίωση

## 🛠️ Τεχνολογίες

- **Spring Boot 4.0.2** (Spring MVC)
- **Java 22**
- **MySQL 5.6+** (Remote Database)
- **Thymeleaf** (Template Engine)
- **Hibernate/JPA** (ORM)
- **Maven** (Build Tool)
- **Lombok** (Code Generation)

## 🚀 Εγκατάσταση & Εκτέλεση

### Προαπαιτούμενα
- Java 22 ή νεότερη
- Maven 3.6+
- Πρόσβαση στη remote MySQL database

### Βήματα Εκτέλεσης

1. **Clone το project**
   ```bash
   cd C:\Users\dimpi\ismgroup11\course-management
   ```

2. **Build το project**
   ```bash
   mvn clean install
   ```

3. **Τρέξε την εφαρμογή**
   ```bash
   mvn spring-boot:run
   ```

4. **Άνοιξε το browser**
   ```
   http://localhost:8081/course-management/
   ```

## 🔑 Login Credentials

### Test Account
- **Username:** `host`
- **Password:** `host123`

## 📍 Available Routes

### Authentication
| Route | Method | Περιγραφή |
|-------|--------|-----------|
| `/` | GET | Home/Login page |
| `/login` | GET/POST | Login form |
| `/dashboard` | GET | Dashboard μετά το login |
| `/logout` | GET | Logout |

### Course Management
| Route | Method | Περιγραφή |
|-------|--------|-----------|
| `/courses/menu` | GET | Course management menu |
| `/courses/add` | GET | Φόρμα προσθήκης μαθήματος |
| `/courses/add` | POST | Αποθήκευση νέου μαθήματος |
| `/courses/list` | GET | Λίστα όλων των μαθημάτων |
| `/courses/edit/{id}` | GET | Επεξεργασία μαθήματος |
| `/courses/update/{id}` | POST | Ενημέρωση μαθήματος |
| `/courses/delete/{id}` | POST | Διαγραφή μαθήματος |

## 🗄️ Database

### Connection Details
- **Host:** 195.251.249.131:3306
- **Database:** ismgroup11
- **User:** ismgroup11

### Tables
- `users` - Χρήστες συστήματος
- `host_universities` - Πανεπιστήμια υποδοχής
- `courses` - Μαθήματα

## 📦 Project Structure

```
course-management/
├── src/
│   ├── main/
│   │   ├── java/com/example/course_management/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # MVC Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── model/           # JPA Entities
│   │   │   ├── repository/      # Spring Data Repositories
│   │   │   └── service/         # Business Logic
│   │   └── resources/
│   │       ├── templates/       # Thymeleaf templates
│   │       ├── static/          # CSS, JS, images
│   │       └── application.yaml # Configuration
├── pom.xml
└── README.md
```

## 🎨 Features

### ✅ Implemented
- [x] User authentication (simple session-based)
- [x] Course CRUD operations
- [x] Responsive UI με CSS Grid/Flexbox
- [x] Validation (Spring Validation)
- [x] Confirmation dialogs για διαγραφή
- [x] Auto-loading test data on startup
- [x] Session management
- [x] Error handling

### 📝 Course Fields
- **Department** (Τμήμα)
- **Course Name** (Όνομα μαθήματος)
- **Course Code** (Κωδικός - optional)
- **ECTS** (1-10 credits)
- **Semester** (Fall/Spring/Summer - optional)

## 🧪 Test Data

Κατά την εκκίνηση, το σύστημα φορτώνει αυτόματα:
- 2 test users (`host`, `testhost`)
- 2 universities (AUEB, TUM)
- 5 sample courses

## ⚙️ Configuration

### Server
- **Port:** 8081
- **Context Path:** `/course-management`
- **Session Timeout:** 30 minutes

### Database
- **Auto-DDL:** `update` (δημιουργεί/ενημερώνει tables αυτόματα)
- **Show SQL:** Enabled (για debugging)

## 🐛 Troubleshooting

### Port already in use
```bash
# Βρες την process που χρησιμοποιεί το port 8081
netstat -ano | findstr :8081
# Σκότωσε το process
taskkill /PID <PID> /F
```

### Database connection issues
- Έλεγξε ότι το MySQL server είναι διαθέσιμο
- Έλεγξε τα credentials στο `application.yaml`

### Thymeleaf template errors
- Έλεγξε ότι τα templates βρίσκονται στο `src/main/resources/templates/`
- Clear το Maven cache: `mvn clean`

## 📄 License

ISM Group 11 - University Project 2026

## 👥 Authors

ISM Group 11
- Course Management Module Implementation

---

**Last Updated:** February 2026

