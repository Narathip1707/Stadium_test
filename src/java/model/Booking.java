/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author Narathip
 */
public class Booking {

    private int bookingId;
    private int userId;                    // เพิ่มเพื่อเชื่อมโยงกับ users
    private Date bookingDate;
    private Time startTime;                // เปลี่ยนจาก String เป็น Time
    private Time endTime;                  // เปลี่ยนจาก String เป็น Time
    private String status;
    private String fieldType;              // เปลี่ยนจาก fieldName
    private int totalPrice;                // เปลี่ยนจาก price และจาก double เป็น int
    private Timestamp createdAt;           // เพิ่มเพื่อเก็บ created_at
    private String paymentStatus;          // เพิ่มเพื่อเก็บ payment_status (ค่าเริ่มต้น: "unpaid")

    public Booking() {
    }

    public Booking(int bookingId, int userId, Date bookingDate, Time startTime, Time endTime,
            String status, String fieldType, int totalPrice, Timestamp createdAt, String paymentStatus) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.fieldType = fieldType;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.paymentStatus = paymentStatus;
    }

    // Getter และ Setter
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
