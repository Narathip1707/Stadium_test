class BookingManager {
    constructor() {
        this.prices = fieldPrices;
        this.initializeElements();
        this.initializeEventListeners();
        this.generateTimeSlots();
    }

    initializeElements() {
        // อัพเดตตัวแปรให้ตรงกับ HTML ใหม่
        this.fieldInputs = document.querySelectorAll('input[name="field"]');
        this.bookingDate = document.getElementById('bookingDate');
        this.timeRangesInput = document.getElementById('timeRanges');
        this.totalPriceElement = document.getElementById('totalPrice');
        this.timeSlotsContainer = document.querySelector('.time-slots-grid');
        this.selectedTimeRangesElement = document.getElementById('selectedTimeRanges');
        this.selectedTimeCountElement = document.getElementById('selectedTimeCount');
    }

    initializeEventListeners() {
        // ใช้ forEach เพื่อเพิ่ม event listener ให้กับทุกปุ่มสนาม
        this.fieldInputs.forEach(input => {
            input.addEventListener('change', () => this.resetTimeSelection());
        });

        this.bookingDate.addEventListener('change', () => {
            this.validateDate();
            this.generateTimeSlots();
        });

        document.getElementById('bookingForm').addEventListener('submit', (e) => this.handleSubmit(e));

        // Event delegation for time slots
        this.timeSlotsContainer.addEventListener('click', (e) => {
            if (e.target.classList.contains('time-slot-btn')) {
                this.handleTimeSelection(e);
            }
        });
    }

    generateTimeSlots() {
        const now = new Date();
        const selectedDate = new Date(this.bookingDate.value);
        const isToday = now.toDateString() === selectedDate.toDateString();

        const slots = [];
        for (let hour = 9; hour < 23; hour++) {
            const start = `${hour.toString().padStart(2, '0')}:00`;
            const end = `${(hour + 1).toString().padStart(2, '0')}:00`;

            let disabledClass = '';
            let disabledAttr = '';

            // เช็คว่าเป็นวันนี้ และเวลาที่สร้างมาก่อนเวลาปัจจุบันหรือไม่
            if (isToday && hour < now.getHours()) {
                disabledClass = 'disabled';
                disabledAttr = 'disabled';
            }

            slots.push(`
                <button type="button" 
                    class="btn btn-outline-primary time-slot-btn ${disabledClass}" 
                    data-slot="${start}-${end}"
                    data-start="${start}"
                    data-end="${end}">
            ${disabledAttr}
                    ${start}-${end}
                </button>
            `);
        }

        this.timeSlotsContainer.innerHTML = slots.join('');
        this.checkAllSlotsAvailability();
    }

    getSelectedField() {
        const selectedField = document.querySelector('input[name="field"]:checked');
        return selectedField ? selectedField.value : null;
    }

    checkAllSlotsAvailability() {
        const fieldType = this.getSelectedField();
        const bookingDate = this.bookingDate.value;

        if (!fieldType || !bookingDate) {
            console.log("Missing field or date selection");
            return;
        }

        const now = new Date();
        const selectedDate = new Date(bookingDate);
        const isToday = now.toDateString() === selectedDate.toDateString();

        document.querySelectorAll('.time-slot-btn').forEach(slot => {
            const slotHour = parseInt(slot.dataset.start.split(':')[0], 10);
            // ถ้าเป็นวันนี้และ slot นั้นเป็นเวลาที่ผ่านมาแล้ว ให้ปิดการใช้งาน
            if (isToday && slotHour < now.getHours()) {
                slot.classList.add('disabled');
                slot.disabled = true;
                slot.innerText = "หมดเวลา 🕒";
                return;
            }

            console.log("Checking availability for slot:", slot.dataset.slot);
            $.ajax({
                url: "BookingServlet",
                method: "POST",
                data: {
                    action: "check",
                    field: fieldType,
                    date: bookingDate,
                    start: slot.dataset.start,
                    end: slot.dataset.end
                },
                async: false,
                success: (response) => {
                    console.log(`Response for ${slot.dataset.slot}: ${response}`);
                    if (response === "available") {
                        slot.classList.remove('disabled');
                        slot.disabled = false;
                        slot.innerText = `${slot.dataset.start}-${slot.dataset.end}`; // จองได้
                    } else {
                        slot.classList.add('disabled');
                        slot.classList.remove('active');
                        slot.disabled = true;
                        slot.innerText = "จองแล้ว ❌"; // จองแล้ว
                    }
                },
                error: (xhr, status, error) => {
                    console.error("Error checking availability:", error);
                    slot.classList.add('disabled');
                    slot.disabled = true;
                    slot.innerText = "ข้อผิดพลาด ⚠️";
                }
            });
        });
    }

    handleTimeSelection(event) {
        const selectedSlot = event.target;
        console.log("Time slot clicked:", selectedSlot.dataset.slot);

        if (selectedSlot.classList.contains('disabled') || selectedSlot.disabled) {
            console.log("Slot is disabled");
            alert('ช่วงเวลานี้ถูกจองแล้ว กรุณาเลือกช่วงอื่น');
            return;
        }

        selectedSlot.classList.toggle('active');
        console.log("Slot active state:", selectedSlot.classList.contains('active'));

        this.updateSelectedTimeRanges();
        this.calculatePrice();
    }

    updateSelectedTimeRanges() {
        const selectedSlots = document.querySelectorAll('.time-slot-btn.active');
        const timeRanges = Array.from(selectedSlots)
                .map(slot => slot.dataset.slot)
                .sort()
                .join(', ');

        this.timeRangesInput.value = Array.from(selectedSlots)
                .map(slot => slot.dataset.slot)
                .sort()
                .join(';');

        this.selectedTimeRangesElement.innerText = timeRanges || '-';
        this.selectedTimeCountElement.innerText = `${selectedSlots.length} ช่วง`;
        console.log("Updated time ranges:", this.timeRangesInput.value);
    }

    resetTimeSelection() {
        console.log("Resetting time selection");
        this.timeRangesInput.value = '';
        document.querySelectorAll('.time-slot-btn').forEach(slot => {
            slot.classList.remove('active', 'disabled');
            slot.disabled = false;
        });
        this.selectedTimeRangesElement.innerText = '-';
        this.selectedTimeCountElement.innerText = '0 ช่วง';
        this.totalPriceElement.innerText = '฿0';
        this.generateTimeSlots();
    }

    calculatePrice() {
        const selectedField = this.getSelectedField();
        const selectedSlots = document.querySelectorAll('.time-slot-btn.active');

        if (!selectedField || selectedSlots.length === 0) {
            this.totalPriceElement.innerText = '฿0';
            return;
        }

        const pricePerHour = this.prices[selectedField];
        const totalHours = selectedSlots.length;
        const totalPrice = totalHours * pricePerHour;
        this.totalPriceElement.innerText = `฿${totalPrice.toLocaleString()}`;
        console.log("Calculated price:", totalPrice);
    }

    validateDate() {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const selected = new Date(this.bookingDate.value);
        selected.setHours(0, 0, 0, 0);

        if (selected < today) {
            alert('กรุณาเลือกวันที่ในอนาคต');
            this.bookingDate.value = '';
            return false;
        }
        return true;
    }

    handleSubmit(event) {
        event.preventDefault();
        console.log("Form submitted");

        const fieldType = this.getSelectedField();
        if (!fieldType) {
            Swal.fire({
                icon: 'warning',
                title: 'กรุณาเลือกสนาม'
            });
            return;
        }

        if (!this.timeRangesInput.value) {
            Swal.fire({
                icon: 'warning',
                title: 'กรุณาเลือกช่วงเวลาการจอง'
            });
            return;
        }

        if (!this.validateDate()) {
            return;
        }

        const ranges = this.timeRangesInput.value.split(';');
        let allAvailable = true;
        const bookingDate = this.bookingDate.value;

        // ตรวจสอบความพร้อมของทุกช่วงเวลาที่เลือกอีกครั้งก่อนส่ง
        ranges.forEach(range => {
            const [start, end] = range.split('-');
            $.ajax({
                url: "BookingServlet",
                method: "POST",
                data: {
                    action: "check",
                    field: fieldType,
                    date: bookingDate,
                    start: start,
                    end: end
                },
                async: false,
                success: (response) => {
                    if (response !== "available") {
                        allAvailable = false;
                    }
                },
                error: (xhr, status, error) => {
                    console.error("Error in final availability check:", error);
                    allAvailable = false;
                }
            });
        });

        if (!allAvailable) {
            Swal.fire({
                icon: 'warning',
                title: 'มีบางช่วงเวลาที่ถูกจองแล้ว กรุณาเลือกช่วงอื่น'
            });
            this.resetTimeSelection();
            return;
        }

        // ส่งคำขอจอง
        $.ajax({
            url: "BookingServlet",
            method: "POST",
            data: {
                action: "book",
                field: fieldType,
                date: this.bookingDate.value,
                timeRanges: this.timeRangesInput.value
            },
            success: (response) => {
                console.log("Booking response:", response);
                if (response.startsWith("success")) {
                    const parts = response.split(":");
                    const totalPrice = parts.length > 1 ? parts[1] : '0';
                    Swal.fire({
                        icon: 'success',
                        title: 'การจองสำเร็จ!',
                        showConfirmButton: true,
                        confirmButtonText: 'ตกลง'
                    }).then(() => {
                        // เพิ่มการเปลี่ยนหน้าไปยัง payment.jsp ที่นี่
                        window.location.href = 'payment.jsp?totalPrice=' + totalPrice;
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: 'การจองไม่สำเร็จ'
                    });
                }
            },
            error: (xhr, status, error) => {
                console.error("Error in booking submission:", {
                    status: status,
                    error: error,
                    response: xhr.responseText
                });
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถส่งคำขอได้: ' + (error ? error : 'ไม่ทราบสาเหตุ')
                });
            }
        });
    }
}

// Initialize booking manager when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    window.bookingManager = new BookingManager();

    // เพิ่ม animation สำหรับการเลือกสนาม
    const fieldCards = document.querySelectorAll('.field-card');
    fieldCards.forEach(card => {
        card.addEventListener('click', function () {
            // เพิ่ม effect เมื่อคลิกที่การ์ด
            this.style.transform = 'scale(0.98)';
            setTimeout(() => {
                this.style.transform = 'scale(1)';
            }, 100);
        });
    });
});