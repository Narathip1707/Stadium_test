$(document).ready(function () {
    loadBookingHistory();

    function loadBookingHistory() {
        $.ajax({
            url: "BookingHistoryServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {action: "getHistory"},
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
                                <td>
                                    ${booking.paymentStatus}
                                    ${booking.paymentStatus === 'unpaid' ?
                                '<a href="payment.jsp?bookingId=' + booking.bookingId + '" class="btn btn-success btn-sm ms-2">ชำระ</a>' +
                                '<button class="btn btn-danger btn-sm ms-2 cancel-btn" data-booking-id="' + booking.bookingId + '">ยกเลิก</button>' :
                                ''}
                                </td>
                            </tr>
                        `;
                    });
                    $("#bookingHistoryBody").html(html);
                    $("#bookingHistoryBody").show();
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
            },
            complete: function () {
                $("#loading").hide();
            }
        });
    }


    // จัดการยกเลิกการจอง
    $(document).on("click", ".cancel-btn", function () {
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
                    url: "BookingHistoryServlet",
                    method: "POST",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    data: {action: "cancel", bookingId: bookingId},
                    dataType: "json",
                    success: function (response) {
                        if (response.status === "success") {
                            Swal.fire({
                                icon: 'success',
                                title: response.message
                            }).then(() => {
                                loadBookingHistory(); // โหลดประวัติใหม่หลังยกเลิก
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