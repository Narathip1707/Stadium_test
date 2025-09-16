<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-lg" style="border-radius: 15px; background: linear-gradient(135deg, #ffffff, #f8f9fa);">
                <div class="card-header text-center bg-danger text-white" style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                    <h3 class="card-title mb-0">
                        <i class="bi bi-person-plus-fill me-2"></i>สมัครสมาชิก
                    </h3>
                </div>
                <div class="card-body p-4">
                    <%                        String errorMessage = (String) request.getAttribute("errorMessage");
                        String successMessage = (String) request.getAttribute("successMessage");
                        if (errorMessage != null) {%>
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle me-2"></i><%= errorMessage%>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <% }
                        if (successMessage != null) {%>
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle me-2"></i><%= successMessage%>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <% }%>

                    <form id="registerForm" class="needs-validation" novalidate>
                        <!-- Username -->
                        <div class="mb-3">
                            <label for="username" class="form-label"><i class="bi bi-person me-2"></i>ชื่อผู้ใช้</label>
                            <input type="text" class="form-control" id="username" name="username" required
                                   minlength="4" maxlength="50" pattern="[a-zA-Z0-9_]+"
                                   placeholder="ตัวอักษร A-Z, 0-9, และ _ เท่านั้น (4-50 ตัว)">
                            <div class="invalid-feedback">กรุณากรอกชื่อผู้ใช้ที่ถูกต้อง (4-50 ตัวอักษร, ใช้ A-Z, 0-9, และ _ เท่านั้น)</div>
                        </div>

                        <!-- Email -->
                        <div class="mb-3">
                            <label for="email" class="form-label"><i class="bi bi-envelope me-2"></i>อีเมล</label>
                            <input type="email" class="form-control" id="email" name="email" required
                                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
                                   placeholder="example@email.com">
                            <div class="invalid-feedback">กรุณากรอกอีเมลที่ถูกต้อง</div>
                        </div>

                        <!-- Phone -->
                        <div class="mb-3">
                            <label for="phone" class="form-label"><i class="bi bi-telephone me-2"></i>เบอร์โทรศัพท์</label>
                            <input type="tel" class="form-control" id="phone" name="phone" 
                                   pattern="\\d{10}"
                                   placeholder="0xxxxxxxxx">
                            <div class="invalid-feedback">กรุณากรอกเบอร์โทรศัพท์ 10 ตัวเลข</div>
                        </div>

                        <!-- First Name -->
                        <div class="mb-3">
                            <label for="firstName" class="form-label"><i class="bi bi-person-fill me-2"></i>ชื่อ</label>
                            <input type="text" class="form-control" id="firstName" name="firstName"
                                   pattern="[A-Za-zก-๙\\s]+"
                                   placeholder="ชื่อ (ใช้ตัวอักษรไทยหรืออังกฤษ)">
                            <div class="invalid-feedback">กรุณากรอกชื่อ (ใช้ตัวอักษรเท่านั้น)</div>
                        </div>

                        <!-- Last Name -->
                        <div class="mb-3">
                            <label for="lastName" class="form-label"><i class="bi bi-person-fill me-2"></i>นามสกุล</label>
                            <input type="text" class="form-control" id="lastName" name="lastName"
                                   pattern="[A-Za-zก-๙\\s]+"
                                   placeholder="นามสกุล (ใช้ตัวอักษรไทยหรืออังกฤษ)">
                            <div class="invalid-feedback">กรุณากรอกนามสกุล (ใช้ตัวอักษรเท่านั้น)</div>
                        </div>

                        <!-- Password -->
                        <div class="mb-3">
                            <label for="password" class="form-label"><i class="bi bi-lock-fill me-2"></i>รหัสผ่าน</label>
                            <input type="password" class="form-control" id="password" name="password" required
                                   minlength="8"
                                   pattern=".{8,}"
                                   placeholder="อย่างน้อย 8 ตัวอักษร">
                            <div class="invalid-feedback">รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร</div>
                        </div>

                        <!-- Confirm Password -->
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label"><i class="bi bi-lock-fill me-2"></i>ยืนยันรหัสผ่าน</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required
                                   minlength="8">
                            <div class="invalid-feedback" id="confirmPasswordFeedback">รหัสผ่านและยืนยันรหัสผ่านต้องตรงกัน</div>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-danger btn-lg" id="submitBtn" disabled>
                                <i class="bi bi-check-circle me-2"></i>สมัครสมาชิก
                            </button>
                        </div>
                    </form>
                </div>
                <div class="card-footer text-center text-muted">
                    <small>มีบัญชีอยู่แล้ว? <a href="login.jsp" class="text-danger">เข้าสู่ระบบ</a></small>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="assets/js/register.js"></script>
<%@ include file="footer.jsp" %>