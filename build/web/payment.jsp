<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="dao.BookingDAO" %>
<%@ page import="model.Booking" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ชำระเงิน</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/payment.css"/>
</head>
<body class="content-body">
    <%@ include file="header.jsp" %>
    <div class="container content-container">
        <h2 class="content-header">ชำระเงินค่าจองสนาม</h2>

        <!-- Loading Overlay -->
        <div id="loadingOverlay" class="loading-overlay">
            <div class="loading-spinner"></div>
        </div>

        <!-- Payment Success Message -->
        <div id="paymentSuccess" class="payment-success-container fade-in">
            <span class="payment-success-icon">✓</span>
            <h3>ชำระเงินสำเร็จ!</h3>
            <p>ขอบคุณที่ใช้บริการ การจองสนามของคุณได้รับการยืนยันแล้ว</p>
            <a href="booking.jsp" class="btn btn-primary mt-3">กลับไปหน้าจองสนาม</a>
        </div>

        <%
            HttpSession userSession = request.getSession(false);
            if (userSession == null || userSession.getAttribute("userId") == null) {
        %>
        <div class="alert alert-danger text-center fade-in">
            กรุณาเข้าสู่ระบบก่อนทำการชำระเงิน
        </div>
        <div class="text-center mt-3">
            <a href="login.jsp" class="btn btn-primary">เข้าสู่ระบบ</a>
        </div>
        <%
            } else {
                int userId = (Integer) userSession.getAttribute("userId");
                BookingDAO bookingDAO = new BookingDAO();
                List<Booking> unpaidBookings = bookingDAO.getUnpaidBookingsByUser(userId);

                if (unpaidBookings == null || unpaidBookings.isEmpty()) {
        %>
        <div class="alert alert-info text-center fade-in">
            ไม่มีรายการที่ต้องชำระเงิน
        </div>
        <div class="text-center mt-3">
            <a href="booking.jsp" class="btn btn-primary">กลับไปหน้าจองสนาม</a>
        </div>
        <%
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                double totalAmount = 0;
                StringBuilder bookingIds = new StringBuilder();

                for (int i = 0; i < unpaidBookings.size(); i++) {
                    Booking booking = unpaidBookings.get(i);
                    totalAmount += booking.getTotalPrice();
                    bookingIds.append(booking.getBookingId());
                    if (i < unpaidBookings.size() - 1) {
                        bookingIds.append(",");
                    }
                }
        %>
        <div id="paymentContent" class="fade-in">
            <div class="alert alert-info mb-4 text-center">
                คุณมี <strong><%= unpaidBookings.size() %></strong> รายการที่รอชำระเงิน
            </div>

            <div class="bookings-list mb-5">
                <h4 class="content-header">รายการจองสนาม</h4>
                <% for (Booking booking : unpaidBookings) { %>
                <div class="booking-item">
                    <div class="row align-items-center">
                        <div class="col-md-8 col-7">
                            <p class="mb-2"><span class="field-type field-<%= booking.getFieldType() %>">สนาม <%= booking.getFieldType() %></span></p>
                            <p class="mb-1">วันที่: <%= dateFormat.format(booking.getBookingDate()) %></p>
                            <p class="mb-0">เวลา: <%= timeFormat.format(booking.getStartTime()) %> - <%= timeFormat.format(booking.getEndTime()) %></p>
                        </div>
                        <div class="col-md-4 col-5 text-end">
                            <p class="fw-bold mb-2">ราคา: <%= booking.getTotalPrice() %> บาท</p>
                            <button class="btn btn-danger btn-sm cancel-btn" data-booking-id="<%= booking.getBookingId() %>">ยกเลิก</button>
                        </div>
                    </div>
                </div>
                <% } %>
                <div class="total-amount mt-4 p-3 rounded" style="background-color: var(--manutd-gray);">
                    <h5 class="text-end mb-0">ยอดรวมทั้งสิ้น: <span class="text-success"><%= totalAmount %> บาท</span></h5>
                </div>
            </div>

            <div class="payment-form">
                <h4 class="content-header">รายละเอียดการชำระเงิน</h4>
                <form id="paymentForm" action="PaymentServlet" method="POST">
                    <input type="hidden" name="bookingIds" value="<%= bookingIds.toString() %>">
                    <input type="hidden" name="amount" value="<%= totalAmount %>">
                    <input type="hidden" name="action" value="processPayment">

                    <div class="mb-4">
                        <label for="paymentMethod" class="form-label fw-bold">วิธีการชำระเงิน</label>
                        <select class="form-select" id="paymentMethod" name="paymentMethod" required onchange="togglePaymentDetails()">
                            <option value="">เลือกวิธีการชำระเงิน</option>
                            <option value="qrCode">QR Code</option>
                            <option value="bankTransfer">โอนเงินผ่านธนาคาร</option>
                        </select>
                    </div>

                    <div id="qrCodeDetails" class="payment-details fade-in text-center">
                        <p class="fw-bold mb-3">สแกน QR Code เพื่อชำระเงิน</p>
                        <img src="assets/images/qrcode.png" alt="QR Code" class="img-fluid mb-3" style="max-width: 250px;">
                        <p>ยอดเงิน: <strong><%= totalAmount %> บาท</strong></p>
                    </div>

                    <div id="bankTransferDetails" class="payment-details fade-in">
                        <p class="fw-bold mb-2">โอนเงินมาที่บัญชี:</p>
                        <ul class="list-unstyled mb-3">
                            <li>ธนาคารกสิกรไทย: 053-8797-758</li>
                            <li>ชื่อบัญชี: นราธิป มณีพัทธพงศ์</li>
                            <li>จำนวนเงิน: <strong><%= totalAmount %> บาท</strong></li>
                        </ul>
                        <p class="text-muted">กรุณาแจ้งหลักฐานการโอนเงินที่ Line ID: toony0001</p>
                    </div>

                    <div class="text-center mt-4">
                        <button type="button" id="confirmPaymentBtn" class="btn btn-primary" onclick="processPayment()">ยืนยันการชำระเงิน</button>
                        <a href="booking.jsp" class="btn btn-secondary ms-2">ยกเลิก</a>
                        <a href="booking.jsp" class="btn btn-secondary ms-2">จองเพิ่ม</a>
                    </div>
                </form>
            </div>
        </div>
        <%
                String paymentResult = (String) request.getAttribute("paymentResult");
                if (paymentResult != null) {
        %>
        <div class="mt-4 alert alert-info fade-in text-center">
            <%= paymentResult %>
        </div>
        <%
                }
            }
        }
        %>
    </div>

    <script src="assets/js/payment.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>