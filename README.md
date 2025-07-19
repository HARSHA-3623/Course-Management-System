
# 🎓 Course Management System (Java Swing + JDBC)

A modular, role-based academic application built with **Java Swing GUI** and **MySQL JDBC** integration. It allows **Admins**, **Professors**, and **Students** to perform course-related operations such as registration, enrollment, and assignment via a visually interactive system.

---

## ✅ Major Enhancements

- 🚀 **Shifted from in-memory arrays to MySQL database** for storing students, professors, courses, and enrollments.
- 🖼️ **Redesigned GUI** using `GridBagLayout`, `JComboBox`, `JOptionPane`, and `JTable` for modern, consistent dialogs.
- 🔄 Separated responsibilities into DAO classes: `AdminDAO`, `StudentDAO`, `ProfessorDAO`, `CourseDAO`, and `EnrollmentDAO`.
- 📚 Added new enrollment table logic and fixed student course linking.
- 🧮 Fully mapped Course ID → Course Name relationships in UI.
- 🧑‍🏫 Professors can now register and assign themselves to courses (under construction).

---

## 📁 Project Structure

```
src/
└── com.gqt.cms
    ├── CourseDatabase.java         # DB connection logic
    └── Launcher.java               # Entry point

├── com.gqt.cms.datamanagement
│   ├── AdminDAO.java              # Admin DB operations
│   ├── CourseDAO.java             # Course DB logic
│   ├── EnrollmentDAO.java         # Manages enrollments
│   ├── ProfessorDAO.java          # Professor-related DB actions
│   └── StudentDAO.java            # Student-related DB actions

├── com.gqt.cms.gui
│   ├── MainWindow.java            # Role selection screen
│   ├── AdminWindow.java           # Admin features
│   ├── ProfessorWindow.java       # Professor panel (under development)
│   └── StudentWindow.java         # Student dashboard

├── com.gqt.cms.users
│   ├── Professor.java             # Model class for Professor
│   └── Student.java               # Model class for Student

└── com.gqt.cms.utils
    ├── DialogUtil.java            # Reusable message/input dialogs
    └── MultiInputDialogUtil.java  # Advanced input panels
```

---

## 🔑 Core Features by Role

### 👤 Admin
- ➕ Add new courses
- 🔍 View all available courses (with IDs)
- ❌ Delete courses from DB
- 📜 View all student-course enrollments

### 👨‍🏫 Professor
- ✅ Register with name and email
- 📘 View available courses
- 🎯 Assign themselves to teach a course
- 🔍 (Upcoming) View students in their assigned course

### 🎓 Student
- ✅ Register using name + email
- 📘 View course list (with `cid`)
- ✍️ Enroll into course using Student ID + selected course
- ✅ Enrollments are stored in a junction table `enrollments`

---

## 💽 MySQL Tables Used

```sql
CREATE TABLE students (
    sid INT AUTO_INCREMENT PRIMARY KEY,
    sname VARCHAR(100),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE professors (
    pid INT AUTO_INCREMENT PRIMARY KEY,
    pname VARCHAR(100),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE courses (
    cid INT AUTO_INCREMENT PRIMARY KEY,
    cname VARCHAR(100),
    duration VARCHAR(50),
    fees FLOAT
);

CREATE TABLE enrollments (
    sid INT,
    cid INT,
    PRIMARY KEY (sid, cid),
    FOREIGN KEY (sid) REFERENCES students(sid),
    FOREIGN KEY (cid) REFERENCES courses(cid)
);
```

---

## 🖼 GUI Preview Features

- ✅ Consistent color palette across windows (`AliceBlue`, `SteelBlue`)
- 🧩 `JComboBox` used for selecting courses
- 📊 `JTable` for displaying course lists in a readable format
- 🎯 `GridBagLayout` for flexible, organized form design

---

## 🚀 How to Run

1. Clone or download the repo.
2. Set up MySQL with the given table structure.
3. Configure DB credentials in `CourseDatabase.java`.
4. Run `Launcher.java` to start the application.
5. Navigate using the role-based dashboard.

---

## 📌 Future Plans

- 🛡️ Login system for each role
- 🧾 Validation: duplicate emails, course constraints
- 📈 Professor reports
- 📤 Export data to CSV

---

## 👨‍💻 Author

**Harsha Naidu**  
📧 [HARSHA-3623@github.com](https://github.com/HARSHA-3623)  
☕ Java Swing Developer | Backend & JDBC Enthusiast
