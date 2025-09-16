// assets/js/register.js
$(document).ready(function () {
    const $form = $('#registerForm');
    const $submitBtn = $('#submitBtn');

    // Utility functions
    function isValidEmail(email) {
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email);
    }

    // ตรวจสอบ username ซ้ำทันทีเมื่อกรอกเสร็จ
    $('#username').on('blur', function () {
        let username = $(this).val().trim();
        if (username !== '') {
            $.ajax({
                url: "RegisterServlet",
                method: "POST",
                data: {action: "checkUsername", username: username},
                success: function (response) {
                    console.log("Response for username check:", response);
                    if (response === "taken") {
                        $('#username').addClass('is-invalid').removeClass('is-valid');
                        $('#username').next('.invalid-feedback').text('ชื่อผู้ใช้นี้ถูกใช้งานแล้ว');
                        $submitBtn.prop('disabled', true);
                    } else if (response === "available") {
                        $('#username').removeClass('is-invalid').addClass('is-valid');
                        checkFormValidity();
                    } else {
                        $('#username').addClass('is-invalid').removeClass('is-valid');
                        $('#username').next('.invalid-feedback').text('เกิดข้อผิดพลาดในการตรวจสอบ');
                        $submitBtn.prop('disabled', true);
                    }
                },
                error: function (xhr, status, error) {
                    console.error("Error checking username:", error, xhr.responseText);
                    $('#username').addClass('is-invalid').removeClass('is-valid');
                    $('#username').next('.invalid-feedback').text('เกิดข้อผิดพลาดในการตรวจสอบ: ' + error);
                    $submitBtn.prop('disabled', true);
                }
            });
        } else {
            $('#username').removeClass('is-valid is-invalid');
            checkFormValidity();
        }
    });

    // ตรวจสอบ email ซ้ำทันทีเมื่อกรอกเสร็จ
    $('#email').on('blur', function () {
        let email = $(this).val().trim();
        if (email !== '') {
            if (!isValidEmail(email)) {
                $('#email').addClass('is-invalid').removeClass('is-valid');
                $('#email').next('.invalid-feedback').text('รูปแบบอีเมลไม่ถูกต้อง');
                $submitBtn.prop('disabled', true);
                return;
            }
            $.ajax({
                url: "RegisterServlet",
                method: "POST",
                data: {action: "checkEmail", email: email},
                success: function (response) {
                    console.log("Response for email check:", response);
                    if (response === "taken") {
                        $('#email').addClass('is-invalid').removeClass('is-valid');
                        $('#email').next('.invalid-feedback').text('อีเมลนี้ถูกใช้งานแล้ว');
                        $submitBtn.prop('disabled', true);
                    } else if (response === "available") {
                        $('#email').removeClass('is-invalid').addClass('is-valid');
                        checkFormValidity();
                    } else {
                        $('#email').addClass('is-invalid').removeClass('is-valid');
                        $('#email').next('.invalid-feedback').text('เกิดข้อผิดพลาดในการตรวจสอบ');
                        $submitBtn.prop('disabled', true);
                    }
                },
                error: function (xhr, status, error) {
                    console.error("Error checking email:", error, xhr.responseText);
                    $('#email').addClass('is-invalid').removeClass('is-valid');
                    $('#email').next('.invalid-feedback').text('เกิดข้อผิดพลาดในการตรวจสอบ: ' + error);
                    $submitBtn.prop('disabled', true);
                }
            });
        } else {
            $('#email').removeClass('is-valid is-invalid');
            checkFormValidity();
        }
    });

    // ตรวจสอบรหัสผ่านและยืนยันรหัสผ่าน
    function checkPasswordMatch() {
        const password = $('#password').val();
        const confirmPassword = $('#confirmPassword').val();
        const $passwordField = $('#password');
        const $confirmPasswordField = $('#confirmPassword');
        const feedback = $('#confirmPasswordFeedback');

        if (password.length < 8) {
            $passwordField.addClass('is-invalid').removeClass('is-valid');
            $passwordField.next('.invalid-feedback').text('รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร');
            return false;
        }

        if (password !== confirmPassword) {
            $confirmPasswordField.addClass('is-invalid').removeClass('is-valid');
            feedback.removeClass('valid-feedback').addClass('invalid-feedback').text('รหัสผ่านและยืนยันรหัสผ่านต้องตรงกัน');
            return false;
        }

        $passwordField.removeClass('is-invalid').addClass('is-valid');
        $confirmPasswordField.removeClass('is-invalid').addClass('is-valid');
        feedback.removeClass('invalid-feedback').addClass('valid-feedback').text('รหัสผ่านตรงกัน');
        return true;
    }

    $('#password, #confirmPassword').on('input', function () {
        checkPasswordMatch();
        checkFormValidity();
    });

    // ตรวจสอบความถูกต้องของฟอร์ม
    function checkFormValidity() {
        const username = $('#username').val().trim();
        const email = $('#email').val().trim();
        const password = $('#password').val();
        const confirmPassword = $('#confirmPassword').val();

        const isValid =
                username !== '' &&
                email !== '' &&
                isValidEmail(email) &&
                password !== '' &&
                password.length >= 8 &&
                password === confirmPassword &&
                $('#username').hasClass('is-valid') &&
                $('#email').hasClass('is-valid');

        $submitBtn.prop('disabled', !isValid);
        return isValid;
    }

    // ตรวจสอบความถูกต้องเริ่มต้น
    $form.find('input').on('input', checkFormValidity);

    // ส่งข้อมูลผ่าน Ajax เมื่อกดปุ่มสมัครสมาชิก
    $form.on('submit', function (event) {
        event.preventDefault();

        // ตรวจสอบความถูกต้องอีกครั้งก่อนส่งข้อมูล
        if (!checkFormValidity()) {
            Swal.fire({
                icon: 'warning',
                title: 'กรุณากรอกข้อมูลให้ครบถ้วน',
                text: 'โปรดตรวจสอบ username, email, หรือ password อีกครั้ง'
            });
            return;
        }

        let formData = {
            action: "register",
            username: $('#username').val().trim(),
            email: $('#email').val().trim(),
            phone: $('#phone').val().trim(), // อนุญาตให้ว่างได้
            firstName: $('#firstName').val().trim(), // อนุญาตให้ว่างได้
            lastName: $('#lastName').val().trim(), // อนุญาตให้ว่างได้
            password: $('#password').val()
        };

        $.ajax({
            url: "RegisterServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8", // ตั้งค่า encoding
            data: formData,
            success: function (response) {
                console.log("Registration response:", response);
                if (response.includes("success")) {
                    Swal.fire({
                        icon: 'success',
                        title: 'สมัครสมาชิกสำเร็จ!',
                        text: 'กรุณาเข้าสู่ระบบ',
                        showConfirmButton: true
                    }).then(() => {
                        window.location.href = "login.jsp";
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: response.replace("error:", "")
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error("Error in registration:", error, xhr.responseText);
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถสมัครสมาชิกได้: ' + (xhr.responseText || error)
                });
            }
        });
    });

    // เรียกฟังก์ชันเริ่มต้น
    checkFormValidity();
});