<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.FieldDAO, java.sql.*" %>

<head>
    <link rel="stylesheet" href="css/booking-styles.css">
</head>

<%    // ดึงข้อมูลสนามที่ active จากตาราง fields
    FieldDAO fieldDAO = new FieldDAO();
    ResultSet rs = fieldDAO.getActiveFields(); // เปลี่ยนจาก getAllFields() เป็น getActiveFields()
    java.util.List<java.util.Map<String, Object>> fields = new java.util.ArrayList<>();
    while (rs.next()) {
        java.util.Map<String, Object> field = new java.util.HashMap<>();
        field.put("field_type", rs.getString("field_type"));
        field.put("name", rs.getString("name"));
        field.put("price", rs.getDouble("price"));
        field.put("image_url", rs.getString("image_url"));
        field.put("capacity", rs.getInt("capacity"));
        fields.add(field);
    }
    rs.close();
    request.setAttribute("fields", fields);
%>

<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <!-- Hero section คงเดิม -->
            <div class="card text-center bg-primary text-white mb-4 rounded-3 shadow">
                <div class="card-body py-4">
                    <h2 class="display-6 fw-bold mb-2"><i class="bi bi-calendar-check me-2"></i>จองสนามฟุตบอล</h2>
                    <p class="lead mb-0">เลือกสนามและเวลาที่คุณต้องการ</p>
                </div>
            </div>

            <div class="card shadow-lg rounded-3">
                <div class="card-body p-4">
                    <%
                        String errorMessage = (String) request.getAttribute("errorMessage");
                        String successMessage = (String) request.getAttribute("successMessage");
                        String bookingId = (String) request.getAttribute("bookingId");

                        if (errorMessage != null) {
                    %>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle me-2"></i><%= errorMessage%>
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <% }
                        if (successMessage != null) {%>
                    <div class="card mb-4 booking-success-card border-0 bg-success bg-opacity-10 rounded-3">
                        <div class="card-body text-center py-5">
                            <div class="success-icon mb-4">
                                <i class="bi bi-check-circle-fill text-success" style="font-size: 4rem;"></i>
                            </div>
                            <h3 class="card-title text-uppercase mb-3 text-success fw-bold">จองสำเร็จ!</h3>
                            <p class="card-text mb-4 fs-5"><%= successMessage%></p>
                            <a href="PaymentServlet?bookingId=<%= bookingId%>" class="btn btn-success btn-lg px-5 py-3 rounded-pill shadow-sm">
                                <i class="bi bi-credit-card me-2"></i>ดำเนินการชำระเงิน
                            </a>
                        </div>
                    </div>
                    <% } else {%>
                    <form action="BookingServlet" method="post" id="bookingForm" class="needs-validation" novalidate>
                        <!-- สนามฟุตบอล -->
                        <div class="mb-5">
                            <h4 class="mb-3 pb-2 border-bottom"><i class="bi bi-diagram-2 me-2 text-primary"></i>เลือกสนาม</h4>
                            <div class="field-container">
                                <div class="row g-3">
                                    <c:forEach var="field" items="${fields}">
                                        <div class="col-md-4">
                                            <div class="card field-card h-100">
                                                <div class="card-body p-3 text-center position-relative">
                                                    <input type="radio" class="btn-check" name="field" id="field_${field.field_type}" value="${field.field_type}" required>
                                                    <label class="btn field-select-btn h-100 w-100 d-flex flex-column" for="field_${field.field_type}">
                                                        <div class="field-image mb-3 mt-2">
                                                            <img src="${field.image_url}" alt="${field.name}" class="img-fluid rounded">
                                                        </div>
                                                        <h5 class="mb-2">${field.name}</h5>
                                                        <p class="mb-1 text-muted">${field.capacity} คน/ทีม</p>
                                                        <p class="text-primary fw-bold">฿${field.price}/ชั่วโมง</p>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="invalid-feedback field-feedback text-center mt-2">กรุณาเลือกสนาม</div>
                            </div>
                        </div>
                        <!-- วันที่จอง และส่วนอื่น ๆ คงเดิม -->
                        <div class="mb-5">
                            <h4 class="mb-3 pb-2 border-bottom"><i class="bi bi-calendar3 me-2 text-primary"></i>วันที่จอง</h4>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="date-picker-container position-relative">
                                        <input type="date" class="form-control form-control-lg" id="bookingDate" name="bookingDate" required 
                                               min="<%= java.time.LocalDate.now()%>"
                                               max="<%= java.time.LocalDate.now().plusMonths(3)%>">
                                        <div class="invalid-feedback">กรุณาเลือกวันที่จอง</div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="date-info bg-light p-3 rounded">
                                        <small class="d-block text-muted"><i class="bi bi-exclamation-circle me-1"></i>เช็คตารางสนามก่อนการจอง</small>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="mb-5">
                            <h4 class="mb-3 pb-2 border-bottom"><i class="bi bi-clock me-2 text-primary"></i>เลือกช่วงเวลา</h4>
                            <p class="text-muted mb-3">เลือกได้หลายช่วง ช่วงละ 1 ชั่วโมง</p>
                            <div class="time-slots-container p-3 bg-light rounded-3">
                                <div class="time-slots-grid"></div>
                                <input type="hidden" id="timeRanges" name="timeRanges" required>
                                <div class="selected-time-summary mt-4 p-3 bg-white rounded shadow-sm">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0"><i class="bi bi-check2-square me-2 text-success"></i>ช่วงเวลาที่เลือก</h5>
                                        <span class="badge bg-success rounded-pill" id="selectedTimeCount">0 ช่วง</span>
                                    </div>
                                    <hr>
                                    <p id="selectedTimeRanges" class="mb-0 fw-bold">-</p>
                                </div>
                            </div>
                        </div>

                        <div class="price-summary mb-4">
                            <div class="card bg-light border-0 rounded-3">
                                <div class="card-body p-4">
                                    <div class="row align-items-center">
                                        <div class="col-md-6">
                                            <h4 class="mb-1"><i class="bi bi-cash me-2 text-success"></i>ราคารวม</h4>
                                            <p class="text-muted mb-0">รวมภาษีมูลค่าเพิ่มแล้ว</p>
                                        </div>
                                        <div class="col-md-6 text-md-end">
                                            <p class="display-5 fw-bold text-success mb-0" id="totalPrice">฿0</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-lg py-3 rounded-pill">
                                <i class="bi bi-check-circle me-2"></i>ยืนยันการจอง
                            </button>
                        </div>
                    </form>
                    <% }%>
                </div>
            </div>
            <!-- ส่วนคำแนะนำด้านล่าง -->
            <div class="card mt-4 border-0 bg-light">
                <div class="card-body p-3">
                    <h5><i class="bi bi-lightbulb me-2 text-warning"></i>คำแนะนำในการจอง</h5>
                    <ul class="mb-0">
                        <li>หากยังไม่ชำระเงิน สามารถยกเลิก/ชำระเงินได้ที่หน้าประวัติการจอง</li>
                        <li>มาถึงสนามก่อนเวลาเริ่มอย่างน้อย 15 นาที</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="assets/js/jquery-3.7.1.min.js"></script>
<!-- ฝังข้อมูลราคาใน JavaScript -->
<script>
    const fieldPrices = {
    <c:forEach var="field" items="${fields}" varStatus="loop">
        '${field.field_type}': ${field.price}${loop.last ? '' : ','}
    </c:forEach>
    };
</script>

<script src="assets/js/booking.js"></script>

<%@ include file="footer.jsp" %>