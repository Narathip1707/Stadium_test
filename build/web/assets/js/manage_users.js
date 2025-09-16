$(document).ready(function () {
    loadUsers();

    function loadUsers() {
        $("#loading").show();
        $("#usersBody").hide();

        $.ajax({
            url: "AdminServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {action: "getUsers"},
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    const users = response.users;
                    let html = "";
                    users.forEach(user => {
                        html += `
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.username}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.email}</td>
                                <td>${user.phone}</td>
                                <td>${user.isAdmin ? 'Yes' : 'No'}</td>
                                <td>
                                    <button class="btn btn-primary btn-sm me-2 edit-user-btn" data-user-id="${user.id}" data-bs-toggle="modal" data-bs-target="#editUserModal">แก้ไข</button>
                                    <button class="btn btn-danger btn-sm delete-user-btn" data-user-id="${user.id}">ลบ</button>
                                </td>
                            </tr>
                        `;
                    });
                    $("#usersBody").html(html);
                    $("#usersBody").show();
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
                    text: 'ไม่สามารถโหลดรายชื่อผู้ใช้ได้: ' + error
                });
                console.error("Ajax Error:", status, error, xhr.responseText);
            },
            complete: function () {
                $("#loading").hide();
            }
        });
    }

    // จัดการแก้ไขผู้ใช้
    $(document).on("click", ".edit-user-btn", function () {
        const userId = $(this).data("user-id");
        $.ajax({
            url: "AdminServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {action: "getUsers", userId: userId}, // หรือใช้ getUserById ถ้ามี
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    const user = response.users.find(u => u.id == userId); // สมมติส่งข้อมูลผู้ใช้ทั้งหมด
                    if (user) {
                        $("#editUserId").val(user.id);
                        $("#editFirstName").val(user.firstName);
                        $("#editLastName").val(user.lastName);
                        $("#editEmail").val(user.email);
                        $("#editPhone").val(user.phone);
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
                    text: 'ไม่สามารถโหลดข้อมูลผู้ใช้ได้: ' + error
                });
                console.error("Ajax Error:", status, error, xhr.responseText);
            }
        });
    });

    // จัดการบันทึกข้อมูลผู้ใช้
    $("#editUserForm").submit(function (e) {
        e.preventDefault();
        const userId = $("#editUserId").val();
        const firstName = $("#editFirstName").val();
        const lastName = $("#editLastName").val();
        const email = $("#editEmail").val();
        const phone = $("#editPhone").val();

        $.ajax({
            url: "AdminServlet",
            method: "POST",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {
                action: "updateUser",
                userId: userId,
                firstName: firstName,
                lastName: lastName,
                email: email,
                phone: phone
            },
            dataType: "json",
            success: function (response) {
                if (response.status === "success") {
                    Swal.fire({
                        icon: 'success',
                        title: response.message
                    }).then(() => {
                        loadUsers(); // โหลดข้อมูลผู้ใช้ใหม่
                        bootstrap.Modal.getInstance(document.getElementById('editUserModal')).hide();
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

    // จัดการลบผู้ใช้
    $(document).on("click", ".delete-user-btn", function () {
        const userId = $(this).data("user-id");
        Swal.fire({
            title: 'ยืนยันการลบ?',
            text: 'คุณแน่ใจหรือไม่ว่าต้องการลบบัญชีนี้?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'ใช่, ลบ!',
            cancelButtonText: 'ยกเลิก'
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: "AdminServlet",
                    method: "POST",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    data: {action: "deleteUser", userId: userId},
                    dataType: "json",
                    success: function (response) {
                        if (response.status === "success") {
                            Swal.fire({
                                icon: 'success',
                                title: response.message
                            }).then(() => {
                                loadUsers(); // โหลดข้อมูลผู้ใช้ใหม่
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
                            text: 'ไม่สามารถลบผู้ใช้ได้: ' + error
                        });
                        console.error("Ajax Error:", status, error, xhr.responseText);
                    }
                });
            }
        });
    });
});