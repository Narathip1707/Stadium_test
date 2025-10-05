# 🏗️ Sports Booking System - Complete Class Diagram for Project Report

**Class Diagram ฉบับสมบูรณ์สำหรับ Project Report ครอบคลุม UCD ทั้งหมด**

---

## 📋 **PlantUML Code - Complete Class Diagram**

```plantuml
@startuml SportsBookingCompleteSystem
!theme plain
skinparam linetype ortho
skinparam classAttributeIconSize 0

' ===================================
' PERSON HIERARCHY - Base Classes
' ===================================

' Person - Abstract Base Class
abstract class Person {
    - personId : int {PK} <<auto_increment>>
    - firstName : String(100) <<not null>>
    - lastName : String(100) <<not null>>
    - email : String(100) {CK} <<unique>>
    - phone : String(20)
    - address : String(300)
    - createdAt : Timestamp
    - updatedAt : Timestamp
    ..
    + getPersonId() : int
    + getFirstName() : String
    + getLastName() : String
    + getEmail() : String
    + getPhone() : String
    + getAddress() : String
    + getFullName() : String
    + validateEmail() : boolean
    + validatePhone() : boolean
    {abstract} + getRole() : String
}

' Account - Login Credentials
class Account {
    - accountId : int {PK} <<auto_increment>>
    - personId : int {FK} <<references Person>>
    - username : String(50) {CK} <<unique>>
    - password : String(255) <<not null>>
    - salt : String(32)
    - lastLogin : Timestamp
    - isActive : boolean <<default: true>>
    - failedLoginAttempts : int <<default: 0>>
    - createdAt : Timestamp
    ..
    + authenticate(password : String) : boolean
    + changePassword(oldPass : String, newPass : String) : boolean
    + resetPassword() : String
    + lockAccount() : void
    + unlockAccount() : void
    + incrementFailedAttempts() : void
    + resetFailedAttempts() : void
}

' ===================================
' CUSTOMER HIERARCHY
' ===================================

class Customer {
    - customerId : int {PK} <<auto_increment>>
    - personId : int {FK} <<references Person>>
    - membershipLevel : String(20) <<default: "standard">>
    - loyaltyPoints : int <<default: 0>>
    - totalBookings : int <<default: 0>>
    - registrationDate : Date
    - isVIP : boolean <<default: false>>
    ..
    + getCustomerId() : int
    + getMembershipLevel() : String
    + getLoyaltyPoints() : int
    + makeBooking() : Booking
    + cancelBooking(bookingId : int) : boolean
    + viewBookingHistory() : List<Booking>
    + addLoyaltyPoints(points : int) : void
    + redeemPoints(points : int) : boolean
    + upgradeMembership() : boolean
    + getRole() : String
}

' ===================================
' EMPLOYEE HIERARCHY
' ===================================

abstract class Employee {
    - employeeId : int {PK} <<auto_increment>>
    - personId : int {FK} <<references Person>>
    - employeeCode : String(15) {CK} <<unique>>
    - department : String(50)
    - position : String(50)
    - salary : Decimal(10,2)
    - hireDate : Date
    - isActive : boolean <<default: true>>
    ..
    + getEmployeeId() : int
    + getEmployeeCode() : String
    + getDepartment() : String
    + getPosition() : String
    + clockIn() : boolean
    + clockOut() : boolean
    {abstract} + performDuty() : void
    + getRole() : String
}

' BookingStaff - UCD-004
class BookingStaff {
    - staffId : int {PK} <<auto_increment>>
    - employeeId : int {FK} <<references Employee>>
    - shiftSchedule : String(50)
    - bookingQuota : int
    - performanceRating : Decimal(3,2)
    - handledBookings : int <<default: 0>>
    ..
    + approveBooking(bookingId : int) : boolean
    + rejectBooking(bookingId : int, reason : String) : boolean
    + searchBookings(criteria : Map) : List<Booking>
    + manageSchedule() : void
    + updateBookingStatus(bookingId : int, status : String) : boolean
    + assignField(bookingId : int, fieldId : int) : boolean
    + performDuty() : void
}

' SystemAdmin - UCD-003
class SystemAdmin {
    - adminId : int {PK} <<auto_increment>>
    - employeeId : int {FK} <<references Employee>>
    - accessLevel : int <<default: 1>>
    - permissions : Text(500)
    - lastBackup : Timestamp
    - systemAccess : String(100)
    ..
    + manageUsers() : void
    + createUser(user : Person) : boolean
    + updateUser(userId : int, data : Map) : boolean
    + deleteUser(userId : int) : boolean
    + manageFields() : void
    + systemMaintenance() : boolean
    + viewAuditLogs() : List<AuditLog>
    + backupDatabase() : boolean
    + restoreDatabase(backupId : String) : boolean
    + performDuty() : void
}

' AccountingStaff - UCD-005
class AccountingStaff {
    - accountingId : int {PK} <<auto_increment>>
    - employeeId : int {FK} <<references Employee>>
    - financeAuthority : String(50)
    - certification : String(100)
    - approvalLimit : Decimal(12,2)
    ..
    + generateInvoice(paymentId : int) : Invoice
    + processRefund(paymentId : int, amount : Decimal) : boolean
    + reconcileTransactions() : Report
    + auditFinancialRecords() : Report
    + exportFinancialData(format : String) : File
    + generateTaxReport() : Report
    + verifyPayment(paymentId : int) : boolean
    + performDuty() : void
}

' ManagingDirector - UCD-006
class ManagingDirector {
    - directorId : int {PK} <<auto_increment>>
    - employeeId : int {FK} <<references Employee>>
    - authorityLevel : int <<default: 10>>
    - signatureLimit : Decimal(15,2)
    - department : String(50)
    ..
    + viewExecutiveDashboard() : Dashboard
    + viewReports(type : String, period : String) : Report
    + analyzeBusinessPerformance() : Analytics
    + approveDecisions(requestId : int) : boolean
    + setBusinessPolicies() : void
    + reviewFinancials() : Report
    + approveExpenses(amount : Decimal) : boolean
    + performDuty() : void
}

' MarketingStaff - UCD-007
class MarketingStaff {
    - marketingId : int {PK} <<auto_increment>>
    - employeeId : int {FK} <<references Employee>>
    - campaignAccess : String(100)
    - targetRegion : String(50)
    - activeCampaigns : int <<default: 0>>
    ..
    + createCampaign(campaign : Campaign) : boolean
    + analyzeTrends() : Analytics
    + generateSalesReport() : Report
    + exportMarketingData(format : String) : File
    + trackCampaignPerformance(campaignId : int) : Analytics
    + segmentCustomers() : List<CustomerSegment>
    + performDuty() : void
}

' ===================================
' FIELD & SERVICE
' ===================================

class Field {
    - fieldId : int {PK} <<auto_increment>>
    - fieldType : String(50) <<not null>>
    - name : String(100) {CK} <<unique>>
    - description : Text(2000)
    - hourlyRate : Decimal(8,2) <<not null>>
    - location : String(200)
    - imageUrl : String(500)
    - status : String(20) <<default: "available">>
    - capacity : int
    - operatingHours : String(50)
    - facilities : Text(1000)
    - maintenanceSchedule : Timestamp
    - createdAt : Timestamp
    - updatedAt : Timestamp
    ..
    + getFieldId() : int
    + getName() : String
    + getHourlyRate() : Decimal
    + isAvailable() : boolean
    + checkAvailability(date : Date, start : Time, end : Time) : boolean
    + calculateRate(hours : int, discount : Decimal) : Decimal
    + updateStatus(status : String) : void
    + scheduleMaintenance(date : Timestamp) : void
    + getFieldDetails() : FieldInfo
}

class Service {
    - serviceId : int {PK} <<auto_increment>>
    - fieldId : int {FK} <<references Field>>
    - serviceName : String(100)
    - serviceDescription : Text(500)
    - servicePrice : Decimal(8,2)
    - serviceDuration : int
    - availability : boolean <<default: true>>
    - serviceCode : String(15) {CK} <<unique>>
    ..
    + getServiceInfo() : ServiceInfo
    + updatePrice(newPrice : Decimal) : void
    + checkAvailability() : boolean
    + applyDiscount(discount : Decimal) : Decimal
    + activateService() : void
    + deactivateService() : void
}

' ===================================
' BOOKING SYSTEM
' ===================================

class Booking {
    - bookingId : int {PK} <<auto_increment>>
    - customerId : int {FK} <<references Customer>>
    - fieldId : int {FK} <<references Field>>
    - staffId : int {FK} <<references BookingStaff>>
    - bookingRef : String(20) {CK} <<unique>>
    - bookingDate : Date <<not null>>
    - startTime : Time <<not null>>
    - endTime : Time <<not null>>
    - status : String(20) <<default: "pending">>
    - totalAmount : Decimal(10,2) <<not null>>
    - paymentStatus : String(20) <<default: "unpaid">>
    - createdBy : int
    - createdAt : Timestamp
    - updatedAt : Timestamp
    ..
    + getBookingId() : int
    + getBookingRef() : String
    + calculateTotalAmount() : Decimal
    + calculateDuration() : int
    + validateBookingTime() : boolean
    + isOverlapping(other : Booking) : boolean
    + updateStatus(status : String) : void
    + confirm() : boolean
    + cancel() : boolean
    + isPending() : boolean
    + isConfirmed() : boolean
    + generateBookingRef() : String
}

class BookingDetail {
    - detailId : int {PK} <<auto_increment>>
    - bookingId : int {FK} <<references Booking>>
    - serviceId : int {FK} <<references Service>>
    - quantity : int
    - unitPrice : Decimal(8,2)
    - subtotal : Decimal(10,2)
    - discount : Decimal(5,2) <<default: 0>>
    ..
    + calculateSubtotal() : Decimal
    + validateQuantity() : boolean
    + updatePrice() : void
    + applyDiscount(discount : Decimal) : void
}

' ===================================
' PAYMENT SYSTEM - UCD-008
' ===================================

class PaymentGateway {
    - gatewayId : int {PK} <<auto_increment>>
    - gatewayName : String(50) <<not null>>
    - gatewayCode : String(10) {CK} <<unique>>
    - apiEndpoint : String(200)
    - apiKey : String(100)
    - secretKey : String(100)
    - isActive : boolean <<default: true>>
    - commissionRate : Decimal(5,4)
    - supportedMethods : String(200)
    - timeout : int <<default: 30>>
    ..
    + initializePayment(payment : Payment) : String
    + confirmPayment(transactionId : String) : PaymentResult
    + processRefund(transactionId : String, amount : Decimal) : RefundResult
    + validateTransaction(transactionId : String) : boolean
    + getTransactionStatus(transactionId : String) : String
    + sendWebhook(data : Map) : boolean
    + testConnection() : boolean
}

class Payment {
    - paymentId : int {PK} <<auto_increment>>
    - bookingId : int {FK} <<references Booking>>
    - gatewayId : int {FK} <<references PaymentGateway>>
    - transactionId : String(100) {CK} <<unique>>
    - paymentMethod : String(30) <<not null>>
    - amount : Decimal(10,2) <<not null>>
    - paymentStatus : String(20) <<default: "pending">>
    - paymentDate : Timestamp
    - gatewayResponse : Text(1000)
    - receiptNumber : String(25) {CK} <<unique>>
    - createdAt : Timestamp
    - updatedAt : Timestamp
    ..
    + processPayment() : boolean
    + verifyPayment() : boolean
    + refund(amount : Decimal) : boolean
    + updateStatus(status : String) : void
    + generateReceipt() : Receipt
    + generateTransactionId() : String
    + generateReceiptNumber() : String
    + isPending() : boolean
    + isCompleted() : boolean
    + isFailed() : boolean
}

class Invoice {
    - invoiceId : int {PK} <<auto_increment>>
    - paymentId : int {FK} <<references Payment>>
    - accountingId : int {FK} <<references AccountingStaff>>
    - invoiceNumber : String(20) {CK} <<unique>>
    - issueDate : Date
    - dueDate : Date
    - totalAmount : Decimal(10,2)
    - taxAmount : Decimal(8,2)
    - discountAmount : Decimal(8,2)
    - netAmount : Decimal(10,2)
    - invoiceStatus : String(20) <<default: "draft">>
    - pdfUrl : String(500)
    - notes : Text(500)
    ..
    + generateInvoiceNumber() : String
    + calculateTax(rate : Decimal) : Decimal
    + calculateNet() : Decimal
    + sendInvoice(email : String) : boolean
    + markAsPaid() : void
    + markAsOverdue() : void
    + generatePDF() : String
}

' ===================================
' LOGISTIC SYSTEM - UCD-009
' ===================================

class Logistic {
    - logisticId : int {PK} <<auto_increment>>
    - bookingId : int {FK} <<references Booking>>
    - trackingNumber : String(25) {CK} <<unique>>
    - deliveryAddress : String(300)
    - deliveryDate : Date
    - deliveryTime : Time
    - deliveryStatus : String(20) <<default: "pending">>
    - courierInfo : String(100)
    - courierContact : String(20)
    - estimatedDelivery : Timestamp
    - actualDelivery : Timestamp
    - deliveryNote : Text(500)
    - signedBy : String(100)
    ..
    + generateTrackingNumber() : String
    + updateStatus(status : String) : void
    + scheduleDelivery(date : Date, time : Time) : boolean
    + trackDelivery() : TrackingInfo
    + sendNotification() : void
    + confirmDelivery(signature : String) : boolean
    + reportIssue(issue : String) : void
}

' ===================================
' REPORTING & ANALYTICS - UCD-006, UCD-007
' ===================================

class Report {
    - reportId : int {PK} <<auto_increment>>
    - reportType : String(50) <<not null>>
    - reportName : String(100)
    - generatedBy : int {FK} <<references Employee>>
    - reportPeriod : String(50)
    - startDate : Date
    - endDate : Date
    - reportData : Text(5000)
    - reportFormat : String(20)
    - filePath : String(500)
    - createdAt : Timestamp
    ..
    + generateReport() : boolean
    + exportToExcel() : File
    + exportToPDF() : File
    + exportToCSV() : File
    + sendByEmail(email : String) : boolean
    + scheduleReport(frequency : String) : void
}

class Campaign {
    - campaignId : int {PK} <<auto_increment>>
    - marketingId : int {FK} <<references MarketingStaff>>
    - campaignName : String(100)
    - campaignType : String(50)
    - targetAudience : String(100)
    - budget : Decimal(10,2)
    - startDate : Date
    - endDate : Date
    - status : String(20) <<default: "draft">>
    - impressions : int <<default: 0>>
    - clicks : int <<default: 0>>
    - conversions : int <<default: 0>>
    ..
    + launch() : boolean
    + pause() : boolean
    + resume() : boolean
    + terminate() : boolean
    + trackPerformance() : Analytics
    + calculateROI() : Decimal
}

' ===================================
' AUDIT & LOGGING
' ===================================

class AuditLog {
    - logId : int {PK} <<auto_increment>>
    - userId : int {FK}
    - action : String(100)
    - tableName : String(50)
    - recordId : int
    - oldValue : Text(2000)
    - newValue : Text(2000)
    - ipAddress : String(45)
    - userAgent : String(200)
    - timestamp : Timestamp
    ..
    + logAction(action : String, data : Map) : void
    + getHistory(recordId : int) : List<AuditLog>
    + searchLogs(criteria : Map) : List<AuditLog>
}

' =========== RELATIONSHIPS ===========

' Person Hierarchy
Person <|-- Customer
Person <|-- Employee
Employee <|-- BookingStaff
Employee <|-- SystemAdmin
Employee <|-- AccountingStaff
Employee <|-- ManagingDirector
Employee <|-- MarketingStaff

' Account Relationships
Person "1" --o "1" Account : has >
Account }o-- Person : belongs to

' Customer Relationships
Customer }|--|| Person : extends
Customer "1" --o{ "0..*" Booking : makes >

' Employee Relationships
Employee }|--|| Person : extends
BookingStaff }|--|| Employee : is
SystemAdmin }|--|| Employee : is
AccountingStaff }|--|| Employee : is
ManagingDirector }|--|| Employee : is
MarketingStaff }|--|| Employee : is

' Booking Relationships
BookingStaff "1" --o{ "0..*" Booking : manages >
Field "1" --o{ "0..*" Booking : hosts >
Booking "1" --o{ "0..*" BookingDetail : contains >
Booking "1" --o| "0..1" Payment : requires >
Booking "1" --o| "0..1" Logistic : ships >

' Field & Service
Field "1" --o{ "0..*" Service : provides >
Service "1" --o{ "0..*" BookingDetail : included_in >

' Payment Relationships
PaymentGateway "1" --o{ "0..*" Payment : processes >
Payment "1" --o| "0..1" Invoice : generates >
AccountingStaff "1" --o{ "0..*" Invoice : creates >

' Report & Campaign
Employee "1" --o{ "0..*" Report : generates >
MarketingStaff "1" --o{ "0..*" Campaign : manages >

' Foreign Key Links
Customer ||..|| Person : personId
Employee ||..|| Person : personId
Account ||..|| Person : personId
BookingStaff ||..|| Employee : employeeId
SystemAdmin ||..|| Employee : employeeId
AccountingStaff ||..|| Employee : employeeId
ManagingDirector ||..|| Employee : employeeId
MarketingStaff ||..|| Employee : employeeId
Service ||..|| Field : fieldId
Booking ||..|| Customer : customerId
Booking ||..|| Field : fieldId
Booking ||..|| BookingStaff : staffId
BookingDetail ||..|| Booking : bookingId
BookingDetail ||..|| Service : serviceId
Payment ||..|| Booking : bookingId
Payment ||..|| PaymentGateway : gatewayId
Logistic ||..|| Booking : bookingId
Invoice ||..|| Payment : paymentId
Invoice ||..|| AccountingStaff : accountingId
Report ||..|| Employee : generatedBy
Campaign ||..|| MarketingStaff : marketingId

@enduml
```

