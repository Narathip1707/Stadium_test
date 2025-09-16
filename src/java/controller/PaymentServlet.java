package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Payment;
import dao.PaymentDAO;
import dao.BookingDAO; // เพิ่ม import สำหรับ BookingDAO
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import model.Booking;

@WebServlet(name = "PaymentServlet", urlPatterns = {"/PaymentServlet"})
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8"); // ระบุ JSON ชัดเจน

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"กรุณาเข้าสู่ระบบก่อน\"}");
            return;
        }

        if ("processPayment".equals(action)) {
            String bookingIdsStr = request.getParameter("bookingIds");
            String paymentMethod = request.getParameter("paymentMethod");
            String amountStr = request.getParameter("amount");

            System.out.println("Booking IDs: " + bookingIdsStr); // Log เพื่อ debug
            System.out.println("Payment Method: " + paymentMethod);
            System.out.println("Amount: " + amountStr);

            if (bookingIdsStr == null || paymentMethod == null || amountStr == null
                    || bookingIdsStr.trim().isEmpty() || paymentMethod.trim().isEmpty() || amountStr.trim().isEmpty()) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"ข้อมูลไม่ครบถ้วน\"}");
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                String[] bookingIdsArray = bookingIdsStr.split(",");
                PaymentDAO paymentDAO = new PaymentDAO();
                String transactionId = paymentDAO.generateTransactionId();

                for (String bookingIdStr : bookingIdsArray) {
                    int bookingId = Integer.parseInt(bookingIdStr);
                    Payment payment = new Payment();
                    payment.setBookingId(bookingId);
                    payment.setAmount(amount / bookingIdsArray.length);
                    payment.setPaymentMethod(paymentMethod);
                    payment.setTransactionId(transactionId);
                    payment.setPaymentStatus("completed");
                    payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));

                    paymentDAO.savePayment(payment);
                    paymentDAO.updateBookingPaymentStatus(bookingId, "paid");
                }

                response.getWriter().write("{\"status\":\"success\",\"transactionId\":\"" + transactionId + "\"}");
                String jsonResponse = "{\"status\":\"success\",\"message\":\"ชำระเงินสำเร็จ\"}";
                return;
            } catch (NumberFormatException e) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"รูปแบบจำนวนเงินไม่ถูกต้อง\"}");
                return;
            } catch (SQLException e) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"เกิดข้อผิดพลาดในฐานข้อมูล: " + e.getMessage() + "\"}");
                return;
            } catch (Exception e) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"เกิดข้อผิดพลาด: " + e.getMessage() + "\"}");
                return;
            }
        } else if ("cancelBooking".equals(action)) {
            // ยกเลิกรายการเดี่ยว
            String bookingIdStr = request.getParameter("bookingId");
            if (bookingIdStr == null || bookingIdStr.trim().isEmpty()) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"กรุณาระบุรหัสการจอง\"}");
                return;
            }

            try {
                int bookingId = Integer.parseInt(bookingIdStr);
                int userId = (Integer) session.getAttribute("userId");
                BookingDAO bookingDAO = new BookingDAO(); // ใช้ BookingDAO ที่มีอยู่

                // ตรวจสอบว่า booking นี้เป็นของ user หรือไม่
                Booking booking = bookingDAO.getBookingById(bookingId);
                if (booking == null) {
                    response.getWriter().write("{\"status\":\"error\",\"message\":\"ไม่พบข้อมูลการจอง\"}");
                    return;
                }

                if (booking.getUserId() != userId) {
                    response.getWriter().write("{\"status\":\"error\",\"message\":\"การจองนี้ไม่ใช่ของคุณ\"}");
                    return;
                }

                if ("unpaid".equals(booking.getPaymentStatus())) {
                    boolean cancelled = bookingDAO.cancelBooking(bookingId); // ใช้ method เดิมจาก BookingDAO
                    if (cancelled) {
                        response.getWriter().write("{\"status\":\"success\",\"message\":\"ยกเลิกการจองสำเร็จ\"}");
                    } else {
                        response.getWriter().write("{\"status\":\"error\",\"message\":\"ไม่สามารถยกเลิกได้\"}");
                    }
                } else {
                    response.getWriter().write("{\"status\":\"error\",\"message\":\"ไม่สามารถยกเลิกได้ เนื่องจากชำระเงินแล้ว\"}");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"รหัสการจองไม่ถูกต้อง: " + e.getMessage() + "\"}");
            } catch (SQLException e) {
                response.getWriter().write("{\"status\":\"error\",\"message\":\"เกิดข้อผิดพลาดในฐานข้อมูล: " + e.getMessage() + "\"}");
            }
        } else {
            response.getWriter().write("{\"status\":\"error\",\"message\":\"คำสั่งไม่ถูกต้อง\"}");
        }
    }
}
