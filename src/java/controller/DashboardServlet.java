package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONObject;
import db.DBConnection;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Connection conn = null;
        PreparedStatement psUsers = null, psBookings = null, psRevenue = null;
        ResultSet rsUsers = null, rsBookings = null, rsRevenue = null;
        JSONObject jsonResponse = new JSONObject();

        try {
            // ใช้ DBConnection แทนการเชื่อมต่อแบบเดิม
            conn = DBConnection.getConnection();

            // ✅ นับจำนวนสมาชิก
            String sqlUsers = "SELECT COUNT(*) AS total_users FROM users ";
            psUsers = conn.prepareStatement(sqlUsers);
            rsUsers = psUsers.executeQuery();
            if (rsUsers.next()) {
                jsonResponse.put("total_users", rsUsers.getInt("total_users"));
            }

            // ✅ นับจำนวนการจอง
            String sqlBookings = "SELECT COUNT(*) AS total_bookings FROM bookings WHERE payment_status = 'paid'";
            psBookings = conn.prepareStatement(sqlBookings);
            rsBookings = psBookings.executeQuery();
            if (rsBookings.next()) {
                jsonResponse.put("total_bookings", rsBookings.getInt("total_bookings"));
            }

            // ✅ คำนวณรายได้ทั้งหมด
            String sqlRevenue = "SELECT SUM(total_price) AS total_revenue FROM bookings WHERE payment_status = 'paid'";
            psRevenue = conn.prepareStatement(sqlRevenue);
            rsRevenue = psRevenue.executeQuery();
            if (rsRevenue.next()) {
                jsonResponse.put("total_revenue", rsRevenue.getDouble("total_revenue"));
            }
            jsonResponse.put("status", "success");

        } catch (Exception e) {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", e.getMessage());
        } finally {
            try {
                if (rsUsers != null) {
                    rsUsers.close();
                }
                if (rsBookings != null) {
                    rsBookings.close();
                }
                if (rsRevenue != null) {
                    rsRevenue.close();
                }
                if (psUsers != null) {
                    psUsers.close();
                }
                if (psBookings != null) {
                    psBookings.close();
                }
                if (psRevenue != null) {
                    psRevenue.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ignored) {
            }
        }

        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }
}