---

## 📊 **Complete Database Schema Summary**

### **Core Tables**

| # | Table | Description | UCD Reference |
|---|-------|-------------|---------------|
| 1 | **Person** | Abstract base - ข้อมูลบุคคล | All UCDs |
| 2 | **Account** | บัญชีผู้ใช้งาน | UCD-002, UCD-003 |
| 3 | **Customer** | ลูกค้า | UCD-002, UCD-010 |
| 4 | **Employee** | พนักงาน (Abstract) | All UCDs |
| 5 | **BookingStaff** | เจ้าหน้าที่จอง | UCD-004 |
| 6 | **SystemAdmin** | ผู้ดูแลระบบ | UCD-003 |
| 7 | **AccountingStaff** | เจ้าหน้าที่บัญชี | UCD-005 |
| 8 | **ManagingDirector** | ผู้จัดการ | UCD-006 |
| 9 | **MarketingStaff** | เจ้าหน้าที่การตลาด | UCD-007 |

### **Business Tables**

| # | Table | Description | UCD Reference |
|---|-------|-------------|---------------|
| 10 | **Field** | สนาม | UCD-003, UCD-004 |
| 11 | **Service** | บริการเสริม | UCD-004 |
| 12 | **Booking** | การจอง | UCD-004, UCD-010 |
| 13 | **BookingDetail** | รายละเอียดการจอง | UCD-004 |

