
# 🎓 Course Management System (Java Swing-Based)

A role-based Java application built with **Java Swing** that allows Admins, Professors, and Students to manage academic operations such as course registration, enrollment, and assignments through a clean graphical user interface (GUI).

---

## 📌 Key Features

### 👤 Admin
- Register new **Students** and **Professors**
- Add new **Courses**
- Central authority to populate the system

### 👨‍🏫 Professor
- Self-register into the system
- View all available courses
- Assign themselves to a course
- View students enrolled in their assigned course

### 🎓 Student
- Self-register with personal details
- View available courses
- Enroll in a selected course

---

## 🧠 System Architecture

This project is **modularized** into different packages following the MVC-style pattern, ensuring separation of concerns.

```
src/
└── com.gqt.cms
    ├── Launcher.java
    └── CourseDatabase.java

├── com.gqt.cms.datamanagement
│   ├── AdminData.java
│   ├── ProfessorData.java
│   └── StudentData.java

├── com.gqt.cms.gui
│   ├── MainWindow.java
│   ├── AdminWindow.java
│   ├── ProfessorWindow.java
│   └── StudentWindow.java

├── com.gqt.cms.users
│   ├── Professor.java
│   └── Student.java

└── com.gqt.cms.utils
    ├── DialogUtil.java
    └── MultiInputDialogUtil.java
```

---

## 🧩 Module-wise Explanation

### 📁 `com.gqt.cms`
#### `Launcher.java`
- The entry point of the application.
- Launches the `MainWindow` which lets users choose between Admin, Professor, or Student roles.

#### `CourseDatabase.java`
- Centralized array-based storage for all courses.
- Provides utility methods to add and fetch courses across the system.

---

### 📁 `com.gqt.cms.datamanagement`
#### `AdminData.java`
- Handles logic related to Admin actions such as adding students, professors, and courses.
- Interacts with other modules (ProfessorData, StudentData).

#### `ProfessorData.java`
- Maintains a static array of professors.
- Allows professors to:
  - Self-register
  - Assign themselves to courses
  - View students enrolled in their assigned course

#### `StudentData.java`
- Stores student records in an array.
- Allows students to:
  - Register with details
  - Enroll in a course

---

### 📁 `com.gqt.cms.gui`
#### `MainWindow.java`
- Displays the main menu.
- Buttons for Admin, Professor, and Student roles.

#### `AdminWindow.java`
- GUI window for admin operations:
  - Register students and professors
  - Add courses using `MultiInputDialogUtil`

#### `ProfessorWindow.java`
- GUI for professor-specific operations:
  - Register
  - View courses
  - Assign themselves to a course
  - View enrolled students in their course

#### `StudentWindow.java`
- GUI for student-specific operations:
  - Register
  - View available courses
  - Enroll in a selected course

---

### 📁 `com.gqt.cms.users`
#### `Professor.java`
- A simple model class to store:
  - Professor name
  - ID
  - Assigned course ID

#### `Student.java`
- A model class to store:
  - Student name
  - ID
  - Enrolled course ID

---

### 📁 `com.gqt.cms.utils`
#### `DialogUtil.java`
- Helper class for displaying basic `JOptionPane` input dialogs.

#### `MultiInputDialogUtil.java`
- Builds multi-field input dialogs using:
  - `JPanel`
  - `JLabel`
  - `JTextField`
- Used for clean registration forms (e.g., name + ID together)

---

## 🔄 System Flow

1. **Launch `Launcher.java`**
2. `MainWindow` opens with role selection (Admin / Professor / Student)
3. Each role opens their respective GUI window (`AdminWindow`, `ProfessorWindow`, `StudentWindow`)
4. All data is stored using static arrays defined in the `datamanagement` module

---

## ✅ Highlights

- 💻 Fully GUI-based using Java Swing
- 🧼 Clean and modular code structure
- 📦 Array-based in-memory data management
- 🧩 Reusable dialog utilities
- 🚫 No database required (great for beginners)

---

## 🚀 How to Run

1. Clone or download this repository.
2. Open the project in your favorite Java IDE (e.g., Eclipse or IntelliJ).
3. Run the `Launcher.java` file.
4. Interact with the GUI to perform admin, professor, or student operations.

---

## 📈 Future Enhancements

- Add file-based storage or database for data persistence
- Implement user login/authentication for each role
- Add input validation and duplicate checks
- Improve GUI design using advanced Swing components/layouts

---

## 👨‍💻 Author

**Harsha Naidu**  
📧 Email: [HARSHA-3623@github.com](https://github.com/HARSHA-3623)  
🎓 Java Developer | Swing UI Designer | Backend Enthusiast

---
