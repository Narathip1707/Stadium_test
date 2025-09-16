<%-- 
    Document   : dashboard
    Created on : 14 ก.พ. 2568, 17:45:50
    Author     : Narathip
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ include file="admin_header.jsp" %>

<div class="container mt-4">
    <h3>📊 Dashboard</h3>

    <div class="row">
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body text-center">
                    <h5 class="card-title">👥 สมาชิกทั้งหมด</h5>
                    <p class="card-text" id="totalUsers">Loading...</p>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body text-center">
                    <h5 class="card-title">📅 การจองสนาม</h5>
                    <p class="card-text" id="totalBookings">Loading...</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-body text-center">
                    <h5 class="card-title">💰 รายได้ทั้งหมด</h5>
                    <p class="card-text" id="totalRevenue">Loading...</p>
                </div>
            </div>
            
        </div>
    </div>
</div>
<script src="assets/js/dashboard.js"></script>