### **Payment Tables**

| # | Table | Description | UCD Reference |
|---|-------|-------------|---------------|
| 14 | **PaymentGateway** | ระบบชำระเงิน | UCD-008 |
| 15 | **Payment** | การชำระเงิน | UCD-004, UCD-008 |
| 16 | **Invoice** | ใบแจ้งหนี้ | UCD-005 |

### **External Integration Tables**

| # | Table | Description | UCD Reference |
|---|-------|-------------|---------------|
| 17 | **Logistic** | ระบบจัดส่ง | UCD-009 |
| 18 | **Report** | รายงาน | UCD-006, UCD-007 |
| 19 | **Campaign** | แคมเปญการตลาด | UCD-007 |
| 20 | **AuditLog** | บันทึกการใช้งาน | UCD-003 |

---

## 🔗 **Complete Relationships Matrix**

### **Person Hierarchy (Inheritance)**
```
                    Person (Abstract)
                       │
        ┌──────────────┴──────────────┐
        │                             │
    Customer                      Employee (Abstract)
                                      │
            ┌─────────────────────────┼─────────────────────────┐
            │                         │                         │
      BookingStaff              SystemAdmin               AccountingStaff
                                      │
                        ┌─────────────┴─────────────┐
                        │                           │
                ManagingDirector              MarketingStaff
```

