package dao;

import model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.DBConnection;

public class PaymentDAO {
    
    private Connection getConnection() throws  SQLException{
        return DBConnection.getConnection();
    }
    
    // บันทึกข้อมูลการชำระเงิน
    public boolean savePayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO payments (booking_id, amount, payment_method, transaction_id, payment_status, payment_date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payment.getBookingId());
            pstmt.setDouble(2, payment.getAmount());
            pstmt.setString(3, payment.getPaymentMethod());
            pstmt.setString(4, payment.getTransactionId());
            pstmt.setString(5, payment.getPaymentStatus());
            pstmt.setTimestamp(6, payment.getPaymentDate());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // อัปเดตสถานะการชำระเงินในตาราง bookings
    public boolean updateBookingPaymentStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE bookings SET payment_status = ? WHERE booking_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // สร้าง transaction ID (ตัวอย่างง่ายๆ)
    public String generateTransactionId() {
        return "TXN" + System.currentTimeMillis(); // สามารถปรับให้ซับซ้อนกว่านี้ได้
    }

    // ดึงรายการการจองที่ยังไม่ได้ชำระเงินตาม userId (ถ้าต้องการใช้ในอนาคต)
    public List<Integer> getPendingBookingsByUserId(int userId) throws SQLException {
        String sql = "SELECT booking_id FROM bookings WHERE user_id = ? AND payment_status = 'unpaid'";
        List<Integer> bookingIds = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookingIds.add(rs.getInt("booking_id"));
                }
            }
        }
        return bookingIds;
    }

    // คำนวณยอดรวมของการจองที่ยังไม่ได้ชำระ (ถ้าต้องการใช้ในอนาคต)
    public double getTotalAmountForBookings(List<Integer> bookingIds) throws SQLException {
        if (bookingIds.isEmpty()) {
            return 0.0;
        }
        String sql = "SELECT SUM(total_price) as total FROM bookings WHERE booking_id IN ("
                + String.join(",", bookingIds.stream().map(String::valueOf).toArray(String[]::new)) + ")";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }
}
