package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.JSONObject;

@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/ForgotPasswordServlet"})
public class ForgotPasswordServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        JSONObject jsonResponse = new JSONObject();

        try {
            if (email == null || email.trim().isEmpty() || newPassword == null || newPassword.trim().isEmpty()) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "กรุณากรอกข้อมูลให้ครบถ้วน");
            } else if (!userDAO.checkEmailExists(email)) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "ไม่พบอีเมลนี้ในระบบ");
            } else if (newPassword.length() < 8) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร");
            } else {
                userDAO.updatePassword(email, newPassword); // อัปเดตรหัสผ่านใหม่

                jsonResponse.put("status", "success");
                jsonResponse.put("message", "รหัสผ่านของคุณถูกเปลี่ยนเรียบร้อยแล้ว");
            }
        } catch (Exception e) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "เกิดข้อผิดพลาด: " + e.getMessage());
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }
}