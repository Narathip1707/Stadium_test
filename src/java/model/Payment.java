package model;

import java.sql.Timestamp;

public class Payment {

    private int paymentId;         // รหัสการชำระเงิน
    private int bookingId;         // รหัสการจอง
    private double amount;         // จำนวนเงิน
    private String paymentMethod;  // วิธีการชำระเงิน (qrCode หรือ bankTransfer)
    private String transactionId;  // รหัสธุรกรรม
    private String paymentStatus;  // สถานะการชำระเงิน (pending, completed, failed)
    private Timestamp paymentDate; // วันที่ชำระเงิน

    // Constructor ว่าง
    public Payment() {
    }

    // Constructor พร้อมฟิลด์ทั้งหมด
    public Payment(int paymentId, int bookingId, double amount, String paymentMethod,
            String transactionId, String paymentStatus, Timestamp paymentDate) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    // Getters และ Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    // toString() เพื่อใช้ในการ debugging
    @Override
    public String toString() {
        return "Payment{"
                + "paymentId=" + paymentId
                + ", bookingId=" + bookingId
                + ", amount=" + amount
                + ", paymentMethod='" + paymentMethod + '\''
                + ", transactionId='" + transactionId + '\''
                + ", paymentStatus='" + paymentStatus + '\''
                + ", paymentDate=" + paymentDate
                + '}';
    }
}
