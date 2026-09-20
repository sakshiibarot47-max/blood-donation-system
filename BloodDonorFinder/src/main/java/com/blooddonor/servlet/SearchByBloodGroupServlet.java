package com.blooddonor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchByBloodGroupServlet")
public class SearchByBloodGroupServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/blooddonor";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // your actual password

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String bloodGroup = request.getParameter("bloodGroup");

        // Save last searched blood group in a cookie (valid for 30 days)
        Cookie cookie = new Cookie("lastBloodGroup", bloodGroup);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setPath("/BloodDonorFinder");
        response.addCookie(cookie);

        out.println("<html><head><link rel='stylesheet' href='style.css'></head><body>");
        out.println("<div class='container' style='max-width:700px;'>");
        out.println("<h2>Donors with Blood Group " + bloodGroup + "</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            jakarta.servlet.http.HttpSession session = request.getSession(false);
            Integer loggedInDonorId = (session != null) ? (Integer) session.getAttribute("donorId") : null;

            String sql = "SELECT name, city, phone FROM donors WHERE blood_group = ? AND id != ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, bloodGroup);
            ps.setInt(2, (loggedInDonorId != null) ? loggedInDonorId : -1);

            ResultSet rs = ps.executeQuery();

            boolean found = false;
            out.println("<table><tr><th>Name</th><th>City</th><th>Phone</th></tr>");

            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("city") + "</td>");
                out.println("<td>" + rs.getString("phone") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            rs.close();
            ps.close();
            conn.close();

            if (!found) {
                response.sendRedirect("searchByBloodGroup.jsp?msg=notfound");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p class='error'>Error: " + e.getMessage() + "</p>");
        }

        out.println("<br><a href='searchByBloodGroup.jsp'>Search again</a>");
        out.println("</div></body></html>");
    }
}