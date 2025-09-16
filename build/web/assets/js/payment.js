/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function togglePaymentDetails() {
    const paymentMethod = document.getElementById('paymentMethod').value;
    const paymentDetails = document.querySelectorAll('.payment-details');
    paymentDetails.forEach(detail => detail.style.display = 'none');
    if (paymentMethod === 'qrCode') {
        document.getElementById('qrCodeDetails').style.display = 'block';
    } else if (paymentMethod === 'bankTransfer') {
        document.getElementById('bankTransferDetails').style.display = 'block';
    }
}

function cancelBooking(bookingId) {
    Swal.fire({
        title: 'ยืนยันการยกเลิก?',
        text: 'คุณแน่ใจหรือไม่ว่าต้องการยกเลิกรายการนี้?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'ใช่, ยกเลิก!',
        cancelButtonText: 'ยกเลิก'
    }).then((result) => {
        if (result.isConfirmed) {
            const loadingOverlay = document.getElementById('loadingOverlay');
            loadingOverlay.style.display = 'flex';

            fetch('PaymentServlet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    action: 'cancelBooking',
                    bookingId: bookingId
                })
            })
                    .then(response => response.json())
                    .then(data => {
                        loadingOverlay.style.display = 'none';
                        if (data.status === 'success') {
                            Swal.fire({
                                icon: 'success',
                                title: data.message
                            }).then(() => {
                                // อัปเดต UI และยอดรวม
                                window.location.reload();
                                const bookingItem = document.querySelector(`.booking-item[data-booking-id="${bookingId}"]`);
                                if (bookingItem) {
                                    const price = parseFloat(bookingItem.querySelector('.fw-bold').textContent.replace(' บาท', ''));
                                    const currentTotal = parseFloat(document.getElementById('totalAmount').textContent);
                                    const newTotal = currentTotal - price;
                                    document.getElementById('totalAmount').textContent = newTotal;
                                    document.getElementById('amount').value = newTotal;
                                    document.getElementById('bookingIds').value = Array.from(document.querySelectorAll('.booking-item'))
                                            .filter(item => item.style.display !== 'none')
                                            .map(item => item.getAttribute('data-booking-id'))
                                            .join(',');
                                    bookingItem.style.display = 'none';

                                    // ตรวจสอบว่ามีรายการเหลือหรือไม่
                                    if (document.querySelectorAll('.booking-item').length === document.querySelectorAll('.booking-item[style="display: none;"]').length) {
                                        window.location.reload();
                                    }
                                }
                            });
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'เกิดข้อผิดพลาด',
                                text: data.message
                            });
                        }
                    })
                    .catch(error => {
                        loadingOverlay.style.display = 'none';
                        Swal.fire({
                            icon: 'error',
                            title: 'เกิดข้อผิดพลาด',
                            text: 'ไม่สามารถยกเลิกได้: ' + error.message
                        });
                        console.error('Fetch error:', error);
                    });
        }
    });
}

function processPayment() {
    const paymentMethod = document.getElementById('paymentMethod').value;
    if (!paymentMethod) {
        alert('กรุณาเลือกวิธีการชำระเงิน');
        return;
    }

    const loadingOverlay = document.getElementById('loadingOverlay');
    loadingOverlay.style.display = 'flex';

    const form = document.getElementById('paymentForm');
    const formData = new FormData(form);

    // แปลง FormData เป็น URLSearchParams สำหรับการส่งแบบ application/x-www-form-urlencoded
    const urlParams = new URLSearchParams();
    for (const pair of formData.entries()) {
        urlParams.append(pair[0], pair[1]);
    }

    fetch('PaymentServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: urlParams
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.status);
                }
                return response.text();
            })
            .then(data => {
                console.log('Response data:', data); // ดูข้อมูลที่ได้รับกลับมา
                loadingOverlay.style.display = 'none';

                try {
                    // พยายาม parse เป็น JSON
                    const jsonData = JSON.parse(data);
                    if (jsonData.status === 'success') {
                        document.getElementById('paymentContent').style.display = 'none';
                        const successBox = document.getElementById('paymentSuccess');
                        successBox.style.display = 'block';
                        successBox.classList.add('fade-in');
                    } else {
                        alert('เกิดข้อผิดพลาด: ' + jsonData.message);
                    }
                } catch (e) {
                    console.error('JSON parse error:', e);
                    // ถ้า parse ไม่ได้ ให้ตรวจสอบว่าคำตอบมีคำว่า "สำเร็จ" หรือไม่
                    if (data.includes('สำเร็จ')) {
                        document.getElementById('paymentContent').style.display = 'none';
                        const successBox = document.getElementById('paymentSuccess');
                        successBox.style.display = 'block';
                        successBox.classList.add('fade-in');
                    } else {
                        alert('เกิดข้อผิดพลาดในการประมวลผลคำตอบจากเซิร์ฟเวอร์');
                    }
                }
            })
            .catch(error => {
                console.error('Fetch error:', error);
                loadingOverlay.style.display = 'none';
                alert('เกิดข้อผิดพลาดในการชำระเงิน: ' + error.message);
            });
}

// ตรวจสอบสถานะเมื่อโหลดหน้า
document.addEventListener('DOMContentLoaded', function () {
    togglePaymentDetails();

    // เพิ่ม event listener สำหรับปุ่มยกเลิก
    document.querySelectorAll('.cancel-btn').forEach(btn => {
        btn.addEventListener('click', () => cancelBooking(btn.getAttribute('data-booking-id')));
    });

    // ตรวจสอบ paymentResult จาก BookingServlet
    // นี่คือส่วนที่จะถูกแทนที่โดย JSP
    /* 
     <% if (request.getAttribute("paymentResult") != null && request.getAttribute("paymentResult").equals("จองสนามสำเร็จ รอการชำระเงิน")) { %>
     document.getElementById('paymentSuccess').style.display = 'none';
     <% } else if (request.getAttribute("paymentResult") != null && request.getAttribute("paymentResult").equals("ชำระเงินสำเร็จ")) { %>
     document.getElementById('paymentContent').style.display = 'none';
     document.getElementById('paymentSuccess').style.display = 'block';
     document.getElementById('paymentSuccess').classList.add('fade-in');
     <% }%>
     */
});

