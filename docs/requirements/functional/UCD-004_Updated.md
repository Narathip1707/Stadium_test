# 📋 Use Case Description (UC-004) - Updated
**Booking Staff Operations — จัดการการจองสนาม**

| Field | Content |
|-------|---------|
| **Diagram ID:** | UC-004 |
| **Use Case Name:** | Booking Operations (การดำเนินการจองสนาม) |
| **Scope:** | ระบบจองสนามกีฬาออนไลน์ |
| **Level:** | User Goal |
| **Primary Actor:** | พนักงานฝ่ายจองสนาม (Booking Staff) |
| **Secondary Actors:** | ผู้ดูแลระบบ (System Admin), ผู้จัดการ (Managing Director) |
| **Support Actors:** | ระบบ Payment Gateway, ระบบ Logistic |
| **Pre-Condition:** | Actor เข้าสู่ระบบสำเร็จ และมีสิทธิ์ตามบทบาท |
| **Post-Condition:** | การจองถูกดำเนินการตามที่ร้องขอ และบันทึกลงระบบเรียบร้อย |

---

## 🎯 Main Success Scenario

### 1) Create Booking (สร้างการจอง)
| Step | Actor | Action | System Response |
|------|-------|--------|-----------------|
| 1.1 | Booking Staff | เลือก "Create Booking" | แสดงฟอร์มสร้างการจองใหม่ |
| 1.2 | Booking Staff | กรอกข้อมูลการจอง (สนาม, วันที่, เวลา) | ตรวจสอบความว่างและคำนวณราคา |
| 1.3 | Booking Staff | ยืนยันการจอง | บันทึกการจองและสร้าง order-id |

### 2) Search Bookings (ค้นหาการจอง)
| Step | Actor | Action | System Response |
|------|-------|--------|-----------------|
| 2.1 | Booking Staff/System Admin | เลือก "Search Bookings" | แสดงหน้าค้นหา พร้อมฟิลเตอร์ |
| 2.2 | Actor | ใส่เงื่อนไขการค้นหา | แสดงรายการการจองที่ตรงตามเงื่อนไข |

### 3) Approve Booking (อนุมัติการจอง)
| Step | Actor | Action | System Response |
|------|-------|--------|-----------------|
| 3.1 | Booking Staff/System Admin | เลือกการจองและคลิก "Approve" | แสดงข้อมูลการจองสำหรับตรวจสอบ |
| 3.2 | Actor | ยืนยันการอนุมัติ | อัพเดตสถานะเป็น "Approved" และแจ้งเตือนที่เกี่ยวข้อง |

### 4) Reject Booking (ปฏิเสธการจอง)
| Step | Actor | Action | System Response |
|------|-------|--------|-----------------|
| 4.1 | Booking Staff/System Admin | เลือกการจองและคลิก "Reject" | แสดงฟอร์มใส่เหตุผลการปฏิเสธ |
| 4.2 | Actor | ใส่เหตุผลและยืนยัน | อัพเดตสถานะเป็น "Rejected" และบันทึก log |

### 5) Manage Schedule (จัดการตารางการจอง)
| Step | Actor | Action | System Response |
|------|-------|--------|-----------------|
| 5.1 | Booking Staff | เลือก "Manage Schedule" | แสดงปฏิทินการจองรายสนาม |
| 5.2 | Booking Staff | แก้ไขตารางการจอง | อัพเดตตารางและตรวจสอบความขัดแย้ง |

### 6) Process Payment (ประมวลผลการชำระเงิน)
| Step | Actor | Action | System Response |
|------|-------|--------|-----------------|
| 6.1 | Payment Gateway | ส่งสถานะการชำระเงิน | ระบบอัพเดตสถานะการชำระในฐานข้อมูล |
| 6.2 | System | ตรวจสอบการชำระเงิน | ส่งการยืนยันกลับไปยัง Payment Gateway |

### 7) Track Delivery (ติดตามการส่งมอบ)
| Step | Actor | Action | System Response |
|------|-------|--------|-----------------|
| 7.1 | Logistic System | ส่ง tracking number | ระบบบันทึก tracking information |
| 7.2 | Booking Staff | ตรวจสอบสถานะการส่งมอบ | แสดงข้อมูลการติดตามจาก Logistic |

### 8) View Reports (ดูรายงาน)
| Step | Actor | Action | System Response |
|------|-------|--------|-----------------|
| 8.1 | Managing Director | เลือก "View Reports" | แสดงรายงานการจองและสถิติต่างๆ |
| 8.2 | Managing Director | เลือกประเภทรายงาน | แสดงรายงานตามที่เลือก (รายวัน/เดือน/ปี) |

---

## ⚠️ Exceptions (ข้อยกเว้น)

| Exception | Condition | System Response |
|-----------|-----------|-----------------|
| **สนามไม่ว่าง** | เมื่อสร้างการจองในช่วงที่มีการจองแล้ว | แสดงแจ้งเตือน "สนามไม่ว่าง กรุณาเลือกช่วงเวลาอื่น" |
| **ข้อมูลไม่ครบถ้วน** | กรอกข้อมูลไม่ครบในฟอร์ม | แสดง "กรุณากรอกข้อมูลให้ครบถ้วน" พร้อมไฮไลท์ช่องที่ขาด |
| **ระบบภายนอกขัดข้อง** | Payment Gateway/Logistic ไม่ตอบสนong | แสดงข้อความแจ้งเตือนและ retry อัตโนมัติ 3 ครั้ง |
| **สิทธิ์ไม่เพียงพอ** | Actor ไม่มีสิทธิ์ดำเนินการ | แสดง "คุณไม่มีสิทธิ์ในการดำเนินการนี้" |

---

## 📋 Special Requirements
- ตรวจสอบ concurrent booking ป้องกัน double-booking
- เวลาในการตอบสนองต้องไม่เกิน 3 วินาที  
- Log ทุกการดำเนินการสำหรับ audit trail
- รองรับการใช้งานพร้อมกันของ multiple actors

## 💻 Technology & Data Variation
- **Web Browser:** Chrome, Edge, Firefox
- **Database:** MySQL 8.0+
- **API Integration:** REST API สำหรับ Payment Gateway และ Logistic
- **Session Management:** Jakarta EE Session API

## 🔄 Frequency of Occurrence
- **การสร้างการจอง:** 200-300 ครั้ง/วัน (Peak ช่วงเย็น)
- **การค้นหา:** 500-800 ครั้ง/วัน  
- **การอนุมัติ/ปฏิเสธ:** 100-150 ครั้ง/วัน
- **การดูรายงาน:** 20-50 ครั้ง/วัน

## 🚨 Open Issues
- **Access Control:** กำหนดสิทธิ์การเข้าถึงแต่ละ use case อย่างละเอียด
- **Real-time Updates:** ปรับปรุงระบบให้อัพเดตแบบ real-time เมื่อมีการเปลี่ยนแปลง  
- **Performance Optimization:** เพิ่ม caching สำหรับการค้นหาที่ใช้บ่อย