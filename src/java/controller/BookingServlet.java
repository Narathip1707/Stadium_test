package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Booking;
import dao.BookingDAO;
import dao.FieldDAO;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "BookingServlet", urlPatterns = {"/BookingServlet"})
public class BookingServlet extends HttpServlet {

    private FieldDAO fieldDAO = new FieldDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        // ตรวจสอบการล็อกอิน
        if (session == null || session.getAttribute("userId") == null) {
            if ("check".equals(action) || "book".equals(action)) {
                response.getWriter().write("error:กรุณาเข้าสู่ระบบก่อน");
                return;
            }
            response.getWriter().write("error:กรุณาเข้าสู่ระบบก่อน");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        try {
            if ("check".equals(action)) {
                // ตรวจสอบความว่างของสนาม
                String fieldType = request.getParameter("field");
                String dateStr = request.getParameter("date");
                String startStr = request.getParameter("start");
                String endStr = request.getParameter("end");

                if (fieldType == null || dateStr == null || startStr == null || endStr == null
                        || fieldType.trim().isEmpty() || dateStr.trim().isEmpty()
                        || startStr.trim().isEmpty() || endStr.trim().isEmpty()) {
                    response.getWriter().write("not_available");
                    return;
                }

                try {
                    Date bookingDate = java.sql.Date.valueOf(dateStr);
                    Time startTime = java.sql.Time.valueOf(startStr + ":00");
                    Time endTime = java.sql.Time.valueOf(endStr + ":00");

                    boolean available = new BookingDAO().checkAvailability(fieldType, bookingDate, startTime, endTime);
                    System.out.println("Checking availability - Field: " + fieldType
                            + ", Date: " + bookingDate
                            + ", Start: " + startTime
                            + ", End: " + endTime
                            + ", Available: " + available);

                    response.getWriter().write(available ? "available" : "not_available");
                } catch (Exception e) {
                    System.out.println("Error checking availability: " + e.getMessage());
                    e.printStackTrace();
                    response.getWriter().write("not_available");
                }
            } else if ("book".equals(action)) {
                // จองสนาม
                String fieldType = request.getParameter("field");
                String dateStr = request.getParameter("date");
                String timeRanges = request.getParameter("timeRanges");

                if (fieldType == null || dateStr == null || timeRanges == null
                        || fieldType.trim().isEmpty() || dateStr.trim().isEmpty()
                        || timeRanges.trim().isEmpty()) {
                    response.getWriter().write("error:กรุณากรอกข้อมูลให้ครบถ้วน");
                    return;
                }

                try {
                    Date bookingDate = java.sql.Date.valueOf(dateStr);
                    String[] ranges = timeRanges.split(";");
                    int totalPrice = 0;

                    // ตรวจสอบความว่างของทุกช่วงเวลา
                    for (String range : ranges) {
                        String[] times = range.split("-");
                        Time startTime = java.sql.Time.valueOf(times[0] + ":00");
                        Time endTime = java.sql.Time.valueOf(times[1] + ":00");

                        if (!new BookingDAO().checkAvailability(fieldType, bookingDate, startTime, endTime)) {
                            response.getWriter().write("error:สนามถูกจองแล้ว กรุณาเลือกช่วงเวลาอื่น");
                            return;
                        }
                    }
                    // ดึงราคาจาก FieldDAO
                    ResultSet rs = fieldDAO.getFieldByFieldType(fieldType);
                    double pricePerHour = -1;
                    if (rs.next()) {
                        pricePerHour = rs.getDouble("price");
                    }
                    rs.close();
                    if (pricePerHour == -1) {
                        response.getWriter().write("error:ประเภทสนามไม่ถูกต้อง");
                        return;
                    }
                    // ทำการจองทุกช่วงเวลา
                    BookingDAO bookingDAO = new BookingDAO();
                    for (String range : ranges) {
                        String[] times = range.split("-");
                        Time startTime = java.sql.Time.valueOf(times[0] + ":00");
                        Time endTime = java.sql.Time.valueOf(times[1] + ":00");
                        
                        long hours = (endTime.getTime() - startTime.getTime()) / (60 * 60 * 1000);
                        if (hours <= 0) {
                            response.getWriter().write("error:ช่วงเวลาไม่ถูกต้อง");
                            return;
                        }

                        int slotPrice = (int) Math.ceil(hours * pricePerHour);
                        totalPrice += slotPrice;

                        // สร้างและบันทึกการจอง
                        Booking booking = new Booking();
                        booking.setUserId(userId);
                        booking.setBookingDate(bookingDate);
                        booking.setStartTime(startTime);
                        booking.setEndTime(endTime);
                        booking.setFieldType(fieldType);
                        booking.setTotalPrice(slotPrice);
                        booking.setStatus("confirmed");
                        booking.setPaymentStatus("unpaid");
                        booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                        bookingDAO.saveBooking(booking);
                    }

                    response.getWriter().write("success:จองสนามสำเร็จ! ราคารวม: " + totalPrice + " บาท");
//                    response.sendRedirect("payment.jsp");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid data format: " + e.getMessage());
                    e.printStackTrace();
                    response.getWriter().write("error:รูปแบบข้อมูลไม่ถูกต้อง");
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                    e.printStackTrace();
                    response.getWriter().write("error:เกิดข้อผิดพลาดในการบันทึกข้อมูล");
                } catch (Exception e) {
                    System.out.println("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                    response.getWriter().write("error:เกิดข้อผิดพลาดที่ไม่คาดคิด");
                }
            }
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
            response.getWriter().write("error:เกิดข้อผิดพลาดที่เซิร์ฟเวอร์");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("booking.jsp");
    }
}
