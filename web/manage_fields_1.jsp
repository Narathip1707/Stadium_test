<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true" %>

<%
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("index.jsp"); // ❌ ไม่ใช่ Admin ให้กลับหน้าแรก
        return;
    }
%>

<%@ include file="header.jsp" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-12">
            <div class="card shadow-lg" style="border-radius: 15px; background: linear-gradient(135deg, #ffffff, #f8f9fa);">
                <div class="card-header text-center bg-success text-white" style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                    <h3 class="card-title mb-0">
                        <i class="bi bi-house-gear me-2"></i>จัดการสนาม
                    </h3>
                </div>
                <div class="card-body p-4">
                    <div class="mb-3 text-end">
                        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addFieldModal">เพิ่มสนามใหม่</button>
                    </div>
                    <div id="fieldsTable" class="table-responsive">
                        <table class="table table-hover">
                            <thead class="table-success">
                                <tr>
                                    <th>ID</th>
                                    <th>ประเภทสนาม</th>
                                    <th>ชื่อ</th>
                                    <th>คำอธิบาย</th>
                                    <th>ราคา</th>
                                    <th>ที่ตั้ง</th>
                                    <th>รูปภาพ</th>
                                    <th>สถานะ</th>
                                    <th>ทีม(คน)</th>
                                    <th>เวลาทำการ</th>
                                    <th>จัดการ</th>
                                </tr>
                            </thead>
                            <tbody id="fieldsBody">
                                <!-- ข้อมูลจะถูกโหลดด้วย Ajax -->
                            </tbody>
                        </table>
                        <div id="loading" class="text-center">
                            <div class="spinner-border text-success" role="status">
                                <span class="visually-hidden">Loading...</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal สำหรับเพิ่มสนาม -->
    <div class="modal fade" id="addFieldModal" tabindex="-1" aria-labelledby="addFieldModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title" id="addFieldModalLabel">เพิ่มสนามใหม่</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="addFieldForm">
                        <div class="mb-3">
                            <label for="fieldType" class="form-label">ประเภทสนาม</label>
                            <input type="text" class="form-control" id="fieldType" name="fieldType" required>
                        </div>
                        <div class="mb-3">
                            <label for="name" class="form-label">ชื่อ</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">คำอธิบาย</label>
                            <textarea class="form-control" id="description" name="description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">ราคา</label>
                            <input type="number" step="0.01" class="form-control" id="price" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="location" class="form-label">ที่ตั้ง</label>
                            <input type="text" class="form-control" id="location" name="location" required>
                        </div>
                        <div class="mb-3">
                            <label for="imageUrl" class="form-label">URL รูปภาพ</label>
                            <input type="text" class="form-control" id="imageUrl" name="imageUrl" required>
                            <img id="previewImageAdd" src="" alt="Preview Image" class="img-fluid mt-2" style="max-width: 200px; display: none;">
                        </div>
                        <div class="mb-3">
                            <label for="status" class="form-label">สถานะ</label>
                            <select class="form-select" id="status" name="status" required>
                                <option value="">เลือกสถานะ</option>
                                <option value="active">ใช้งาน</option>
                                <option value="inactive">ไม่ใช้งาน</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="capacity" class="form-label">ทีม(คน)</label>
                            <input type="number" class="form-control" id="capacity" name="capacity" required>
                        </div>
                        <div class="mb-3">
                            <label for="operatingHours" class="form-label">เวลาทำการ</label>
                            <input type="text" class="form-control" id="operatingHours" name="operatingHours" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100">บันทึก</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal สำหรับแก้ไขสนาม -->
    <div class="modal fade" id="editFieldModal" tabindex="-1" aria-labelledby="editFieldModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title" id="editFieldModalLabel">แก้ไขข้อมูลสนาม</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editFieldForm">
                        <input type="hidden" id="editFieldId" name="fieldId">
                        <div class="mb-3">
                            <label for="editFieldType" class="form-label">ประเภทสนาม</label>
                            <input type="text" class="form-control" id="editFieldType" name="fieldType" required>
                        </div>
                        <div class="mb-3">
                            <label for="editName" class="form-label">ชื่อ</label>
                            <input type="text" class="form-control" id="editName" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">คำอธิบาย</label>
                            <textarea class="form-control" id="editDescription" name="description" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="editPrice" class="form-label">ราคา</label>
                            <input type="number" step="0.01" class="form-control" id="editPrice" name="price" required>
                        </div>
                        <div class="mb-3">
                            <label for="editLocation" class="form-label">ที่ตั้ง</label>
                            <input type="text" class="form-control" id="editLocation" name="location" required>
                        </div>
                        <div class="mb-3">
                            <label for="editImageUrl" class="form-label">URL รูปภาพ</label>
                            <input type="text" class="form-control" id="editImageUrl" name="imageUrl" required>
                            <img id="previewImageEdit" src="" alt="Preview Image" class="img-fluid mt-2" style="max-width: 200px; display: none;">
                        </div>
                        <div class="mb-3">
                            <label for="editStatus" class="form-label">สถานะ</label>
                            <select class="form-select" id="editStatus" name="status" required>
                                <option value="">เลือกสถานะ</option>
                                <option value="active">ใช้งาน</option>
                                <option value="inactive">ไม่ใช้งาน</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="editCapacity" class="form-label">ทีม(คน)</label>
                            <input type="number" class="form-control" id="editCapacity" name="capacity" required>
                        </div>
                        <div class="mb-3">
                            <label for="editOperatingHours" class="form-label">เวลาทำการ</label>
                            <input type="text" class="form-control" id="editOperatingHours" name="operatingHours" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100">บันทึก</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="assets/js/manage_fields.js"></script>
<%@ include file="footer.jsp" %>