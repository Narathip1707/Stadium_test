package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import dao.UserDAO;
import java.io.IOException;

/**
 *
 * @author Narathip
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8"); // ตั้งค่าเป็น plain text สำหรับ Ajax

        String action = request.getParameter("action");

        // ตรวจสอบ action ว่าเป็น null หรือไม่
        if (action == null || action.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("error:Invalid action");
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();

            switch (action) {
                case "checkUsername":
                    String username = request.getParameter("username");
                    if (username == null || username.trim().isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("error:Username is required");
                        return;
                    }
                    boolean isUsernameTaken = userDAO.findByUsername(username.trim());
                    System.out.println("Checking username: " + username + ", Result: " + isUsernameTaken);
                    response.getWriter().write(isUsernameTaken ? "taken" : "available");
                    break;

                case "checkEmail":
                    String email = request.getParameter("email");
                    if (email == null || email.trim().isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("error:Email is required");
                        return;
                    }
                    boolean isEmailTaken = userDAO.findByEmail(email.trim());
                    System.out.println("Checking email: " + email + ", Result: " + isEmailTaken);
                    response.getWriter().write(isEmailTaken ? "taken" : "available");
                    break;

                case "register":
                    // รับค่าพารามิเตอร์จาก Ajax
                    User user = new User();
                    user.setUsername(request.getParameter("username"));
                    user.setEmail(request.getParameter("email"));
                    user.setPhone(request.getParameter("phone")); // อนุญาตให้ว่างได้
                    user.setFirstName(request.getParameter("firstName")); // ไม่ตรวจสอบ
                    user.setLastName(request.getParameter("lastName")); // ไม่ตรวจสอบ
                    String password = request.getParameter("password");
                    if (password == null || password.trim().isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("error:Password is required");
                        return;
                    }
                    user.setPassword(password.trim()); // เก็บ plain text
                    user.setAdmin(false); // ตั้งค่าเป็นผู้ใช้ทั่วไป (is_admin = 0)

                    // ตรวจสอบความถูกต้องของข้อมูล (แค่ที่จำเป็น)
                    if (!user.isValid()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("error:ข้อมูลไม่ถูกต้อง กรุณาตรวจสอบ username, email, หรือ password");
                        return;
                    }

                    // ตรวจสอบชื่อผู้ใช้ซ้ำ
                    if (userDAO.findByUsername(user.getUsername())) {
                        response.setStatus(HttpServletResponse.SC_CONFLICT);
                        response.getWriter().write("error:ชื่อผู้ใช้นี้ถูกใช้งานแล้ว");
                        return;
                    }

                    // ตรวจสอบอีเมลซ้ำ
                    if (userDAO.findByEmail(user.getEmail())) {
                        response.setStatus(HttpServletResponse.SC_CONFLICT);
                        response.getWriter().write("error:อีเมลนี้ถูกใช้งานแล้ว");
                        return;
                    }

                    // บันทึกผู้ใช้
                    userDAO.saveUser(user);
                    response.getWriter().write("success:สมัครสมาชิกสำเร็จ! กรุณาเข้าสู่ระบบ");
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("error:Invalid action");
                    break;
            }
        } catch (Exception e) {
            // บันทึก error log
            e.printStackTrace();
            System.out.println("Error in RegisterServlet: " + e.getMessage());

            // ส่ง error response กลับไปยัง client
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("error:เกิดข้อผิดพลาดที่เซิร์ฟเวอร์ กรุณาลองใหม่อีกครั้ง: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}