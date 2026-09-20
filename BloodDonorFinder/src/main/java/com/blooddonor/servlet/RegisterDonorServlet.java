package com.blooddonor.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterDonorServlet")
public class RegisterDonorServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/blooddonor";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // your actual password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String bloodGroup = request.getParameter("bloodGroup");
        String city = request.getParameter("city");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO donors (name, blood_group, city, phone, email, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, bloodGroup);
            ps.setString(3, city);
            ps.setString(4, phone);
            ps.setString(5, email);
            ps.setString(6, password);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("<html><head><link rel='stylesheet' type='text/css' href='style.css'></head><body>");
                request.getRequestDispatcher("navbar.jsp").include(request, response);
                out.println("<div class='page-wrap'><div class='container'>");
                out.println("<h2 style='color:#27ae60;'>✅ Registration Successful!</h2>");
                out.println("<p class='subtitle'>Your donor profile has been created.</p>");
                out.println("<a href='login.jsp' class='btn' style='display:block; text-align:center; text-decoration:none; box-sizing:border-box;'>Click here to Login</a>");
                out.println("</div></div>");
                request.getRequestDispatcher("footer.jsp").include(request, response);
                out.println("</body></html>");
            } else {
                out.println("<html><head><link rel='stylesheet' type='text/css' href='style.css'></head><body>");
                request.getRequestDispatcher("navbar.jsp").include(request, response);
                out.println("<div class='page-wrap'><div class='container'>");
                out.println("<p class='error'>Registration failed. Try again.</p>");
                out.println("</div></div>");
                request.getRequestDispatcher("footer.jsp").include(request, response);
                out.println("</body></html>");
            }

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}