### **Core Business Flow**
```
Customer → Booking → Payment → Invoice
    ↓         ↓         ↓
  Field  BookingDetail  PaymentGateway
             ↓
          Service

Booking → Logistic (optional)
```

### **Staff Operations**
```
BookingStaff → manages → Booking
SystemAdmin → manages → All Tables
AccountingStaff → creates → Invoice
ManagingDirector → views → Report
MarketingStaff → creates → Campaign
```

---

## 🔑 **Complete Keys Summary**

### **Primary Keys**
| Table | PK Column | Type | Description |
|-------|-----------|------|-------------|
| Person | personId | INT | Auto-increment |
| Account | accountId | INT | Auto-increment |
| Customer | customerId | INT | Auto-increment |
| Employee | employeeId | INT | Auto-increment |
| BookingStaff | staffId | INT | Auto-increment |
| SystemAdmin | adminId | INT | Auto-increment |
| AccountingStaff | accountingId | INT | Auto-increment |
| ManagingDirector | directorId | INT | Auto-increment |
| MarketingStaff | marketingId | INT | Auto-increment |
| Field | fieldId | INT | Auto-increment |
| Service | serviceId | INT | Auto-increment |
| Booking | bookingId | INT | Auto-increment |
| BookingDetail | detailId | INT | Auto-increment |
| PaymentGateway | gatewayId | INT | Auto-increment |
| Payment | paymentId | INT | Auto-increment |
| Invoice | invoiceId | INT | Auto-increment |
| Logistic | logisticId | INT | Auto-increment |
| Report | reportId | INT | Auto-increment |
| Campaign | campaignId | INT | Auto-increment |
| AuditLog | logId | INT | Auto-increment |

