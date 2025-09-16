<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="th">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Stadium Booking</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/style.css">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <!-- Secondary Header -->
        <div class="secondary-header d-none d-lg-block">
            <div class="container d-flex justify-content-between align-items-center">
                
<!--                <div>
                    <a href="#"><i class="bi bi-facebook"></i></a>
                    <a href="#"><i class="bi bi-twitter-x"></i></a>
                    <a href="#"><i class="bi bi-instagram"></i></a>
                    <a href="#"><i class="bi bi-youtube"></i></a>
                </div>-->
            </div>
        </div>

        <!-- Main Navigation -->
        <nav class="navbar navbar-expand-lg navbar-dark">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">
                    <i class="bi bi-trophy-fill me-2"></i>Stadium Booking
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto align-items-center">
                        <li class="nav-item">
                            <a class="nav-link" href="index.jsp">
                                <i class="bi bi-house-door-fill me-1"></i>หน้าแรก
                            </a>
                        </li>
                        <%
                            String username = (String) session.getAttribute("user");
                            if (username != null) {
                        %>
                        <li class="nav-item">
                            <a class="nav-link" href="booking.jsp">
                                <i class="bi bi-calendar-check-fill me-1"></i>จองสนาม
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="bookingHistory.jsp">
                                <i class="bi bi-clock-history me-1"></i>ประวัติการจอง
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="payment.jsp">
                                <i class="bi bi-clock-history me-1"></i>รายการจอง
                            </a>
                        </li>
                        <li class="nav-item">
                            <span class="user-welcome">
                                <i class="bi bi-person-circle me-1"></i><%= username%>
                            </span>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link logout-btn" href="LogoutServlet">
                                <i class="bi bi-box-arrow-right me-1"></i>ออกจากระบบ
                            </a>
                        </li>
                        <%
                        } else {
                        %>
                        <li class="nav-item">
                            <a class="nav-link login-btn" href="login.jsp">
                                <i class="bi bi-box-arrow-in-right me-1"></i>เข้าสู่ระบบ
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link register-btn" href="register.jsp">
                                <i class="bi bi-person-plus-fill me-1"></i>สมัครสมาชิก
                            </a>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Bootstrap JS -->
        <script src="assets/js/jquery-3.7.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <script>
            // เพิ่ม active class ให้กับ link ที่กำลังใช้งาน
            document.addEventListener('DOMContentLoaded', function () {
                const currentLocation = location.pathname;
                const navLinks = document.querySelectorAll('.nav-link');
                navLinks.forEach(link => {
                    if (link.getAttribute('href') === currentLocation.split('/').pop()) {
                        link.classList.add('active-link');
                    }
                });
            });
        </script>