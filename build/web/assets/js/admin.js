$(document).ready(function () {
    loadUsers();

    function loadUsers() {
        $("#loading").show();
        $("#usersBody").hide();

        $.ajax({
            url: "AdminServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {action: "getUsers"},
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    const users = response.users;
                    let html = "";
                    users.forEach(user => {
                        html += `
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.name}</td>
                                <td>${user.phone}</td>
                                <td>${user.email}</td>
                                <td>
                                    <button class="btn btn-info btn-sm view-bookings-btn" data-user-id="${user.id}">ดูประวัติการจอง</button>
                                </td>
                            </tr>
                        `;
                    });
                    $("#usersBody").html(html);
                    $("#usersBody").show();
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: response.message
                    });
                }
            },
            error: function (xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถโหลดรายชื่อผู้ใช้ได้: ' + error
                });
                console.error("Ajax Error:", status, error, xhr.responseText);
            },
            complete: function () {
                $("#loading").hide();
            }
        });
    }

    // จัดการดูและยกเลิกประวัติการจอง
    $(document).on("click", ".view-bookings-btn", function () {
        const userId = $(this).data("user-id");
        $.ajax({
            url: "AdminServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {action: "getUserBookings", userId: userId},
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    const bookings = response.bookings;
                    let html = "";
                    bookings.forEach(booking => {
                        html += `
                            <tr>
                                <td>${booking.bookingId}</td>
                                <td>${booking.fieldName}</td>
                                <td>${booking.bookingDate}</td>
                                <td>${booking.startTime}</td>
                                <td>${booking.endTime}</td>
                                <td>฿${booking.totalPrice.toLocaleString()}</td>
                                <td>${booking.paymentStatus}</td>
                                <td>
                                    ${booking.paymentStatus === 'unpaid' ?
                                '<button class="btn btn-danger btn-sm cancel-booking-btn" data-booking-id="' + booking.bookingId + '">ยกเลิก</button>' :
                                '-'}
                                </td>
                            </tr>
                        `;
                    });
                    $("#bookingHistoryModalBodyContent").html(html);
                    const modal = new bootstrap.Modal(document.getElementById('bookingHistoryModal'));
                    modal.show();
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: response.message
                    });
                }
            },
            error: function (xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถโหลดประวัติได้: ' + error
                });
                console.error("Ajax Error:", status, error, xhr.responseText);
            }
        });
    });

    // จัดการยกเลิกการจอง
    $(document).on("click", ".cancel-booking-btn", function () {
        const bookingId = $(this).data("booking-id");
        Swal.fire({
            title: 'ยืนยันการยกเลิก?',
            text: 'คุณแน่ใจหรือไม่ว่าต้องการยกเลิกการจองนี้?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'ใช่, ยกเลิก!',
            cancelButtonText: 'ยกเลิก'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "AdminServlet", // หรือ "PaymentServlet" ถ้าต้องการใช้ PaymentServlet
                    method: "POST",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    data: {action: "cancelBooking", bookingId: bookingId},
                    dataType: "json",
                    success: function (response) {
                        if (response.status === "success") {
                            Swal.fire({
                                icon: 'success',
                                title: response.message
                            }).then(() => {
                                loadUsers(); // โหลดข้อมูลผู้ใช้ใหม่ (หรืออัปเดต modal อย่างเดียว)
                                const modal = bootstrap.Modal.getInstance(document.getElementById('bookingHistoryModal'));
                                if (modal)
                                    modal.hide(); // ปิด modal หลังยกเลิก
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
                        Swal.fire({
                            icon: 'error',
                            title: 'เกิดข้อผิดพลาด',
                            text: 'ไม่สามารถยกเลิกได้: ' + error
                        });
                        console.error("Ajax Error:", status, error, xhr.responseText);
                    }
                });
            }
        });
    });
});