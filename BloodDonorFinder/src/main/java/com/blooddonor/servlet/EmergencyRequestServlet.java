package com.blooddonor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EmergencyRequestServlet")
public class EmergencyRequestServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/blooddonor";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // your actual password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String patientName = request.getParameter("patientName");
        String bloodGroup = request.getParameter("bloodGroup");
        String city = request.getParameter("city");
        String contactNumber = request.getParameter("contactNumber");
        String hospitalName = request.getParameter("hospitalName");
        String message = request.getParameter("message");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 1. Save the emergency request
            String insertSql = "INSERT INTO emergency_requests (patient_name, blood_group, city, contact_number, hospital_name, message) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPs = conn.prepareStatement(insertSql);
            insertPs.setString(1, patientName);
            insertPs.setString(2, bloodGroup);
            insertPs.setString(3, city);
            insertPs.setString(4, contactNumber);
            insertPs.setString(5, hospitalName);
            insertPs.setString(6, message);
            insertPs.executeUpdate();
            insertPs.close();

            // 2. Find matching donors (same blood group + city) to show immediately
            jakarta.servlet.http.HttpSession session = request.getSession(false);
            Integer loggedInDonorId = (session != null) ? (Integer) session.getAttribute("donorId") : null;

            String matchSql = "SELECT name, phone FROM donors WHERE blood_group = ? AND city LIKE ? AND id != ?";
            PreparedStatement matchPs = conn.prepareStatement(matchSql);
            matchPs.setString(1, bloodGroup);
            matchPs.setString(2, "%" + city + "%");
            matchPs.setInt(3, (loggedInDonorId != null) ? loggedInDonorId : -1);
            ResultSet rs = matchPs.executeQuery();

            out.println("<html><head><link rel='stylesheet' type='text/css' href='style.css'></head><body>");
            request.getRequestDispatcher("navbar.jsp").include(request, response);
            out.println("<div class='page-wrap'><div class='container wide'>");
            out.println("<h2 style='color:#27ae60;'>✅ Emergency request submitted!</h2>");
            out.println("<p class='subtitle'>Matching donors nearby:</p>");
            out.println("<table><tr><th>Name</th><th>Phone</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr><td>" + rs.getString("name") + "</td><td>" + rs.getString("phone") + "</td></tr>");
            }
            out.println("</table>");

            if (!found) {
                out.println("<div style='text-align:center; padding:20px 0;'>");
                out.println("<p class='error' style='display:inline-block;'>No matching donors found right now.</p>");
                out.println("<p style='color:#666; margin-top:10px;'>Your request has been saved, and we'll keep looking. Please also try calling nearby blood banks for faster help.</p>");
                out.println("</div>");
            }

            rs.close();
            matchPs.close();
            conn.close();

            out.println("<br><a href='home.jsp'>Back to Home</a>");
            out.println("</div></div>");
            request.getRequestDispatcher("footer.jsp").include(request, response);
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p class='error'>Error: " + e.getMessage() + "</p>");
        }
    }
}