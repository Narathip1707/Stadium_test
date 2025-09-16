<%-- 
    Document   : index
    Created on : 27 ก.พ. 2568, 18:02:40
    Author     : Narathip
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="db.DBConnection" %>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="css/homepage.css"/>
<!-- Hero Section -->
<div class="hero-section">
    <div class="hero-content">
        <h1>ยินดีต้อนรับสู่ Jeng Stadium</h1>
        <p>บริการจองสนาม</p>
        <a href="#fields" class="btn btn-hero btn-lg">สำรวจสนาม</a>
    </div>
</div>

<!-- Fields Section -->
<div class="container py-5" id="fields">
    <%        String user = (String) session.getAttribute("user");
        boolean isAdmin = false;
        if (user != null) {
            Object adminAttr = session.getAttribute("isAdmin");
            if (adminAttr != null) {
                isAdmin = (Boolean) adminAttr;
            }
    %>

    <!-- Welcome Section for Logged In Users -->
    <div class="welcome-section">
        <div class="container">
            <div class="text-center">
                <h2 class="welcome-title">สวัสดี, <span class="username"><%= user%></span>!</h2>
                <div class="action-buttons">
                    <a href="booking.jsp" class="btn btn-danger">
                        <i class="bi bi-calendar-plus me-2"></i>จองสนาม
                    </a>
                    <a href="bookingHistory.jsp" class="btn btn-primary">
                        <i class="bi bi-clock-history me-2"></i>ประวัติการจอง
                    </a>
                    <% if (isAdmin) { %>
                    <a href="admin.jsp" class="btn btn-warning">
                        <i class="bi bi-gear me-2"></i>หน้าผู้ดูแล
                    </a>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <!-- Fields Listing Section -->
    <div class="section-header">
        <h2>สนามของเรา</h2>
    </div>

    <div class="row g-4">
        <%
            // เชื่อมต่อกับฐานข้อมูล
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                // ใช้ DBConnection
                conn = DBConnection.getConnection();

                // ดึงข้อมูลจากตาราง fields ที่ต้องการแสดง
                String sql = "SELECT id, name, location, price, capacity, operating_hours, image_url FROM fields WHERE status = 'active'";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    double price = rs.getDouble("price");
                    int capacity = rs.getInt("capacity");
                    String operating_hours = rs.getString("operating_hours");
                    String image_url = rs.getString("image_url");

                    // ถ้าไม่มี URL รูปภาพ ให้ใช้รูปภาพเริ่มต้น
                    if (image_url == null || image_url.trim().isEmpty()) {
                        image_url = "assets/images/default-field.jpg";
                    }
        %>

        <!-- สนาม <%= id%> -->
        <div class="col-md-4">
            <div class="card field-card h-100">
                <img src="<%= image_url%>" class="card-img-top" alt="<%= name%>">
                <div class="card-body">
                    <h5 class="card-title"><%= name%></h5>
                    <p class="card-text text-muted mb-3"><%= location%></p>
                    <div class="field-feature">
                        <i class="bi bi-people-fill"></i>
                        <span><%= capacity%> คน/ทีม</span>
                    </div>
                    <div class="field-feature">
                        <i class="bi bi-clock-fill"></i>
                        <span>เปิด <%= operating_hours%></span>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="price-tag">฿<%= String.format("%,.0f", price)%>/ชม.</span>
                        <a href="FieldDetailServlet?field=<%= id%>" class="btn btn-details btn-danger">รายละเอียด</a>
                    </div>
                </div>
            </div>
        </div>

        <%
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // ปิดการเชื่อมต่อ
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        %>
    </div>

    <% } else { %>
    <!-- Auth Section for Non-Logged In Users -->
    <div class="auth-section text-center">
        <div class="container">
            <h2 class="section-header mb-4">เริ่มต้นการจองสนาม</h2>
            <p>กรุณาเข้าสู่ระบบหรือสมัครสมาชิกเพื่อจองสนามฟุตบอล</p>
            <div class="mt-4">
                <a href="login.jsp" class="btn btn-danger">
                    <i class="bi bi-box-arrow-in-right me-2"></i>เข้าสู่ระบบ
                </a>
                <a href="register.jsp" class="btn btn-primary">
                    <i class="bi bi-person-plus-fill me-2"></i>สมัครสมาชิก
                </a>
            </div>
        </div>
    </div>

    <!-- Fields Preview Section -->
    <div class="section-header">
        <h2>สนามของเรา</h2>
    </div>

    <div class="row g-4">
        <%
            // เชื่อมต่อกับฐานข้อมูล
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                // ใช้ DBConnection
                conn = DBConnection.getConnection();

                // ดึงข้อมูลจากตาราง fields ที่ต้องการแสดง
                String sql = "SELECT id, name, location, price, capacity, operating_hours, image_url FROM fields WHERE status = 'active'";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    double price = rs.getDouble("price");
                    int capacity = rs.getInt("capacity");
                    String operating_hours = rs.getString("operating_hours");
                    String image_url = rs.getString("image_url");

                    // ถ้าไม่มี URL รูปภาพ ให้ใช้รูปภาพเริ่มต้น
                    if (image_url == null || image_url.trim().isEmpty()) {
                        image_url = "assets/images/default-field.jpg";
                    }
        %>

        <!-- สนาม <%= id%> -->
        <div class="col-md-4">
            <div class="card field-card h-100">
                <img src="<%= image_url%>" class="card-img-top" alt="<%= name%>">
                <div class="card-body">
                    <h5 class="card-title"><%= name%></h5>
                    <p class="card-text text-muted mb-3"><%= location%></p>
                    <div class="field-feature">
                        <i class="bi bi-people-fill"></i>
                        <span><%= capacity%> คน/ทีม</span>
                    </div>
                    <div class="field-feature">
                        <i class="bi bi-clock-fill"></i>
                        <span>เปิด <%= operating_hours%></span>
                    </div>
                    <hr>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="price-tag">฿<%= String.format("%,.0f", price)%>/ชม.</span>
                        <a href="login.jsp" class="btn btn-details btn-danger">เข้าสู่ระบบเพื่อจอง</a>
                    </div>
                </div>
            </div>
        </div>

        <%
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // ปิดการเชื่อมต่อ
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                }
            }
        %>
    </div>
    <% }%>
</div>

<%@ include file="footer.jsp" %>