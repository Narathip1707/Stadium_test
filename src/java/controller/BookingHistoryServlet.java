package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "BookingHistoryServlet", urlPatterns = {"/BookingHistoryServlet"})
public class BookingHistoryServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BookingHistoryServlet.class);

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

        int userId = (Integer) session.getAttribute("userId");
        BookingDAO bookingDAO = new BookingDAO();

        try {
            if ("getHistory".equals(action)) {
                // ดึงประวัติการจองทั้งหมด
                List<Booking> bookings = bookingDAO.getBookingsByUser(userId);
                JSONArray jsonArray = new JSONArray();
                for (Booking booking : bookings) {
                    JSONObject jsonBooking = new JSONObject();
                    jsonBooking.put("bookingId", booking.getBookingId());
                    jsonBooking.put("fieldName", booking.getFieldType()); // หรือชื่อสนามจาก fields
                    jsonBooking.put("bookingDate", booking.getBookingDate().toString());
                    jsonBooking.put("startTime", booking.getStartTime().toString());
                    jsonBooking.put("endTime", booking.getEndTime().toString());
                    jsonBooking.put("totalPrice", booking.getTotalPrice());
                    jsonBooking.put("status", booking.getStatus());
                    jsonBooking.put("paymentStatus", booking.getPaymentStatus());
                    jsonArray.put(jsonBooking);
                }

                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("status", "success");
                jsonResponse.put("bookings", jsonArray);
                response.getWriter().write(jsonResponse.toString());
            } else if ("cancel".equals(action)) {
                // ยกเลิกรายการที่ยังไม่ชำระ
                String bookingIdStr = request.getParameter("bookingId");
                if (bookingIdStr == null || bookingIdStr.trim().isEmpty()) {
                    JSONObject jsonResponse = new JSONObject();
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "กรุณาระบุรหัสการจอง");
                    response.getWriter().write(jsonResponse.toString());
                    return;
                }

                int bookingId = Integer.parseInt(bookingIdStr);
                boolean cancelled = bookingDAO.cancelBooking(bookingId);

                JSONObject jsonResponse = new JSONObject();
                if (cancelled) {
                    jsonResponse.put("status", "success");
                    jsonResponse.put("message", "ยกเลิกการจองสำเร็จ");
                } else {
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "ไม่สามารถยกเลิกได้ อาจชำระเงินแล้วหรือไม่พบการจอง");
                }
                response.getWriter().write(jsonResponse.toString());
            }
        } catch (SQLException e) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "เกิดข้อผิดพลาด: " + e.getMessage());
            response.getWriter().write(jsonResponse.toString());
            logger.error("Database error in BookingHistoryServlet: {}", e.getMessage(), e);
        } catch (NumberFormatException e) {
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "รหัสการจองไม่ถูกต้อง: " + e.getMessage());
            response.getWriter().write(jsonResponse.toString());
            logger.error("Invalid bookingId format in BookingHistoryServlet: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("booking.jsp");
    }
}
