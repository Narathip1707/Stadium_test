/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function () {
    loadFields();

    // Preview ภาพเมื่อเลือกไฟล์ใน modal เพิ่ม
    $("#imageFile").on("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $("#previewImageAdd").attr("src", e.target.result).show();
            };
            reader.readAsDataURL(file);
        } else {
            $("#previewImageAdd").hide();
        }
    });

    // Preview ภาพเมื่อเลือกไฟล์ใน modal แก้ไข
    $("#editImageFile").on("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $("#previewImageEdit").attr("src", e.target.result).show();
            };
            reader.readAsDataURL(file);
        } else {
            $("#previewImageEdit").hide();
        }
    });

    // ฟังก์ชันตรวจสอบ fieldType ว่าเป็นภาษาอังกฤษเท่านั้น
    function isEnglishOnly(value) {
        return /^[A-Za-z]+$/.test(value);
    }

    // Real-time validation สำหรับ fieldType ใน modal เพิ่ม
    $("#fieldType").on("input", function () {
        const value = $(this).val().trim();
        if (value && !isEnglishOnly(value)) {
            $(this).addClass("is-invalid");
        } else {
            $(this).removeClass("is-invalid");
        }
    });

    // Real-time validation สำหรับ fieldType ใน modal แก้ไข
    $("#editFieldType").on("input", function () {
        const value = $(this).val().trim();
        if (value && !isEnglishOnly(value)) {
            $(this).addClass("is-invalid");
        } else {
            $(this).removeClass("is-invalid");
        }
    });
    
    function loadFields() {
        $("#loading").show();
        $("#fieldsBody").hide();

        $.ajax({
            url: "FieldServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {action: "getFields"},
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    const fields = response.fields;
                    let html = "";
                    fields.forEach(field => {
                        html += `
                            <tr>
                                <td>${field.id}</td>
                                <td>${field.fieldType}</td>
                                <td>${field.name}</td>
                                <td>${field.description}</td>
                                <td>฿${field.price.toLocaleString()}</td>
                                <td>${field.location}</td>
                                <td><img src="${field.imageUrl}" alt="${field.name}" class="img-fluid" style="max-width: 100px;" loading="lazy"></td>
                                <td>${field.status}</td>
                                <td>${field.capacity}</td>
                                <td>${field.operatingHours}</td>
                                <td>
                                    <button class="btn btn-primary btn-sm me-2 edit-field-btn" data-field-id="${field.id}" data-bs-toggle="modal" data-bs-target="#editFieldModal">แก้ไข</button>
                                    <button class="btn btn-danger btn-sm delete-field-btn" data-field-id="${field.id}">ลบ</button>
                                </td>
                            </tr>
                        `;
                    });
                    $("#fieldsBody").html(html);
                    $("#fieldsBody").show();
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: response.message
                    });
                }
            },
            error: function (xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถโหลดข้อมูลสนามได้: ' + error
                });
                console.error("Ajax Error:", status, error, xhr.responseText);
            },
            complete: function () {
                $("#loading").hide();
            }
        });
    }

    // จัดการเพิ่มสนาม
    $("#addFieldForm").submit(function (e) {
        e.preventDefault();
        const fieldType = $("#fieldType").val().trim();

        // ตรวจสอบ fieldType
        if (!isEnglishOnly(fieldType)) {
            Swal.fire({
                icon: 'error',
                title: 'ข้อผิดพลาด',
                text: 'ประเภทสนามต้องเป็นภาษาอังกฤษเท่านั้น (A-Z, a-z)'
            });
            return;
        }

        const formData = new FormData(this);
        // เพิ่ม action เข้าไปใน FormData
        formData.append("action", "addField");

        $.ajax({
            url: "FieldServlet",
            method: "POST",
            data: formData,
            processData: false, // ป้องกัน jQuery จากการ process data
            contentType: false, // ให้ browser กำหนด content-type (multipart/form-data)
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    Swal.fire({
                        icon: 'success',
                        title: response.message
                    }).then(() => {
                        loadFields(); // โหลดข้อมูลสนามใหม่
                        bootstrap.Modal.getInstance(document.getElementById('addFieldModal')).hide();
                        $("#addFieldForm")[0].reset(); // รีเซ็ตฟอร์ม
                        $("#previewImageAdd").hide(); // ซ่อน preview
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: response.message
                    });
                }
            },
            error: function (xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถเพิ่มสนามได้: ' + error
                });
                console.error("Ajax Error:", status, error, xhr.responseText);
            }
        });
    });

    // จัดการแก้ไขสนาม - โหลดข้อมูล
    $(document).on("click", ".edit-field-btn", function () {
        const fieldId = $(this).data("field-id");
        $.ajax({
            url: "FieldServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {action: "getFields", fieldId: fieldId},
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    const field = response.fields.find(f => f.id == fieldId);
                    if (field) {
                        $("#editFieldId").val(field.id);
                        $("#editFieldType").val(field.fieldType);
                        $("#editName").val(field.name);
                        $("#editDescription").val(field.description);
                        $("#editPrice").val(field.price);
                        $("#editLocation").val(field.location);
                        $("#editImageFile").val(''); // รีเซ็ตไฟล์
                        $("#editStatus").val(field.status);
                        $("#editCapacity").val(field.capacity);
                        $("#editOperatingHours").val(field.operatingHours);
                        $("#previewImageEdit").attr("src", field.imageUrl).show();
                    }
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: response.message
                    });
                }
            },
            error: function (xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถโหลดข้อมูลสนามได้: ' + error
                });
                console.error("Ajax Error:", status, error, xhr.responseText);
            }
        });
    });

    // จัดการบันทึกข้อมูลสนามที่แก้ไข
    $("#editFieldForm").submit(function (e) {
        e.preventDefault();
        const fieldType = $("#editFieldType").val().trim();

        // ตรวจสอบ fieldType
        if (!isEnglishOnly(fieldType)) {
            Swal.fire({
                icon: 'error',
                title: 'ข้อผิดพลาด',
                text: 'ประเภทสนามต้องเป็นภาษาอังกฤษเท่านั้น (A-Z, a-z)'
            });
            return;
        }
        const formData = new FormData(this);

        // เพิ่ม action เข้าไปใน FormData
        formData.append("action", "updateField");

        $.ajax({
            url: "FieldServlet",
            method: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    Swal.fire({
                        icon: 'success',
                        title: response.message
                    }).then(() => {
                        loadFields(); // โหลดข้อมูลสนามใหม่
                        bootstrap.Modal.getInstance(document.getElementById('editFieldModal')).hide();
                        $("#editFieldForm")[0].reset(); // รีเซ็ตฟอร์ม
                        $("#previewImageEdit").hide(); // ซ่อน preview
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'เกิดข้อผิดพลาด',
                        text: response.message
                    });
                }
            },
            error: function (xhr, status, error) {
                Swal.fire({
                    icon: 'error',
                    title: 'เกิดข้อผิดพลาด',
                    text: 'ไม่สามารถอัปเดตข้อมูลได้: ' + error
                });
                console.error("Ajax Error:", status, error, xhr.responseText);
            }
        });
    });

    // จัดการลบสนาม
    $(document).on("click", ".delete-field-btn", function () {
        const fieldId = $(this).data("field-id");
        Swal.fire({
            title: 'ยืนยันการลบ?',
            text: 'คุณแน่ใจหรือไม่ว่าต้องการลบสนามนี้?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'ใช่, ลบ!',
            cancelButtonText: 'ยกเลิก'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "FieldServlet",
                    method: "POST",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    data: {action: "deleteField", fieldId: fieldId},
                    dataType: "json",
                    success: function (response) {
                        if (response.status === "success") {
                            Swal.fire({
                                icon: 'success',
                                title: response.message
                            }).then(() => {
                                loadFields(); // โหลดข้อมูลสนามใหม่
                            });
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'เกิดข้อผิดพลาด',
                                text: response.message
                            });
                        }
                    },
                    error: function (xhr, status, error) {
                        Swal.fire({
                            icon: 'error',
                            title: 'เกิดข้อผิดพลาด',
                            text: 'ไม่สามารถลบสนามได้: ' + error
                        });
                        console.error("Ajax Error:", status, error, xhr.responseText);
                    }
                });
            }
        });
    });
});