### **Foreign Keys**
| Child Table | FK Column | Parent Table | Parent PK | Cascade |
|-------------|-----------|--------------|-----------|---------|
| Account | personId | Person | personId | CASCADE |
| Customer | personId | Person | personId | CASCADE |
| Employee | personId | Person | personId | CASCADE |
| BookingStaff | employeeId | Employee | employeeId | CASCADE |
| SystemAdmin | employeeId | Employee | employeeId | CASCADE |
| AccountingStaff | employeeId | Employee | employeeId | CASCADE |
| ManagingDirector | employeeId | Employee | employeeId | CASCADE |
| MarketingStaff | employeeId | Employee | employeeId | CASCADE |
| Service | fieldId | Field | fieldId | CASCADE |
| Booking | customerId | Customer | customerId | RESTRICT |
| Booking | fieldId | Field | fieldId | RESTRICT |
| Booking | staffId | BookingStaff | staffId | SET NULL |
| BookingDetail | bookingId | Booking | bookingId | CASCADE |
| BookingDetail | serviceId | Service | serviceId | RESTRICT |
| Payment | bookingId | Booking | bookingId | CASCADE |
| Payment | gatewayId | PaymentGateway | gatewayId | RESTRICT |
| Invoice | paymentId | Payment | paymentId | CASCADE |
| Invoice | accountingId | AccountingStaff | accountingId | SET NULL |
| Logistic | bookingId | Booking | bookingId | CASCADE |
| Report | generatedBy | Employee | employeeId | SET NULL |
| Campaign | marketingId | MarketingStaff | marketingId | SET NULL |

