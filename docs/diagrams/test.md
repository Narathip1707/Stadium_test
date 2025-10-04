@startuml
left to right direction
skinparam packageStyle rectangle

actor Customer
actor "Booking Staff" as Staff  
actor "System Administrator" as Admin
actor "Managing Director" as Director
actor "Accounting Staff" as Accounting

rectangle "Booking Operations System" {
    (Booking Operations)
    
    ' Customer specific operations
    (Make Booking)
    (Cancel Booking)
    (View My Bookings)
    
    ' Booking Staff specific operations
    (Approve Booking)
    (Reject Booking)
    (Manage Booking Schedule)
    (Search All Bookings)
    
    ' Admin specific operations
    (System Maintenance)
    (View All Bookings)
    
    ' Director specific operations
    (View Booking Reports)
    (Monitor Booking Performance)
    
    ' Accounting specific operations
    (Process Payment)
    (Generate Invoice)
    (Verify Payment Status)
}

' Customer connections
Customer --> (Booking Operations)
(Booking Operations) ..> (Make Booking) : <<include>>
(Booking Operations) ..> (Cancel Booking) : <<include>>
(Booking Operations) ..> (View My Bookings) : <<include>>

' Booking Staff connections
Staff --> (Booking Operations)
(Booking Operations) ..> (Approve Booking) : <<include>>
(Booking Operations) ..> (Reject Booking) : <<include>>
(Booking Operations) ..> (Manage Booking Schedule) : <<include>>
(Booking Operations) ..> (Search All Bookings) : <<include>>

' System Admin connections
Admin --> (Booking Operations)
(Booking Operations) ..> (System Maintenance) : <<include>>
(Booking Operations) ..> (View All Bookings) : <<include>>

' Managing Director connections
Director --> (Booking Operations)
(Booking Operations) ..> (View Booking Reports) : <<include>>
(Booking Operations) ..> (Monitor Booking Performance) : <<include>>

' Accounting Staff connections
Accounting --> (Booking Operations)
(Booking Operations) ..> (Process Payment) : <<include>>
(Booking Operations) ..> (Generate Invoice) : <<include>>
(Booking Operations) ..> (Verify Payment Status) : <<include>>

@enduml
``` 📊 **Use Case Relationships (Multi-Actor Access to Booking Operations):**

| Actor | Primary Use Case | Relationship | Secondary Use Case | Description |
|-------|-----------------|--------------|-------------------|-------------|
| Customer | Booking Operations | «include» | Make Booking | ลูกค้าสามารถทำการจองสนาม |
| Customer | Booking Operations | «include» | Cancel Booking | ลูกค้าสามารถยกเลิกการจอง |
| Customer | Booking Operations | «include» | View My Bookings | ลูกค้าดูประวัติการจองของตนเอง |
| Booking Staff | Booking Operations | «include» | Approve Booking | เจ้าหน้าที่อนุมัติการจอง |
| Booking Staff | Booking Operations | «include» | Reject Booking | เจ้าหน้าที่ปฏิเสธการจอง |
| Booking Staff | Booking Operations | «include» | Manage Booking Schedule | จัดการตารางการจอง |
| Booking Staff | Booking Operations | «include» | Search All Bookings | ค้นหาการจองทั้งหมด |
| System Admin | Booking Operations | «include» | System Maintenance | ดูแลรักษาระบบจอง |
| System Admin | Booking Operations | «include» | View All Bookings | ดูการจองทั้งหมดในระบบ |
| Managing Director | Booking Operations | «include» | View Booking Reports | ดูรายงานการจอง |
| Managing Director | Booking Operations | «include» | Monitor Booking Performance | ติดตามประสิทธิภาพการจอง |
| Accounting Staff | Booking Operations | «include» | Process Payment | ประมวลผลการชำระเงิน |
| Accounting Staff | Booking Operations | «include» | Generate Invoice | สร้างใบแจ้งหนี้ |
| Accounting Staff | Booking Operations | «include» | Verify Payment Status | ตรวจสอบสถานะการชำระเงิน | นี่คือการออกแบบ UCD-004 ใหม่ที่ครอบคลุมการเข้าถึง Booking Operations จากหลาย actors ตามที่คุณต้องการ แต่ละ actor มีสิทธิ์และหน้าที่เฉพาะตัวในการจัดการการจองครับ!