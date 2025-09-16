// assets/js/login.js
$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault(); // ป้องกันการรีโหลดหน้า

        let username = $("#username").val().trim();
        let password = $("#password").val().trim();

        if (username === "" || password === "") {
            Swal.fire({// ใช้ SweetAlert2 แทน alert HTML
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
            contentType: "application/x-www-form-urlencoded; charset=UTF-8", // ตั้งค่า encoding สำหรับภาษาไทย
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
                        window.location.href = response.redirect; // เปลี่ยนหน้า
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
});