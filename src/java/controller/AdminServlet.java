package controller;

import dao.BookingDAO;
import dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AdminServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "กรุณาเข้าสู่ระบบก่อน");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "คุณไม่มีสิทธิ์เข้าถึง");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        Integer currentUserId = (Integer) session.getAttribute("userId"); // เปลี่ยนชื่อเป็น currentUserId เพื่อหลีกเลี่ยง conflict
        AdminDAO adminDAO = new AdminDAO();
        BookingDAO bookingDAO = new BookingDAO();

        try {
            if ("getUsers".equals(action)) {
                // ดึงรายชื่อผู้ใช้ทั้งหมด
                List<User> users = adminDAO.getAllUsers();
                JSONArray jsonArray = new JSONArray();
                for (User user : users) {
                    JSONObject jsonUser = new JSONObject();
                    jsonUser.put("id", user.getId());
                    jsonUser.put("username", user.getUsername()); // เพิ่ม username
                    jsonUser.put("firstName", user.getFirstName()); // แยก firstName
                    jsonUser.put("lastName", user.getLastName()); // แยก lastName
                    jsonUser.put("name", user.getFirstName() + " " + user.getLastName());
                    jsonUser.put("phone", user.getPhone());
                    jsonUser.put("email", user.getEmail());
                    jsonUser.put("phone", user.getPhone()); // ซ้ำ, ลบหนึ่งอัน
                    jsonUser.put("isAdmin", user.isAdmin() ? 1 : 0); // ส่งเป็น int (0/1) สำหรับ JSON
                    jsonArray.put(jsonUser);
                }

                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("status", "success");
                jsonResponse.put("users", jsonArray);
                response.getWriter().write(jsonResponse.toString());
            } else if ("updateUser".equals(action)) {
                String userIdStr = request.getParameter("userId");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");

                if (userIdStr == null || firstName == null || lastName == null || email == null || phone == null
                        || userIdStr.trim().isEmpty() || firstName.trim().isEmpty() || lastName.trim().isEmpty()
                        || email.trim().isEmpty() || phone.trim().isEmpty()) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "ข้อมูลไม่ครบถ้วน");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                int userId = Integer.parseInt(userIdStr); // ใช้ userId ใน scope นี้
                User user = new User();
                user.setId(userId);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhone(phone);

                adminDAO.updateUser(user);
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "อัปเดตข้อมูลผู้ใช้สำเร็จ");
                response.getWriter().write(jsonResponse.toString());
            } else if ("getUserBookings".equals(action)) {
                // ดึงประวัติการจองของผู้ใช้
                String userIdStr = request.getParameter("userId");
                if (userIdStr == null || userIdStr.trim().isEmpty()) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "กรุณาระบุ ID ผู้ใช้");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                int userId = Integer.parseInt(userIdStr); // ใช้ userId ใน scope นี้
                List<Booking> bookings = bookingDAO.getBookingsByUser(userId);
                JSONArray jsonArray = new JSONArray();
                for (Booking booking : bookings) {
                    JSONObject jsonBooking = new JSONObject();
                    jsonBooking.put("bookingId", booking.getBookingId());

                    // ดึงชื่อสนามจาก fields
                    String fieldName = booking.getFieldType(); // สมมติ field_type เป็นชื่อสนามชั่วคราว
                    try {
                        // ควรใช้ AdminDAO หรือ BookingDAO ดึงชื่อจาก fields
                        fieldName = adminDAO.getFieldName(booking.getFieldType()); // สมมติมี method นี้
                    } catch (SQLException e) {
                        logger.error("Error fetching field name: {}", e.getMessage(), e);
                    }

                    jsonBooking.put("fieldName", fieldName);
                    jsonBooking.put("bookingDate", booking.getBookingDate().toString());
                    jsonBooking.put("startTime", booking.getStartTime().toString());
                    jsonBooking.put("endTime", booking.getEndTime().toString());
                    jsonBooking.put("totalPrice", booking.getTotalPrice());
                    jsonBooking.put("paymentStatus", booking.getPaymentStatus());
                    jsonArray.put(jsonBooking);
                }

                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("status", "success");
                jsonResponse.put("bookings", jsonArray);
                response.getWriter().write(jsonResponse.toString());
            } else if ("cancelBooking".equals(action)) {
                // ยกเลิกการจองเฉพาะรายการ
                String bookingIdStr = request.getParameter("bookingId");
                if (bookingIdStr == null || bookingIdStr.trim().isEmpty()) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "กรุณาระบุรหัสการจอง");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                int bookingId = Integer.parseInt(bookingIdStr);
                Booking booking = bookingDAO.getBookingById(bookingId);
                if (booking == null) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "ไม่พบข้อมูลการจอง");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                if (!"unpaid".equals(booking.getPaymentStatus())) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "ไม่สามารถยกเลิกได้ เนื่องจากไม่ใช่สถานะ 'unpaid'");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                boolean cancelled = bookingDAO.cancelBooking(bookingId);
                JSONObject jsonResponse = new JSONObject();
                if (cancelled) {
                    jsonResponse.put("status", "success");
                    jsonResponse.put("message", "ยกเลิกการจองสำเร็จ");
                } else {
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "ไม่สามารถยกเลิกได้");
                }
                response.getWriter().write(jsonResponse.toString());
            } else if ("deleteUser".equals(action)) {
                // ลบบัญชีผู้ใช้
                String userIdStr = request.getParameter("userId");
                if (userIdStr == null || userIdStr.trim().isEmpty()) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "กรุณาระบุ ID ผู้ใช้");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                int userId = Integer.parseInt(userIdStr); // ใช้ userId ใน scope นี้
                User user = adminDAO.getUserById(userId);
                if (user == null) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "ไม่พบผู้ใช้");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                if (user.isAdmin()) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "ไม่สามารถลบผู้ใช้ที่เป็นแอดมินได้");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                // ตรวจสอบว่า user มี bookings หรือ payments หรือไม่ (สมมติลบได้ถ้าไม่มี)
                if (!bookingDAO.getBookingsByUser(userId).isEmpty()) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "ไม่สามารถลบผู้ใช้ที่มีการจอง");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                adminDAO.deleteUser(userId);
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "ลบบัญชีผู้ใช้สำเร็จ");
                response.getWriter().write(jsonResponse.toString());
            }
        } catch (SQLException e) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "เกิดข้อผิดพลาด: " + e.getMessage());
            response.getWriter().write(jsonResponse.toString());
            logger.error("Database error in AdminServlet: {}", e.getMessage(), e);
        } catch (NumberFormatException e) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "ID ผู้ใช้ไม่ถูกต้อง: " + e.getMessage());
            response.getWriter().write(jsonResponse.toString());
            logger.error("Invalid data format in AdminServlet: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
