# Student Management System (SMS)

A full-stack, enterprise-grade academic management web application engineered using **Spring Boot**, **Spring Security**, and **Thymeleaf**. The platform delivers a dual-portal architecture featuring isolated authentication workflows for Administrators and Students, dynamic fee aggregation, administrative analytics, and client-side PDF receipt generation.

---

## Key Features

### Authentication & Authorization
* **Dual-Portal Login**: Dedicated authentication interfaces (`/admin/login` and `/student/login`) with role-based redirection via custom `AuthenticationSuccessHandler`.
* **Credential Protection**: Secure password encryption utilizing `BCryptPasswordEncoder`.
* **Session Lifecycle Management**: Session invalidation and cookie clearing upon logout.

### Admin Portal (`ROLE_ADMIN`)
* **Live Operational Metrics**: Real-time summary cards tracking Total Students, Total Courses, Active Enrollments, and Top Courses.
* **Student Record Management**: Full CRUD capabilities (Register, Inspect, Edit credentials, Delete).
* **Instant Record Filtering**: Responsive client-side search across student names and emails without full-page reloads.
* **Course & Enrollment Workflows**: Course provisioning and automated student-course mappings with real-time fee calculation.

### Student Portal (`ROLE_STUDENT`)
* **Personalized Dashboard**: View registered courses, enrollment dates, and aggregate tuition fees.
* **Instant Receipt Generation**: Client-side, print-optimized PDF invoice generation and downloading via `html2pdf.js`.

---

## Tech Stack

* **Backend**: Java 17+, Spring Boot, Spring Data JPA, Spring Security
* **Persistence**: MySQL / H2, Hibernate ORM
* **Frontend / Templating**: Thymeleaf, HTML5, Modern CSS3, JavaScript (ES6+)
* **Libraries**: FontAwesome 6, `html2pdf.js`
* **Build Tool**: Maven

---

## Architecture & Project Structure

```text
src/main/java/com/sms/student_management_system/
├── config/
│   ├── CustomLoginSuccessHandler.java
│   └── SecurityConfig.java
├── controller/
│   ├── AdminController.java
│   ├── AuthController.java
│   ├── CourseController.java
│   ├── EnrollmentController.java
│   └── StudentController.java
├── dto/
│   ├── RegisterDTO.java
│   └── StudentResponseDTO.java
├── entity/
│   ├── Course.java
│   ├── Enrollment.java
│   └── User.java
├── repository/
│   ├── CourseRepository.java
│   ├── EnrollmentRepository.java
│   └── UserRepository.java
└── service/
    └── StudentService.java
