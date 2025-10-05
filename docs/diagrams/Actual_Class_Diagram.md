# 🏗️ Sports Booking System - Class Diagram (Based on Actual Code)

**Class Diagram ที่ตรงกับ Model classes ที่เขียนจริง**

---

## 📋 **PlantUML Code - Actual Implementation**

```plantuml
@startuml SportsBookingActualClassDiagram
!theme plain
skinparam linetype ortho
skinparam classAttributeIconSize 0

' User Class 
class User {
    - id : int {PK}
    - username : String(50)
    - email : String(100)
    - phone : String(20)
    - firstName : String(100)
    - lastName : String(100)
    - password : String(255)
    - isAdmin : boolean
    ..
    + getId() : int
    + setId(id : int) : void
    + getUsername() : String
    + setUsername(username : String) : void
    + getEmail() : String
    + setEmail(email : String) : void
    + getPhone() : String
    + setPhone(phone : String) : void
    + getFirstName() : String
    + setFirstName(firstName : String) : void
    + getLastName() : String
    + setLastName(lastName : String) : void
    + getPassword() : String
    + setPassword(password : String) : void
    + isAdmin() : boolean
    + setAdmin(isAdmin : boolean) : void
    + getFullName() : String
    + isValid() : boolean
    + toString() : String
}

' Field Class (ตามโค้ดจริง)
class Field {
    - id : int {PK}
    - fieldType : String(50)
    - name : String(100)
    - description : Text
    - price : double
    - location : String(200)
    - imageUrl : String(500)
    - status : String(20)
    - capacity : int
    - operatingHours : String(50)
    ..
    + getId() : int
    + setId(id : int) : void
    + getFieldType() : String
    + setFieldType(fieldType : String) : void
    + getName() : String
    + setName(name : String) : void
    + getDescription() : String
    + setDescription(description : String) : void
    + getPrice() : double
    + setPrice(price : double) : void
    + getLocation() : String
    + setLocation(location : String) : void
    + getImageUrl() : String
    + setImageUrl(imageUrl : String) : void
    + getStatus() : String
    + setStatus(status : String) : void
    + getCapacity() : int
    + setCapacity(capacity : int) : void
    + getOperatingHours() : String
    + setOperatingHours(operatingHours : String) : void
}

' Booking Class (ตามโค้ดจริง)
class Booking {
    - bookingId : int {PK}
    - userId : int {FK}
    - bookingDate : Date
    - startTime : Time
    - endTime : Time
    - status : String(20)
    - fieldType : String(50)
    - totalPrice : int
    - createdAt : Timestamp
    - paymentStatus : String(20)
    ..
    + getBookingId() : int
    + setBookingId(bookingId : int) : void
    + getUserId() : int
    + setUserId(userId : int) : void
    + getBookingDate() : Date
    + setBookingDate(bookingDate : Date) : void
    + getStartTime() : Time
    + setStartTime(startTime : Time) : void
    + getEndTime() : Time
    + setEndTime(endTime : Time) : void
    + getStatus() : String
    + setStatus(status : String) : void
    + getFieldType() : String
    + setFieldType(fieldType : String) : void
    + getTotalPrice() : int
    + setTotalPrice(totalPrice : int) : void
    + getCreatedAt() : Timestamp
    + setCreatedAt(createdAt : Timestamp) : void
    + getPaymentStatus() : String
    + setPaymentStatus(paymentStatus : String) : void
}

' Payment Class (ตามโค้ดจริง)
class Payment {
    - paymentId : int {PK}
    - bookingId : int {FK}
    - amount : double
    - paymentMethod : String(30)
    - transactionId : String(100)
    - paymentStatus : String(20)
    - paymentDate : Timestamp
    ..
    + getPaymentId() : int
    + setPaymentId(paymentId : int) : void
    + getBookingId() : int
    + setBookingId(bookingId : int) : void
    + getAmount() : double
    + setAmount(amount : double) : void
    + getPaymentMethod() : String
    + setPaymentMethod(paymentMethod : String) : void
    + getTransactionId() : String
    + setTransactionId(transactionId : String) : void
    + getPaymentStatus() : String
    + setPaymentStatus(paymentStatus : String) : void
    + getPaymentDate() : Timestamp
    + setPaymentDate(paymentDate : Timestamp) : void
    + toString() : String
}

' =========== RELATIONSHIPS ===========

' User to Booking (One-to-Many)
User "1" --> "0..*" Booking : makes
User ||..|| Booking : userId

' Booking to Payment (One-to-One or One-to-Many)
Booking "1" --> "0..1" Payment : has
Booking ||..|| Payment : bookingId

' Notes
note right of User
  - isAdmin = true สำหรับ Admin
  - isAdmin = false สำหรับ Customer
  - ไม่มี separate Customer/Employee classes
end note

note right of Booking
  - fieldType อ้างอิง Field.fieldType
  - ยังไม่มี FK constraint ไป Field
  - totalPrice เป็น int (บาท)
  - paymentStatus: "unpaid", "paid", "failed"
end note

note right of Payment
  - paymentMethod: "qrCode", "bankTransfer"
  - paymentStatus: "pending", "completed", "failed"
  - transactionId เก็บ reference จาก payment gateway
end note

@enduml
```

