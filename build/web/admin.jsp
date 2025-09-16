<%-- 
    Document   : admin
    Created on : 14 ก.พ. 2568, 14:59:49
    Author     : Narathip
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("index.jsp"); // ❌ ไม่ใช่ Admin ให้กลับหน้าแรก
        return;
    }
%>
<%@ include file="header.jsp" %>

<div class="container py-5">
    <div class="row mb-4">
        <div class="col-12">
            <div class="d-flex justify-content-between align-items-center">
                <h2 class="mb-0">🛠️ แผงควบคุมผู้ดูแลระบบ</h2>
                <div class="alert alert-success py-2 px-3 mb-0">
                    ยินดีต้อนรับ, <strong><%= session.getAttribute("user")%></strong>
                </div>
            </div>
            <hr class="my-3">
        </div>
    </div>

    <!-- 📊 Dashboard -->
    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <%@ include file="dashboard.jsp" %> 
        </div>
    </div>

    <div class="row g-4">
        <div class="col-md-4">
            <div class="card shadow-sm h-100">
                <div class="card-body text-center d-flex flex-column">
                    <h5 class="card-title mb-3">📅 รายชื่อสมาชิก&การจองสนาม</h5>
                    <p class="card-text flex-grow-1">ตรวจสอบสถานะการจอง</p>
                    <a href="manage_bookings.jsp" class="btn btn-info w-100">ตรวจสอบ</a>
                </div>
            </div>
        </div>
        
        <!-- พื้นที่สำหรับเพิ่มการ์ดอื่นๆ ในอนาคต -->
        <div class="col-md-4">
            <div class="card shadow-sm h-100">
                <div class="card-body text-center d-flex flex-column">
                    <h5 class="card-title mb-3">👥 จัดการผู้ใช้</h5>
                    <p class="card-text flex-grow-1">เพิ่ม ลบ แก้ไขข้อมูลผู้ใช้งานระบบ</p>
                    <a href="manage_users.jsp" class="btn btn-primary w-100">จัดการ</a>
                </div>
            </div>
        </div>
        
        <div class="col-md-4">
            <div class="card shadow-sm h-100">
                <div class="card-body text-center d-flex flex-column">
                    <h5 class="card-title mb-3">🏟️ จัดการสนาม</h5>
                    <p class="card-text flex-grow-1">เพิ่ม ลบ แก้ไขข้อมูลสนาม</p>
                    <a href="manage_fields.jsp" class="btn btn-success w-100">จัดการ</a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>