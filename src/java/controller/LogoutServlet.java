package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ดึง session ปัจจุบัน (ถ้ามี)
        HttpSession session = request.getSession(false);
        if (session != null) {
            // ทำการลบข้อมูล session ทั้งหมด
            session.invalidate();
            System.out.println("Session invalidated"); // เพิ่ม debug
        }
        // เปลี่ยนเส้นทางกลับไปยังหน้า login (หรือ index.jsp ตามที่คุณต้องการ)
        response.sendRedirect("login.jsp"); // หรือ "index.jsp" ถ้าต้องการ
    }

    @Override
    public String getServletInfo() {
        return "Servlet สำหรับจัดการการ Logout";
    }
}