### **Candidate Keys (Unique Constraints)**
| Table | Column | Example |
|-------|--------|---------|
| Person | email | "customer@example.com" |
| Account | username | "admin001" |
| Employee | employeeCode | "EMP2025001" |
| Field | name | "Barcelona Stadium 1" |
| Service | serviceCode | "SVC001" |
| Booking | bookingRef | "BK20250105001" |
| PaymentGateway | gatewayCode | "PROMPTPAY" |
| Payment | transactionId | "TXN20250105123456" |
| Payment | receiptNumber | "RCP20250105001" |
| Invoice | invoiceNumber | "INV2025010001" |
| Logistic | trackingNumber | "TRK20250105001" |

---

## 📐 **Complete ERD Diagram**

```
┌───────────────────┐
│      Person       │ (Abstract)
│   personId {PK}   │
└─────────┬─────────┘
          │
    ┌─────┴─────────────────────┐
    │                           │
┌───▼────────────┐      ┌───────▼────────┐
│   Customer     │      │   Employee     │ (Abstract)
│ customerId{PK} │      │ employeeId{PK} │
│ personId {FK}  │      │ personId {FK}  │
└───┬────────────┘      └───────┬────────┘
    │                           │
    │ 1                    ┌────┴────────────────────┐
    │                      │                         │
    │ makes          ┌─────▼──────┐          ┌──────▼────────┐
    │                │BookingStaff│          │  SystemAdmin  │
    │ 0..*           │staffId{PK} │          │  adminId{PK}  │
    │                └─────┬──────┘          └───────────────┘
    │                      │
    │                      │ manages              ┌─────────────────┐
    │                      │                      │ AccountingStaff │
    │                      │ 0..*                 │ accountingId{PK}│
┌───▼──────────────┐       │                      └────────┬────────┘
│     Booking      │◄──────┘                               │
│  bookingId {PK}  │                                       │ creates
│  customerId {FK} │                                       │
│  fieldId {FK}    │                                       │ 0..*
│  staffId {FK}    │                                       │
└───┬──────────────┘                                       │
    │ 1                                              ┌─────▼──────┐
    │                                                │   Invoice  │
    │ has                                            │invoiceId{PK│
    │                                                │paymentId{FK│
    │ 0..1                                           └────────────┘
┌───▼──────────────┐       ┌──────────────────┐
│     Payment      │───────│  PaymentGateway  │
│  paymentId {PK}  │ uses  │  gatewayId {PK}  │
│  bookingId {FK}  │       │                  │
│  gatewayId {FK}  │       └──────────────────┘
└──────────────────┘

┌──────────────────┐       ┌──────────────────┐
│      Field       │       │     Service      │
│   fieldId {PK}   │───────│  serviceId {PK}  │
│                  │provides│   fieldId {FK}   │
└─────────┬────────┘       └──────────────────┘
          │
          │ hosts
          │
          │ 0..*
      (to Booking)

┌──────────────────┐
│    Logistic      │
│ logisticId {PK}  │
│  bookingId {FK}  │
└──────────────────┘
      │
      │ ships (0..1)
      │
  (from Booking)

┌──────────────────┐       ┌──────────────────┐
│ManagingDirector  │       │ MarketingStaff   │
│ directorId {PK}  │       │ marketingId {PK} │
└─────────┬────────┘       └─────────┬────────┘
          │                          │
          │ generates                │ creates
          │                          │
          ▼                          ▼
    ┌──────────┐              ┌──────────┐
    │  Report  │              │ Campaign │
    └──────────┘              └──────────┘
```

