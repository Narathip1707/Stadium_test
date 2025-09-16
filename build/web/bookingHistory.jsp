<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!--<head>
    <link rel="stylesheet" href="css/booking-history.css">
</head>-->
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card shadow-lg" style="border-radius: 15px; background: linear-gradient(135deg, #ffffff, #f8f9fa);">
                <div class="card-header text-center bg-danger text-white" style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                    <h3 class="card-title mb-0">
                        <i class="bi bi-clock-history me-2"></i>ประวัติการจอง
                    </h3>
                </div>
                <div class="card-body p-4">
                    <div id="bookingHistoryTable" class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-danger">
                                <tr>
                                    <th>รหัสการจอง</th>
                                    <th>สนาม</th>
                                    <th>วันที่</th>
                                    <th>เวลาเริ่ม</th>
                                    <th>เวลาสิ้นสุด</th>
                                    <th>ราคา</th>
                                    <th>สถานะ</th>
                                </tr>
                            </thead>
                            <tbody id="bookingHistoryBody">
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
</div>
<script src="assets/js/jquery-3.7.1.min.js"></script>
<script src="assets/js/bookingHistory.js"></script>