package controller;

import dao.BookingDAO;
import dao.FieldDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Field;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "FieldServlet", urlPatterns = {"/FieldServlet"})
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024, // 5MB
        maxRequestSize = 5 * 1024 * 1024, // 5MB
        fileSizeThreshold = 1024 * 1024 // 1MB
)
public class FieldServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(FieldServlet.class);
    private static final String UPLOAD_DIR = "assets/images";
    // ปรับเป็น path จริงของโฟลเดอร์ในโปรเจกต์ (เปลี่ยนตาม path ในเครื่องคุณ)
    private static final String UPLOAD_PATH = "D:/Cos2204.2.67/SportsBooking/web/assets/images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            sendErrorResponse(response, "กรุณาเข้าสู่ระบบก่อน");
            return;
        }

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            sendErrorResponse(response, "คุณไม่มีสิทธิ์เข้าถึง");
            return;
        }

        FieldDAO fieldDAO = new FieldDAO();

        try {
            if ("getFields".equals(action)) {
                List<Field> fields = convertResultSetToList(fieldDAO.getAllFields());
                sendFieldsResponse(response, fields);
            } else if ("getFieldsForHome".equals(action)) {
                List<Field> fields = getFieldsForHome(fieldDAO);
                sendFieldsResponse(response, fields);
            } else if ("addField".equals(action)) {
                Field field = processFieldData(request, true);
                fieldDAO.addField(field);
                sendSuccessResponse(response, "เพิ่มสนามสำเร็จ");
            } else if ("updateField".equals(action)) {
                Field field = processFieldData(request, false);
                fieldDAO.updateField(field);
                sendSuccessResponse(response, "อัปเดตข้อมูลสนามสำเร็จ");
            } else if ("deleteField".equals(action)) {
                String idStr = request.getParameter("fieldId");
                if (idStr == null || idStr.trim().isEmpty()) {
                    sendErrorResponse(response, "กรุณาระบุ ID สนาม");
                    return;
                }

                int id = Integer.parseInt(idStr);
                BookingDAO bookingDAO = new BookingDAO();
                String fieldType = fieldDAO.getFieldTypeById(id);
                if (fieldType != null && !bookingDAO.getBookingsByFieldType(fieldType).isEmpty()) {
                    sendErrorResponse(response, "ไม่สามารถลบสนามที่มีการจอง");
                    return;
                }

                fieldDAO.deleteField(id);
                sendSuccessResponse(response, "ลบสนามสำเร็จ");
            }
        } catch (SQLException e) {
            sendErrorResponse(response, "เกิดข้อผิดพลาดในฐานข้อมูล: " + e.getMessage());
            logger.error("Database error in FieldServlet: {}", e.getMessage(), e);
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "ข้อมูลไม่ถูกต้อง: " + e.getMessage());
            logger.error("Invalid data format in FieldServlet: {}", e.getMessage(), e);
        } catch (IOException e) {
            sendErrorResponse(response, "เกิดข้อผิดพลาดในการอัพโหลดไฟล์: " + e.getMessage());
            logger.error("File upload error in FieldServlet: {}", e.getMessage(), e);
        } catch (ServletException e) {
            sendErrorResponse(response, "เกิดข้อผิดพลาดใน Servlet: " + e.getMessage());
            logger.error("Servlet error in FieldServlet: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    private Field processFieldData(HttpServletRequest request, boolean isAdd) throws IOException, ServletException, SQLException {
        String fieldIdStr = request.getParameter("fieldId");
        String fieldType = request.getParameter("fieldType");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String location = request.getParameter("location");
        String status = request.getParameter("status");
        String capacityStr = request.getParameter("capacity");
        String operatingHours = request.getParameter("operatingHours");

        if ((isAdd && (fieldType == null || name == null || description == null || priceStr == null || location == null
                || status == null || capacityStr == null || operatingHours == null))
                || (!isAdd && fieldIdStr == null)
                || fieldType.trim().isEmpty() || name.trim().isEmpty() || description.trim().isEmpty()
                || priceStr.trim().isEmpty() || location.trim().isEmpty() || status.trim().isEmpty()
                || capacityStr.trim().isEmpty() || operatingHours.trim().isEmpty()) {
            throw new IllegalArgumentException("ข้อมูลไม่ครบถ้วน");
        }

        double price = Double.parseDouble(priceStr);
        int capacity = Integer.parseInt(capacityStr);
        int id = isAdd ? 0 : Integer.parseInt(fieldIdStr);

        Part filePart = request.getPart("imageFile");
        String imageUrl = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String contentType = filePart.getContentType();

            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/webp")) {
                throw new IOException("รองรับเฉพาะไฟล์ .jpg, .png, หรือ .webp เท่านั้น");
            }

            String newFileName = "field_" + System.currentTimeMillis() + "_" + fileName;
            File uploadDir = new File(UPLOAD_PATH);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = UPLOAD_PATH + File.separator + newFileName;
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(filePath));
            }

            // ปรับ path ให้เป็น relative path ที่เข้าถึงได้
            String contextPath = request.getContextPath();
            imageUrl = contextPath + "/" + UPLOAD_DIR + "/" + newFileName;
        } else if (!isAdd) {
            FieldDAO fieldDAO = new FieldDAO();
            Field existingField = fieldDAO.getFieldById(id);
            imageUrl = existingField.getImageUrl();
        } else {
            throw new IOException("ต้องอัพโหลดรูปภาพสำหรับสนามใหม่");
        }

        Field field = new Field();
        if (!isAdd) {
            field.setId(id);
        }
        field.setFieldType(fieldType.trim().toUpperCase());
        field.setName(name);
        field.setDescription(description);
        field.setPrice(price);
        field.setLocation(location);
        field.setImageUrl(imageUrl);
        field.setStatus(status);
        field.setCapacity(capacity);
        field.setOperatingHours(operatingHours);

        return field;
    }

    private List<Field> getFieldsForHome(FieldDAO fieldDAO) throws SQLException {
        List<Field> fields = new ArrayList<>();
        String[] fieldTypes = {"A", "B", "C"};
        for (String fieldType : fieldTypes) {
            try (ResultSet rs = fieldDAO.getFieldByFieldType(fieldType)) {
                while (rs.next()) {
                    Field field = new Field();
                    field.setId(rs.getInt("id"));
                    field.setFieldType(rs.getString("field_type"));
                    field.setName(rs.getString("name"));
                    field.setDescription(rs.getString("description"));
                    field.setPrice(rs.getDouble("price"));
                    field.setLocation(rs.getString("location"));
                    field.setImageUrl(rs.getString("image_url"));
                    field.setStatus(rs.getString("status"));
                    field.setCapacity(rs.getInt("capacity"));
                    field.setOperatingHours(rs.getString("operating_hours"));
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    private List<Field> convertResultSetToList(ResultSet rs) throws SQLException {
        List<Field> fields = new ArrayList<>();
        while (rs.next()) {
            Field field = new Field();
            field.setId(rs.getInt("id"));
            field.setFieldType(rs.getString("field_type"));
            field.setName(rs.getString("name"));
            field.setDescription(rs.getString("description"));
            field.setPrice(rs.getDouble("price"));
            field.setLocation(rs.getString("location"));
            field.setImageUrl(rs.getString("image_url"));
            field.setStatus(rs.getString("status"));
            field.setCapacity(rs.getInt("capacity"));
            field.setOperatingHours(rs.getString("operating_hours"));
            fields.add(field);
        }
        return fields;
    }

    private void sendFieldsResponse(HttpServletResponse response, List<Field> fields) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Field field : fields) {
            JSONObject jsonField = new JSONObject();
            jsonField.put("id", field.getId());
            jsonField.put("fieldType", field.getFieldType());
            jsonField.put("name", field.getName());
            jsonField.put("description", field.getDescription());
            jsonField.put("price", field.getPrice());
            jsonField.put("location", field.getLocation());
            jsonField.put("imageUrl", field.getImageUrl());
            jsonField.put("status", field.getStatus());
            jsonField.put("capacity", field.getCapacity());
            jsonField.put("operatingHours", field.getOperatingHours());
            jsonArray.put(jsonField);
        }

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "success");
        jsonResponse.put("fields", jsonArray);
        response.getWriter().write(jsonResponse.toString());
    }

    private void sendSuccessResponse(HttpServletResponse response, String message) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "success");
        jsonResponse.put("message", message);
        response.getWriter().write(jsonResponse.toString());
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "error");
        jsonResponse.put("message", message);
        response.getWriter().write(jsonResponse.toString());
    }
}
