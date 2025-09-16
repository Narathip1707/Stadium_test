<%-- 
    Document   : fieldDetail
    Created on : 28 ก.พ. 2568, 11:20:19
    Author     : Narathip
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-lg" style="border-radius: 15px; background: linear-gradient(135deg, #ffffff, #f8f9fa);">
                <div class="card-header text-center bg-danger text-white" style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                    <h3 class="card-title mb-0">
                        <i class="bi bi-info-circle-fill me-2"></i>รายละเอียดสนาม
                    </h3>
                </div>
                <div class="card-body p-4">
                    <%
                        java.sql.ResultSet rs = (java.sql.ResultSet) request.getAttribute("fieldResult");
                        if (rs != null && rs.next()) {
                    %>
                    <div class="mb-3">
                        <img src="<%= rs.getString("image_url")%>" class="card-img-top img-fluid" alt="<%= rs.getString("name")%>" style="border-radius: 8px;">
                    </div>
                    <h4 class="card-title text-dark"><%= rs.getString("name")%></h4>
                    <p class="card-text text-muted"><%= rs.getString("description")%></p>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <p><strong>สถานที่:</strong> <%= rs.getString("location")%></p>
                            <p><strong>ราคา:</strong> ฿<%= rs.getDouble("price")%>/ชม.</p>
                            <p><strong>จำนวนคน/ทีม:</strong> <%= rs.getInt("capacity")%> คน</p>
                        </div>
                        <div class="col-md-6">
                            <p><strong>ชั่วโมงเปิด:</strong> <%= rs.getString("operating_hours")%></p>
                            <p><strong>สถานะ:</strong> <span class="text-<%= rs.getString("status").equals("available") ? "success" : "danger"%>"><%= rs.getString("status")%></span></p>
                        </div>
                    </div>
                    <div class="d-grid gap-2">
                        <a href="booking.jsp?field=<%= rs.getString("field_type")%>" class="btn btn-danger btn-lg">
                            <i class="bi bi-calendar-plus-fill me-2"></i>จองสนาม
                        </a>
                    </div>
                    <% } else { %>
                    <div class="alert alert-danger text-center">
                        <i class="bi bi-exclamation-triangle me-2"></i>ไม่พบข้อมูลสนาม
                    </div>
                    <% }%>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>