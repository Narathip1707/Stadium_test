/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Booking;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Narathip
 */
public class BookingDAO {

    public int saveBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (user_id, booking_date, start_time, end_time, status, created_at, field_type, total_price, payment_status) "
                + "VALUES (?, ?, ?, ?, ?, NOW(), ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setDate(2, booking.getBookingDate());
            stmt.setTime(3, booking.getStartTime());
            stmt.setTime(4, booking.getEndTime());
            stmt.setString(5, booking.getStatus());
            stmt.setString(6, booking.getFieldType());
            stmt.setInt(7, booking.getTotalPrice());
            stmt.setString(8, booking.getPaymentStatus());
            stmt.executeUpdate();

            // ดึง booking_id ที่เพิ่งสร้าง
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // คืนค่า booking_id
                }
            }
        }
        return -1; // กรณีไม่สำเร็จ
    }

    public Booking getBookingById(int bookingId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setStartTime(rs.getTime("start_time"));
                booking.setEndTime(rs.getTime("end_time"));
                booking.setStatus(rs.getString("status"));
                booking.setFieldType(rs.getString("field_type"));
                booking.setTotalPrice(rs.getInt("total_price"));
                booking.setCreatedAt(rs.getTimestamp("created_at"));
                booking.setPaymentStatus(rs.getString("payment_status"));
                return booking;
            }
            return null;
        }
    }

    public Booking[] getBookingsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            // นับจำนวนผลลัพธ์เพื่อสร้าง array
            rs.last();
            int size = rs.getRow();
            if (size == 0) {
                return new Booking[0];
            }
            Booking[] bookings = new Booking[size];
            rs.beforeFirst();
            int index = 0;
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setBookingDate(rs.getDate("booking_date"));
                booking.setStartTime(rs.getTime("start_time"));
                booking.setEndTime(rs.getTime("end_time"));
                booking.setStatus(rs.getString("status"));
                booking.setFieldType(rs.getString("field_type"));
                booking.setTotalPrice(rs.getInt("total_price"));
                booking.setCreatedAt(rs.getTimestamp("created_at"));
                booking.setPaymentStatus(rs.getString("payment_status"));
                bookings[index++] = booking;
            }
            return bookings;
        }
    }

    public boolean checkAvailability(String fieldType, Date bookingDate, Time startTime, Time endTime) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings WHERE field_type = ? AND booking_date = ? "
                + "AND ((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?))";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fieldType);
            stmt.setDate(2, bookingDate);
            stmt.setTime(3, endTime);
            stmt.setTime(4, startTime);
            stmt.setTime(5, startTime);
            stmt.setTime(6, endTime);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Check Availability - fieldType: " + fieldType + ", date: " + bookingDate
                        + ", startTime: " + startTime + ", endTime: " + endTime + ", count: " + count);
                return count == 0; // ถ้าไม่มีข้อมูลในช่วงนี้ = ว่าง
            }
            System.out.println("No bookings found for check - fieldType: " + fieldType + ", date: " + bookingDate);
            return true; // ถ้าไม่มีข้อมูลเลย = ว่าง
        }
    }

    public void updatePaymentStatus(int bookingId, String paymentStatus) throws SQLException {
        String sql = "UPDATE bookings SET payment_status = ? WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentStatus);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        }
    }

    public void deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            stmt.executeUpdate();
        }
    }

    // ดึงรายการจองทั้งหมดของผู้ใช้
    public List<Booking> getBookingsByUser(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.user_id, b.booking_date, b.start_time, b.end_time, "
                + "f.name, b.total_price, b.payment_status "
                + "FROM bookings b "
                + "JOIN fields f ON b.field_type = f.field_type "
                + "WHERE b.user_id = ? "
                + "ORDER BY b.booking_date DESC, b.start_time DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setStartTime(rs.getTime("start_time"));
                    booking.setEndTime(rs.getTime("end_time"));
                    booking.setFieldType(rs.getString("name"));
                    booking.setTotalPrice(rs.getInt("total_price"));
                    booking.setPaymentStatus(rs.getString("payment_status"));
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }

    public boolean cancelBooking(int bookingId) throws SQLException {
        String sql = "UPDATE bookings SET payment_status = 'cancelled' "
                + "WHERE booking_id = ? AND payment_status = 'unpaid'";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // เพิ่มเมธอดต่อไปนี้ในคลาส BookingDAO ที่มีอยู่แล้ว
    public List<Booking> getUnpaidBookingsByUser(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.user_id, b.booking_date, b.start_time, b.end_time, "
                + "f.name, b.total_price, b.payment_status "
                + "FROM bookings b "
                + "JOIN fields f ON b.field_type = f.field_type "
                + // หรือ f.field_id ถ้าใช้ field_id
                "WHERE b.user_id = ? AND b.payment_status = 'unpaid' "
                + "ORDER BY b.booking_date, b.start_time";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setStartTime(rs.getTime("start_time"));
                    booking.setEndTime(rs.getTime("end_time"));
                    booking.setFieldType(rs.getString("name"));
                    booking.setTotalPrice(rs.getInt("total_price"));
                    booking.setPaymentStatus(rs.getString("payment_status"));
                    bookings.add(booking);
                }
            }
        }

        return bookings;
    }

    public List<Booking> getBookingsByFieldType(String fieldType) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE field_type = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fieldType.trim().toUpperCase());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setBookingDate(rs.getDate("booking_date"));
                    booking.setStartTime(rs.getTime("start_time"));
                    booking.setEndTime(rs.getTime("end_time"));
                    booking.setStatus(rs.getString("status"));
                    booking.setFieldType(rs.getString("field_type"));
                    booking.setTotalPrice(rs.getInt("total_price"));
                    booking.setPaymentStatus(rs.getString("payment_status"));
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }
}
