package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import dao.UserDAO;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author Narathip
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // ตั้งค่า encoding สำหรับ request
        response.setContentType("application/json;charset=UTF-8"); // ตั้งค่า encoding สำหรับ JSON response

        System.out.println("🔍 LoginServlet ถูกเรียกใช้งาน!");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("📌 รับค่าจากฟอร์ม: Username = " + username);

        JSONObject jsonResponse = new JSONObject();

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByUsernameAndPassword(username.trim(), password.trim());

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user.getUsername());
                session.setAttribute("userId", user.getId());
                session.setAttribute("isAdmin", user.isAdmin());

                jsonResponse.put("status", "success");
                jsonResponse.put("message", "✅ เข้าสู่ระบบสำเร็จ!");
                jsonResponse.put("redirect", user.isAdmin() ? "admin.jsp" : "index.jsp"); // เปลี่ยนหน้าให้ JavaScript
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "❌ ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
            }

        } catch (Exception e) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "❌ เกิดข้อผิดพลาด: " + e.getMessage());
            System.err.println("🚨 Error in LoginServlet: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("✅ LoginServlet ถูกเรียก!");
        System.out.println("📤 ส่ง JSON Response: " + jsonResponse.toString());
        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}
