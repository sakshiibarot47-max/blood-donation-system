package com.blooddonor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchByCityServlet")
public class SearchByCityServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/blooddonor";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // your actual password

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String city = request.getParameter("city");
 

        out.println("<html><head><link rel='stylesheet' href='style.css'></head><body>");
        out.println("<div class='container' style='max-width:700px;'>");
        out.println("<h2>Donors in " + city + "</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            jakarta.servlet.http.HttpSession session = request.getSession(false);
            Integer loggedInDonorId = (session != null) ? (Integer) session.getAttribute("donorId") : null;

            String sql = "SELECT name, blood_group, phone FROM donors WHERE city LIKE ? AND id != ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + city + "%");
            ps.setInt(2, (loggedInDonorId != null) ? loggedInDonorId : -1);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            out.println("<table><tr><th>Name</th><th>Blood Group</th><th>Phone</th></tr>");

            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("blood_group") + "</td>");
                out.println("<td>" + rs.getString("phone") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            rs.close();
            ps.close();
            conn.close();

            if (!found) {
                response.sendRedirect("searchByCity.jsp?msg=notfound");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p class='error'>Error: " + e.getMessage() + "</p>");
        }

        out.println("<br><a href='searchByCity.jsp'>Search again</a>");
        out.println("</div></body></html>");
    }
}