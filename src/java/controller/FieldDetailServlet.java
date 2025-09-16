package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import db.DBConnection;

@WebServlet(name = "FieldDetailServlet", urlPatterns = {"/FieldDetailServlet"})
public class FieldDetailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // รับค่า field ID จาก parameter
            String fieldIdStr = request.getParameter("field");
            int fieldId = 0;
            
            try {
                // แปลง String เป็น int
                fieldId = Integer.parseInt(fieldIdStr);
            } catch (NumberFormatException e) {
                // ถ้าแปลงไม่ได้ (กรณีที่ส่งมาเป็นตัวอักษร เช่น A, B, C จากโค้ดเก่า)
                // ให้เปลี่ยนเป็นการค้นหาด้วย field_type แทน
                conn = DBConnection.getConnection();
                String sql = "SELECT * FROM fields WHERE field_type = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, fieldIdStr);
                rs = pstmt.executeQuery();
                
                request.setAttribute("fieldResult", rs);
                request.getRequestDispatcher("fieldDetail.jsp").forward(request, response);
                return;
            }
            
            // กรณีที่เป็น ID (ตัวเลข)
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM fields WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, fieldId);
            rs = pstmt.executeQuery();
            
            request.setAttribute("fieldResult", rs);
            request.getRequestDispatcher("fieldDetail.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Field Detail Servlet";
    }
}