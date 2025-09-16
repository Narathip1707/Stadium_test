// assets/js/login.js
$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault();

        let username = $("#username").val().trim();
        let password = $("#password").val().trim();

        if (username === "" || password === "") {
            Swal.fire({
                icon: 'warning',
                title: '⚠️ กรุณากรอกข้อมูลให้ครบถ้วน',
                showConfirmButton: true
            });
            return;
        }

        console.log("📤 ส่งข้อมูลไปที่เซิร์ฟเวอร์...");

        $.ajax({
            url: "LoginServlet",
            type: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {username: username, password: password},
            dataType: "json",
            success: function (response) {
                console.log("📥 ได้รับข้อมูลจากเซิร์ฟเวอร์:", response);
                if (response.status === "success") {
                    Swal.fire({
                        icon: 'success',
                        title: response.message,
                        showConfirmButton: true
                    }).then(() => {
                        window.location.href = response.redirect;
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: response.message
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error("❌ AJAX Error:", error, xhr.responseText);
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถเชื่อมต่อได้: ' + (xhr.responseText || error)
                });
            }
        });
    });

    // การจัดการคลิก "ลืมรหัสผ่าน"
    $("#forgotPasswordLink").click(function (event) {
        event.preventDefault();

        Swal.fire({
            title: 'รีเซ็ตรหัสผ่าน',
            html: `
                <input type="email" id="swal-email" class="swal2-input" placeholder="กรอกอีเมล" style="width: 80%;">
                <input type="password" id="swal-new-password" class="swal2-input" placeholder="กรอกรหัสผ่านใหม่ (อย่างน้อย 8 ตัวอักษร)" style="width: 80%;">
            `,
            showCancelButton: true,
            confirmButtonText: 'รีเซ็ต',
            cancelButtonText: 'ยกเลิก',
            confirmButtonColor: '#DA291C',
            cancelButtonColor: '#666666',
            preConfirm: () => {
                let email = $("#swal-email").val().trim();
                let newPassword = $("#swal-new-password").val().trim();

                if (!email) {
                    Swal.showValidationMessage('กรุณากรอกอีเมล!');
                    return false;
                }
                if (!/\S+@\S+\.\S+/.test(email)) {
                    Swal.showValidationMessage('กรุณากรอกอีเมลที่ถูกต้อง!');
                    return false;
                }
                if (!newPassword) {
                    Swal.showValidationMessage('กรุณากรอกรหัสผ่านใหม่!');
                    return false;
                }
                if (newPassword.length < 8) {
                    Swal.showValidationMessage('รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร!');
                    return false;
                }

                return {email: email, newPassword: newPassword};
            }
        }).then((result) => {
            if (result.isConfirmed) {
                let {email, newPassword} = result.value;
                console.log("📤 ส่งคำขอรีเซ็ตรหัสผ่านสำหรับอีเมล:", email);

                $.ajax({
                    url: "ForgotPasswordServlet",
                    type: "POST",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    data: {email: email, newPassword: newPassword},
                    dataType: "json",
                    success: function (response) {
                        console.log("📥 Response:", response);
                        if (response.status === "success") {
                            Swal.fire({
                                icon: 'success',
                                title: 'รีเซ็ตสำเร็จ!',
                                text: 'รหัสผ่านของคุณถูกเปลี่ยนเรียบร้อยแล้ว',
                                confirmButtonColor: '#DA291C'
                            });
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'เกิดข้อผิดพลาด',
                                text: response.message || 'ไม่สามารถรีเซ็ตได้',
                                confirmButtonColor: '#DA291C'
                            });
                        }
                    },
                    error: function (xhr, status, error) {
                        console.error("❌ AJAX Error:", error, xhr.responseText);
                        Swal.fire({
                            icon: 'error',
                            title: 'เกิดข้อผิดพลาด',
                            text: 'ไม่สามารถเชื่อมต่อได้: ' + (xhr.responseText || error),
                            confirmButtonColor: '#DA291C'
                        });
                    }
                });
            }
        });
    });
});