# 🔍 UML Class Diagram Symbols Guide
**คู่มืออ้างอิงเครื่องหมายและสัญลักษณ์ในคลาสไดอะแกรม**

---

## 📋 **สารบัญ**
1. [Access Modifiers](#1-access-modifiers-การเข้าถึง)
2. [Data Types](#2-data-types-ประเภทข้อมูล)
3. [Key Constraints](#3-key-constraints-ข้อจำกัดคีย์)
4. [Relationship Lines](#4-relationship-lines-เส้นความสัมพันธ์)
5. [Multiplicity](#5-multiplicity-ความสัมพันธ์เชิงจำนวน)
6. [Inheritance Symbols](#6-inheritance-symbols-สัญลักษณ์การสืบทอด)
7. [Special Keywords](#7-special-keywords-คำสำคัญพิเศษ)
8. [Class Separators](#8-class-separators-เครื่องหมายแบ่งส่วน)

---

## **1. Access Modifiers (การเข้าถึง)**

| สัญลักษณ์ | ความหมาย | คำอธิบาย |
|----------|----------|----------|
| `+` (plus) | **public** | เข้าถึงได้จากทุกที่ |
| `-` (minus) | **private** | เข้าถึงได้จากคลาสเดียวกันเท่านั้น |
| `#` (hash) | **protected** | เข้าถึงได้จาก subclass |
| `~` (tilde) | **package** | เข้าถึงได้จากแพ็คเกจเดียวกัน |

### **ตัวอย่างจากโปรเจค Sports Booking:**
```java
class Person {
    - person_id : String      // private (ข้อมูลส่วนตัว)
    + getName() : String      // public (ใช้ได้ทุกที่)
    # validateInfo() : Boolean // protected (ใช้ใน subclass)
}
```

---

## **2. Data Types (ประเภทข้อมูล)**

| สัญลักษณ์ | ความหมาย | คำอธิบาย |
|----------|----------|----------|
| `Str(20)` | **String** | ข้อความความยาว 20 ตัวอักษร |
| `int` | **Integer** | จำนวนเต็ม |
| `real` | **Real number** | ทศนิยม |
| `boolean` | **Boolean** | true/false |
| `date` | **Date** | วันที่ |
| `time` | **Time** | เวลา |

### **ตัวอย่างจากโปรเจค Sports Booking:**
```java
class Booking {
    - booking_id : Str(15)        // รหัสจอง 15 ตัวอักษร
    - total_amount : real         // ยอดเงินทศนิยม
    - booking_date : date         // วันที่จอง
    - start_time : time           // เวลาเริ่ม
    - is_confirmed : boolean      // สถานะยืนยัน
}
```

---

## **3. Key Constraints (ข้อจำกัดคีย์)**

| สัญลักษณ์ | ความหมาย | คำอธิบาย |
|----------|----------|----------|
| `{PK}` | **Primary Key** | คีย์หลัก - ไม่ซ้ำกัน |
| `{FK}` | **Foreign Key** | คีย์นอก - อ้างอิงไปยังตารางอื่น |
| `{PK}{FK}` | **Primary & Foreign** | คีย์ที่เป็นทั้งหลักและนอก |

### **ตัวอย่างจากโปรเจค Sports Booking:**
```java
class Customer {
    - customer_id : Str(10) {PK}     // คีย์หลัก
    - person_id : Str(10) {FK}       // อ้างอิงไปยัง Person
}

class BookingDetail {
    - booking_id : Str(15) {PK}{FK}  // ทั้งคีย์หลักและคีย์นอก
    - detail_id : Str(20) {PK}       // คีย์หลักร่วม
}
```

---

## **4. Relationship Lines (เส้นความสัมพันธ์)**

| สัญลักษณ์ | ความหมาย | คำอธิบาย |
|----------|----------|----------|
| `──────` | **Association** | ความสัมพันธ์ทั่วไป |
| `- - - -` | **Dependency** | การพึ่งพา |
| `──────>` | **Directed Association** | ความสัมพันธ์มีทิศทาง |
| `◆──────` | **Composition** | ส่วนประกอบ - ไม่อยู่ได้โดยอิสระ |
| `◇──────` | **Aggregation** | การรวมกลุ่ม - อยู่ได้โดยอิสระ |

### **ตัวอย่างจากโปรเจค Sports Booking:**
```
Customer ──────── Booking           // Association
Customer ◆─────── Account           // Composition (Account ไม่อยู่ได้โดยไม่มี Customer)
Payment - - - -> PaymentGateway     // Dependency (Payment ใช้ PaymentGateway)
```

---

## **5. Multiplicity (ความสัมพันธ์เชิงจำนวน)**

| สัญลักษณ์ | ความหมาย | คำอธิบาย |
|----------|----------|----------|
| `1` | **หนึ่งต่อหนึ่ง** | หนึ่งเท่านั้น |
| `0..1` | **ศูนย์หรือหนึ่ง** | ไม่มีหรือมีหนึ่งอัน (optional) |
| `1..*` | **หนึ่งหรือมากกว่า** | อย่างน้อยหนึ่งอัน |
| `0..*` หรือ `*` | **ศูนย์หรือมากกว่า** | ไม่จำกัดจำนวน |
| `2..5` | **ช่วง** | ตั้งแต่ 2 ถึง 5 |

### **ตัวอย่างจากโปรเจค Sports Booking:**
```
Customer 1 ────── 0..* Booking
↑                      ↑
ลูกค้า 1 คน           ไม่จองหรือจองได้หลายครั้ง

Booking 1 ────── 1..* BookingDetail  
↑                     ↑
การจอง 1 ครั้ง         ต้องมีรายละเอียดอย่างน้อย 1 รายการ

Payment 1 ────── 1 PaymentGateway
↑                    ↑  
การชำระ 1 ครั้ง        ใช้ Gateway 1 ตัว
```

---

## **6. Inheritance Symbols (สัญลักษณ์การสืบทอด)**

| สัญลักษณ์ | ความหมาย | คำอธิบาย |
|----------|----------|----------|
| `◁──────` | **Inheritance** | การสืบทอด/การนำไปใช้ทั่วไป |
| `<|────` | **PlantUML syntax** | เหมือนกันแต่เป็น syntax ของ PlantUML |

### **ตัวอย่างจากโปรเจค Sports Booking:**
```
Person <|── Customer        // Customer สืบทอดจาก Person
Person <|── Employee        // Employee สืบทอดจาก Person
Employee <|── BookingStaff  // BookingStaff สืบทอดจาก Employee
```

---

## **7. Special Keywords (คำสำคัญพิเศษ)**

| คำสำคัญ | ความหมาย | การใช้งาน |
|---------|----------|-----------|
| `has` | **มี/ครอบครอง** | แสดงความเป็นเจ้าของ |
| `uses` | **ใช้** | แสดงการใช้งาน |
| `makes` | **สร้าง/ทำ** | แสดงการสร้างหรือผลิต |
| `manages` | **จัดการ** | แสดงการดูแลหรือควบคุม |
| `provides` | **จัดหา/ให้บริการ** | แสดงการให้บริการ |

### **ตัวอย่างจากโปรเจค Sports Booking:**
```
Customer 1 ──makes──→ 0..* Booking      // ลูกค้าทำการจอง
Booking 1 ──has────→ 1 Payment         // การจองมีการชำระเงิน  
Payment * ──uses───→ 1 PaymentGateway  // การชำระใช้ Payment Gateway
Field 1 ──provides→ * Service          // สนามให้บริการต่างๆ
BookingStaff * ──manages→ * Booking    // เจ้าหน้าที่จัดการการจอง
```

---

## **8. Class Separators (เครื่องหมายแบ่งส่วน)**

| สัญลักษณ์ | ความหมาย | การใช้งาน |
|----------|----------|-----------|
| `..` | **เส้นจุด** | แบ่งส่วน attributes กับ methods |
| `────────` | **เส้นทึบ** | เส้นแบ่งธรรมดา |

### **ตัวอย่างจากโปรเจค Sports Booking:**
```java
class Booking {
    // Attributes
    - booking_id : Str(15) {PK}    
    - customer_id : Str(10) {FK}   
    - total_amount : real          
    ..                             // ← เส้นแบ่ง
    // Methods
    + makeBooking() : void         
    + cancelBooking() : boolean    
    + calculateTotal() : real      
}
```

---

## **📚 เอกสารอ้างอิง**

### **PlantUML Syntax:**
```plantuml
@startuml
!theme plain
skinparam linetype ortho

class Person {
    - person_id : Str(10) {PK}
    - name : Str(100)
    - email : Str(50)
    ..
    + validateInfo() : boolean
    + sendNotification() : void
}

class Customer {
    + membership_level : Str(20)
    + loyalty_points : int
    ..
    + makeBooking() : void
    + viewHistory() : void
}

Person <|-- Customer
Customer 1 --|{ Booking : makes
@enduml
```

### **Quick Reference:**
- **Private:** `-` (ข้อมูลส่วนตัว)
- **Public:** `+` (เข้าถึงได้ทุกที่)
- **Primary Key:** `{PK}` (คีย์หลัก)
- **Foreign Key:** `{FK}` (คีย์นอก)
- **One to Many:** `1 --|{ *` 
- **Inheritance:** `<|--`
- **Composition:** `*--`
- **Association:** `--`

---

## **💡 Tips สำหรับการใช้งาน**

1. **ใช้ชื่อที่มีความหมาย** - ชื่อคลาสและ attribute ควรเข้าใจง่าย
2. **กำหนด data types ให้ชัดเจน** - ระบุขนาดของ String เช่น `Str(50)`
3. **ใส่ {PK} {FK} ทุกครั้ง** - ช่วยในการออกแบบฐานข้อมูล
4. **ใช้ multiplicity อย่างถูกต้อง** - ป้องกันความเข้าใจผิด
5. **จัดกลุ่มคลาสที่เกี่ยวข้อง** - ทำให้ไดอะแกรมอ่านง่าย

---

*📝 เอกสารนี้สร้างขึ้นเพื่อการทบทวนและอ้างอิงในการออกแบบ UML Class Diagram สำหรับโปรเจค Sports Booking System*