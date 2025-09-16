/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
    $(document).ready(function () {
        $.get("DashboardServlet", function (data) {
            console.log("📌 JSON Response:", data);

            if (data.status === "success") {
                $("#totalUsers").text(data.total_users);
                $("#totalBookings").text(data.total_bookings);
                // แสดงรายได้ทั้งหมดพร้อมจัดรูปแบบเป็นเงินบาท
                if (data.total_revenue !== undefined) {
                    $("#totalRevenue").text(data.total_revenue.toLocaleString() + " บาท");
                }
            } else {
                console.error("❌ Error: Invalid response from server");
            }
        }).fail(function () {
            console.error("❌ Failed to load dashboard data.");
        });
    });

