package com.blooddonor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RequestHistoryServlet")
public class RequestHistoryServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/blooddonor";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // your actual password

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><link rel='stylesheet' href='style.css'></head><body>");
        try {
            // Include navbar manually since this is a plain servlet, not a JSP
            request.getRequestDispatcher("navbar.jsp").include(request, response);
        } catch (Exception e) {
            // ignore if include fails, page still works
        }

        out.println("<div class='container' style='max-width:900px;'>");
        out.println("<h2>📋 Emergency Request History</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM emergency_requests ORDER BY request_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr><th>Patient</th><th>Blood Group</th><th>City</th><th>Contact</th><th>Hospital</th><th>Message</th><th>Date</th></tr>");

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("patient_name") + "</td>");
                out.println("<td>" + rs.getString("blood_group") + "</td>");
                out.println("<td>" + rs.getString("city") + "</td>");
                out.println("<td>" + rs.getString("contact_number") + "</td>");
                out.println("<td>" + (rs.getString("hospital_name") != null ? rs.getString("hospital_name") : "-") + "</td>");
                out.println("<td>" + (rs.getString("message") != null ? rs.getString("message") : "-") + "</td>");
                out.println("<td>" + rs.getTimestamp("request_date") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            if (!found) {
                out.println("<p style='text-align:center; color:#888;'>No emergency requests yet.</p>");
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p class='error'>Error: " + e.getMessage() + "</p>");
        }

        out.println("<br><a href='home.jsp'>Back to Home</a>");
        out.println("</div>");

        try {
            request.getRequestDispatcher("footer.jsp").include(request, response);
        } catch (Exception e) {
            // ignore
        }

        out.println("</body></html>");
    }
}