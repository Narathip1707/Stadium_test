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
                        <i class="bi bi-person-gear me-2"></i>จัดการผู้ใช้
                    </h3>
                </div>
                <div class="card-body p-4">
                    <div id="usersTable" class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-danger">
                                <tr>
                                    <th>ID</th>
                                    <th>ชื่อผู้ใช้</th>
                                    <th>ชื่อ</th>
                                    <th>นามสกุล</th>
                                    <th>อีเมล</th>
                                    <th>เบอร์โทร</th>
                                    <th>แอดมิน</th>
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

    <!-- Modal สำหรับแก้ไขข้อมูลผู้ใช้ -->
    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title" id="editUserModalLabel">แก้ไขข้อมูลผู้ใช้</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editUserForm">
                        <input type="hidden" id="editUserId" name="userId">
                        <div class="mb-3">
                            <label for="editFirstName" class="form-label">ชื่อ</label>
                            <input type="text" class="form-control" id="editFirstName" name="firstName" required>
                        </div>
                        <div class="mb-3">
                            <label for="editLastName" class="form-label">นามสกุล</label>
                            <input type="text" class="form-control" id="editLastName" name="lastName" required>
                        </div>
                        <div class="mb-3">
                            <label for="editEmail" class="form-label">อีเมล</label>
                            <input type="email" class="form-control" id="editEmail" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="editPhone" class="form-label">เบอร์โทร</label>
                            <input type="tel" class="form-control" id="editPhone" name="phone" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">บันทึก</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="assets/js/manage_users.js"></script>