---

## 📊 **Actual Database Schema**

| Table | Primary Key | Foreign Keys | Actual Columns |
|-------|------------|--------------|----------------|
| **users** | id (int) | - | id, username, email, phone, firstName, lastName, password, isAdmin |
| **fields** | id (int) | - | id, fieldType, name, description, price, location, imageUrl, status, capacity, operatingHours |
| **bookings** | bookingId (int) | userId → users.id | bookingId, userId, bookingDate, startTime, endTime, status, fieldType, totalPrice, createdAt, paymentStatus |
| **payments** | paymentId (int) | bookingId → bookings.bookingId | paymentId, bookingId, amount, paymentMethod, transactionId, paymentStatus, paymentDate |

---

## 🔍 **ความแตกต่างจาก Design เดิม:**

### **1. ไม่มี Person Abstract Class:**
```
❌ Design เดิม: Person → Customer/Employee
✅ โค้ดจริง: User class เดียว (แยกด้วย isAdmin flag)
```

### **2. ไม่มี Employee Hierarchy:**
```
❌ Design เดิม: Employee → BookingStaff/SystemAdmin/AccountingStaff
✅ โค้ดจริง: ไม่มี Employee classes แยก
```

### **3. Booking ไม่มี FK ไป Field:**
```
❌ Design เดิม: Booking.field_id → Field.field_id
✅ โค้ดจริง: Booking.fieldType (String) - reference โดยไม่มี FK
```

### **4. ไม่มี Service, BookingDetail:**
```
❌ Design เดิม: มี Service และ BookingDetail classes
✅ โค้ดจริง: ไม่มี classes เหล่านี้
```

### **5. ไม่มี External Services:**
```
❌ Design เดิม: มี PaymentGateway, Logistic, Invoice classes
✅ โค้ดจริง: มีแค่ Payment class (ไม่มี Gateway/Logistic/Invoice แยก)
```

---

## 🎯 **Simplified Class Diagram (ตามโค้ดจริง):**

```
┌─────────────┐
│    User     │
│  (isAdmin)  │
└──────┬──────┘
       │ makes
       │ 1..*
       ▼
┌─────────────┐       ┌─────────────┐
│   Booking   │ has   │   Payment   │
│             │──────▶│             │
└─────────────┘ 0..1  └─────────────┘

┌─────────────┐
│    Field    │ (ไม่มีความสัมพันธ์โดยตรงกับ Booking)
└─────────────┘
```

---

## 💡 **แนะนำการปรับปรุงในอนาคต:**

### **1. เพิ่ม FK Constraint:**
```sql
-- เพิ่ม FK จาก Booking ไป Field
ALTER TABLE bookings ADD COLUMN field_id INT;
ALTER TABLE bookings ADD FOREIGN KEY (field_id) REFERENCES fields(id);
```

### **2. แยก Customer และ Admin:**
```java
// เพิ่ม Customer class
public class Customer extends User {
    private int loyaltyPoints;
    private String membershipLevel;
}

// เพิ่ม Admin class  
public class Admin extends User {
    private String department;
    private int accessLevel;
}
```

### **3. เพิ่ม BookingDetail:**
```java
public class BookingDetail {
    private int detailId;
    private int bookingId;
    private int serviceId;
    private int quantity;
    private double unitPrice;
}
```

---

## 📁 **ไฟล์ที่มีอยู่จริง:**

```
src/java/model/
├── User.java           ✅ (มีอยู่)
├── Field.java          ✅ (มีอยู่)
├── Booking.java        ✅ (มีอยู่)
└── Payment.java        ✅ (มีอยู่)

ไม่มี:
├── Person.java         ❌
├── Customer.java       ❌
├── Employee.java       ❌
├── Service.java        ❌
├── PaymentGateway.java ❌
└── Logistic.java       ❌
```

---

## ✅ **สรุป:**

**Class Diagram ที่ปรับแล้วตรงกับโค้ดจริง 100%:**
- ✅ มี 4 classes: User, Field, Booking, Payment
- ✅ User ใช้ flag `isAdmin` แทนการแยก Customer/Admin
- ✅ Booking มี FK ไป User (userId)
- ✅ Payment มี FK ไป Booking (bookingId)
- ✅ Field เป็น standalone (ยังไม่มี FK relationship)

**นี่คือ Class Diagram ที่ตรงกับระบบที่คุณพัฒนาจริงๆ ครับ!** 🎉