---

## 🎯 **UCD Coverage Matrix**

| UCD | Title | Related Classes | Status |
|-----|-------|----------------|--------|
| **UCD-002** | Login System | Person, Account, Customer, Employee | ✅ Complete |
| **UCD-003** | System Administrator | SystemAdmin, AuditLog | ✅ Complete |
| **UCD-004** | Booking Staff Operations | BookingStaff, Booking, BookingDetail, Field, Service | ✅ Complete |
| **UCD-005** | Accounting Staff | AccountingStaff, Invoice, Payment | ✅ Complete |
| **UCD-006** | Managing Director | ManagingDirector, Report | ✅ Complete |
| **UCD-007** | Marketing System | MarketingStaff, Campaign, Report | ✅ Complete |
| **UCD-008** | Payment Gateway Integration | PaymentGateway, Payment | ✅ Complete |
| **UCD-009** | Logistic System Integration | Logistic, Booking | ✅ Complete |
| **UCD-010** | Customer Experience | Customer, Booking, Payment | ✅ Complete |

---

## 💡 **Implementation Phases**

### **Phase 1: Core System (Current)**
✅ Implemented:
- User (combines Customer + Admin via isAdmin flag)
- Field
- Booking (simplified without FK to Field)
- Payment (without Gateway)

### **Phase 2: User Hierarchy (Recommended Next)**
🔄 To Implement:
- Person (abstract base class)
- Customer (extends Person)
- Employee (abstract, extends Person)
- Account (separate login credentials)

### **Phase 3: Employee Specialization**
🔄 To Implement:
- BookingStaff
- SystemAdmin
- AccountingStaff
- ManagingDirector
- MarketingStaff

### **Phase 4: Business Enhancement**
🔄 To Implement:
- Service (additional field services)
- BookingDetail (many-to-many)
- Invoice (accounting)

### **Phase 5: External Integration**
🔄 To Implement:
- PaymentGateway (UCD-008)
- Logistic (UCD-009)

### **Phase 6: Analytics & Reporting**
🔄 To Implement:
- Report (UCD-006, UCD-007)
- Campaign (UCD-007)
- AuditLog (UCD-003)

---

## 🚀 **Quick Start - Draw Complete Diagram**

