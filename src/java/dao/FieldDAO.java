package dao;

import db.DBConnection;
import model.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldDAO {

    public ResultSet getFieldByFieldType(String fieldType) throws SQLException {
        String sql = "SELECT * FROM fields WHERE field_type = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, fieldType.trim().toUpperCase());
        return stmt.executeQuery();
    }

    public ResultSet getAllFields() throws SQLException {
        String sql = "SELECT * FROM fields";
        Connection conn = DBConnection.getConnection();
        return conn.createStatement().executeQuery(sql);
    }

    public void updateFieldStatus(String fieldType, String status) throws SQLException {
        String sql = "UPDATE fields SET status = ? WHERE field_type = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, fieldType.trim().toUpperCase());
            stmt.executeUpdate();
        }
    }

    public void addField(Field field) throws SQLException {
        String sql = "INSERT INTO fields (field_type, name, description, price, location, image_url, status, capacity, operating_hours) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, field.getFieldType().trim().toUpperCase());
            stmt.setString(2, field.getName());
            stmt.setString(3, field.getDescription());
            stmt.setDouble(4, field.getPrice());
            stmt.setString(5, field.getLocation());
            stmt.setString(6, field.getImageUrl());
            stmt.setString(7, field.getStatus());
            stmt.setInt(8, field.getCapacity());
            stmt.setString(9, field.getOperatingHours());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    field.setId(rs.getInt(1));
                }
            }
        }
    }

    public void updateField(Field field) throws SQLException {
        String sql = "UPDATE fields SET field_type = ?, name = ?, description = ?, price = ?, location = ?, image_url = ?, status = ?, capacity = ?, operating_hours = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, field.getFieldType().trim().toUpperCase());
            stmt.setString(2, field.getName());
            stmt.setString(3, field.getDescription());
            stmt.setDouble(4, field.getPrice());
            stmt.setString(5, field.getLocation());
            stmt.setString(6, field.getImageUrl());
            stmt.setString(7, field.getStatus());
            stmt.setInt(8, field.getCapacity());
            stmt.setString(9, field.getOperatingHours());
            stmt.setInt(10, field.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteField(int id) throws SQLException {
        String sql = "DELETE FROM fields WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public String getFieldTypeById(int id) throws SQLException {
        String sql = "SELECT field_type FROM fields WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("field_type");
                }
            }
        }
        return null;
    }

    // เพิ่ม method นี้เพื่อใช้ใน FieldServlet (โหมดแก้ไข)
    public Field getFieldById(int id) throws SQLException {
        String sql = "SELECT * FROM fields WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
                    return field;
                }
            }
        }
        return null;
    }
    // ใน FieldDAO.java เพิ่มเมธอด

    public List<Map<String, Object>> getActiveFieldsList() {
        String sql = "SELECT * FROM fields WHERE status = 'active'";
        List<Map<String, Object>> fieldList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> field = new HashMap<>();
                field.put("field_type", rs.getString("field_type"));
                field.put("name", rs.getString("name"));
                field.put("price", rs.getDouble("price"));
                field.put("image_url", rs.getString("image_url"));
                field.put("capacity", rs.getInt("capacity"));
                fieldList.add(field);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fieldList;
    }

    public ResultSet getActiveFields() {
        String sql = "SELECT * FROM fields WHERE status = 'active'";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
