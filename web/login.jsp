<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>

<style>
    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
    }
    body {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }
    .container {
        flex: 1 0 auto;
    }
    footer {
        flex-shrink: 0;
        width: 100%;
    }
</style>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm" style="border-radius: 20px; background: #ffffff;">
                <div class="card-header text-center bg-white border-bottom-0" style="border-top-left-radius: 20px; border-top-right-radius: 20px;">
                    <h3 class="card-title mb-0" style="color: #DA291C;">
                        <i class="bi bi-box-arrow-in-right me-2"></i>เข้าสู่ระบบ
                    </h3>
                </div>
                <div class="card-body p-5">
                    <form id="loginForm" class="needs-validation" novalidate>
                        <div class="mb-4">
                            <label for="username" class="form-label" style="color: #333333;">
                                <i class="bi bi-person me-2"></i>บัญชีผู้ใช้
                            </label>
                            <input type="text" class="form-control form-control-lg" 
                                   id="username" name="username" required
                                   placeholder="กรุณากรอกชื่อผู้ใช้"
                                   style="border-radius: 10px; border: 1px solid #e0e0e0;">
                            <div class="invalid-feedback">กรุณากรอกชื่อผู้ใช้</div>
                        </div>
                        <div class="mb-4">
                            <label for="password" class="form-label" style="color: #333333;">
                                <i class="bi bi-lock-fill me-2"></i>รหัสผ่าน
                            </label>
                            <input type="password" class="form-control form-control-lg" 
                                   id="password" name="password" required
                                   minlength="8"
                                   placeholder="กรุณากรอกรหัสผ่านอย่างน้อย 8 ตัวอักษร"
                                   style="border-radius: 10px; border: 1px solid #e0e0e0;">
                            <div class="invalid-feedback">กรุณากรอกรหัสผ่านอย่างน้อย 8 ตัวอักษร</div>
                            <!-- เพิ่มลิงก์ลืมรหัสผ่าน -->
                            <div class="text-end mt-2">
                                <div class="text-end mt-2">
                                    <a href="forgot-password.jsp" id="forgotPasswordLink" class="text-decoration-none" style="color: #DA291C; font-size: 0.9rem;">
                                        ลืมรหัสผ่าน?
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-lg" 
                                    id="submitBtn"
                                    style="border-radius: 10px; background-color: #DA291C; border: none; transition: background-color 0.3s;">
                                <i class="bi bi-box-arrow-in-right me-2"></i>เข้าสู่ระบบ
                            </button>
                        </div>
                    </form>
                </div>
                <div class="card-footer text-center border-top-0" style="background: #ffffff; border-bottom-left-radius: 20px; border-bottom-right-radius: 20px;">
                    <small style="color: #666666;">ยังไม่มีบัญชี? 
                        <a href="register.jsp" class="text-decoration-none" style="color: #DA291C;">สมัครสมาชิก</a>
                    </small>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

<script src="assets/js/login.js"></script>