### **Step 1: Setup Draw.io**
1. Open https://app.diagrams.net/
2. Create new "Blank Diagram"
3. Enable UML shape library

### **Step 2: Layout Strategy**

**Recommended Layout (Top to Bottom):**
```
Row 1: Person (center, abstract)
Row 2: Customer (left) | Employee (right, abstract)
Row 3: BookingStaff, SystemAdmin, AccountingStaff
Row 4: ManagingDirector, MarketingStaff
Row 5: Account (left) | Field (right)
Row 6: Booking (center)
Row 7: BookingDetail, Service
Row 8: Payment, PaymentGateway
Row 9: Invoice, Logistic
Row 10: Report, Campaign, AuditLog
```

### **Step 3: Draw Order**
1. **Person hierarchy first** (inheritance arrows)
2. **Account connections**
3. **Field and Services**
4. **Booking system**
5. **Payment system**
6. **External systems** (Logistic)
7. **Reporting** (Report, Campaign)
8. **FK links last** (dashed lines)

### **Step 4: Color Coding (Optional)**
- 🔵 **Blue:** Core Person hierarchy
- 🟢 **Green:** Business entities (Field, Booking, Service)
- 🟡 **Yellow:** Payment system
- 🟠 **Orange:** External integrations
- 🟣 **Purple:** Reporting & Analytics
- ⚪ **White:** Support tables (AuditLog)

---

## ⏱️ **Estimated Time**

| Task | Classes | Time | Difficulty |
|------|---------|------|------------|
| Person hierarchy | 10 classes | 2 hours | ⭐⭐⭐ |
| Business entities | 5 classes | 1 hour | ⭐⭐ |
| Payment system | 3 classes | 45 min | ⭐⭐ |
| External systems | 4 classes | 1 hour | ⭐⭐ |
| Relationships | All | 1.5 hours | ⭐⭐⭐ |
| FK links & details | All | 1 hour | ⭐⭐ |
| Layout & polish | All | 1 hour | ⭐ |
| **Total** | **20 classes** | **8-9 hours** | ⭐⭐⭐ |

---

## ✅ **Checklist for Complete Diagram**

### **Classes**
- [ ] Person (abstract)
- [ ] Account
- [ ] Customer
- [ ] Employee (abstract)
- [ ] BookingStaff
- [ ] SystemAdmin
- [ ] AccountingStaff
- [ ] ManagingDirector
- [ ] MarketingStaff
- [ ] Field
- [ ] Service
- [ ] Booking
- [ ] BookingDetail
- [ ] PaymentGateway
- [ ] Payment
- [ ] Invoice
- [ ] Logistic
- [ ] Report
- [ ] Campaign
- [ ] AuditLog

### **Relationships**
- [ ] Inheritance arrows (Person → Customer/Employee)
- [ ] Associations with multiplicity
- [ ] FK links (dashed arrows)
- [ ] Relationship labels

### **Details**
- [ ] All attributes with types & lengths
- [ ] PK, FK, CK markers
- [ ] Access modifiers
- [ ] Constraints (not null, unique, default)
- [ ] All methods with parameters & return types

---

## 📝 **Notes for Project Report**

### **System Architecture Highlights:**
1. **Inheritance Hierarchy:** Person-based design แยก Customer และ Employee specializations
2. **Role-Based Access:** แต่ละประเภท Employee มี responsibilities ตาม UCD
3. **External Integration:** PaymentGateway และ Logistic เป็น separate entities
4. **Audit Trail:** AuditLog สำหรับ tracking การเปลี่ยนแปลง
5. **Reporting:** Report และ Campaign สำหรับ analytics

### **Design Patterns:**
- ✅ **Inheritance:** Person → Customer/Employee hierarchy
- ✅ **Composition:** Booking contains BookingDetail
- ✅ **Strategy:** PaymentGateway (multiple payment methods)
- ✅ **Observer:** Notification system (implied)
- ✅ **Factory:** Report generation

### **Database Normalization:**
- ✅ **1NF:** Atomic values
- ✅ **2NF:** No partial dependencies
- ✅ **3NF:** No transitive dependencies
- ✅ **BCNF:** All determinants are candidate keys

**Class Diagram นี้ครอบคลุม UCD ทั้งหมดแล้ว พร้อมสำหรับ Project Report! 🎉**
