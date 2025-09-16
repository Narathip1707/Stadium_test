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

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-12">
            <div class="card shadow-lg" style="border-radius: 15px; background: linear-gradient(135deg, #ffffff, #f8f9fa);">
                <div class="card-header text-center bg-danger text-white" style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                    <h3 class="card-title mb-0">
                        <i class="bi bi-person-gear me-2"></i>จัดการการจอง (แอดมิน)
                    </h3>
                </div>
                <div class="card-body p-4">
                    <div id="usersTable" class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-danger">
                                <tr>
                                    <th>ID</th>
                                    <th>ชื่อ</th>
                                    <th>เบอร์โทร</th>
                                    <th>อีเมล</th>
                                    <th>จัดการ</th>
                                </tr>
                            </thead>
                            <tbody id="usersBody">
                                <!-- ข้อมูลจะถูกโหลดด้วย Ajax -->
                            </tbody>
                        </table>
                        <div id="loading" class="text-center">
                            <div class="spinner-border text-danger" role="status">
                                <span class="visually-hidden">Loading...</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal สำหรับประวัติการจอง -->
    <div class="modal fade" id="bookingHistoryModal" tabindex="-1" aria-labelledby="bookingHistoryModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title" id="bookingHistoryModalLabel">ประวัติการจองของผู้ใช้</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="bookingHistoryModalBody" class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-danger">
                                <tr>
                                    <th>รหัสการจอง</th>
                                    <th>สนาม</th>
                                    <th>วันที่</th>
                                    <th>เวลาเริ่ม</th>
                                    <th>เวลาสิ้นสุด</th>
                                    <th>ราคา</th>
                                    <th>สถานะ</th>
                                </tr>
                            </thead>
                            <tbody id="bookingHistoryModalBodyContent">
                                <!-- ข้อมูลจะถูกโหลดด้วย Ajax -->
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">ปิด</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="assets/js/admin.js"></script>
