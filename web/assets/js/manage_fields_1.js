/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function () {
    loadFields();

    // Preview ภาพเมื่อกรอก URL ใน modal เพิ่ม
    $("#imageUrl").on("input", function () {
        const url = $(this).val();
        if (url) {
            $("#previewImageAdd").attr("src", url).show();
        } else {
            $("#previewImageAdd").hide();
        }
    });

    // Preview ภาพเมื่อกรอก URL ใน modal แก้ไข
    $("#editImageUrl").on("input", function () {
        const url = $(this).val();
        if (url) {
            $("#previewImageEdit").attr("src", url).show();
        } else {
            $("#previewImageEdit").hide();
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
                                <td><img src="${field.imageUrl}" alt="${field.name}" class="img-fluid" style="max-width: 100px;"></td>
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
        const fieldData = {
            fieldType: $("#fieldType").val(),
            name: $("#name").val(),
            description: $("#description").val(),
            price: $("#price").val(),
            location: $("#location").val(),
            imageUrl: $("#imageUrl").val(),
            status: $("#status").val(),
            capacity: $("#capacity").val(),
            operatingHours: $("#operatingHours").val()
        };

        $.ajax({
            url: "FieldServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: $.param({action: "addField", ...fieldData}),
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    Swal.fire({
                        icon: 'success',
                        title: response.message
                    }).then(() => {
                        loadFields(); // โหลดข้อมูลสนามใหม่
                        bootstrap.Modal.getInstance(document.getElementById('addFieldModal')).hide();
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

    // จัดการแก้ไขสนาม
    $(document).on("click", ".edit-field-btn", function () {
        const fieldId = $(this).data("field-id");
        $.ajax({
            url: "FieldServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {action: "getFields", fieldId: fieldId}, // หรือใช้ getFieldById ถ้ามี
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    const field = response.fields.find(f => f.id == fieldId); // สมมติส่งข้อมูลสนามทั้งหมด
                    if (field) {
                        $("#editFieldId").val(field.id);
                        $("#editFieldType").val(field.fieldType);
                        $("#editName").val(field.name);
                        $("#editDescription").val(field.description);
                        $("#editPrice").val(field.price);
                        $("#editLocation").val(field.location);
                        $("#editImageUrl").val(field.imageUrl);
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

    // จัดการบันทึกข้อมูลสนาม
    $("#editFieldForm").submit(function (e) {
        e.preventDefault();
        const fieldData = {
            fieldId: $("#editFieldId").val(),
            fieldType: $("#editFieldType").val(),
            name: $("#editName").val(),
            description: $("#editDescription").val(),
            price: $("#editPrice").val(),
            location: $("#editLocation").val(),
            imageUrl: $("#editImageUrl").val(),
            status: $("#editStatus").val(),
            capacity: $("#editCapacity").val(),
            operatingHours: $("#editOperatingHours").val()
        };

        $.ajax({
            url: "FieldServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: $.param({action: "updateField", ...fieldData}),
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    Swal.fire({
                        icon: 'success',
                        title: response.message
                    }).then(() => {
                        loadFields(); // โหลดข้อมูลสนามใหม่
                        bootstrap.Modal.getInstance(document.getElementById('editFieldModal')).hide();
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
