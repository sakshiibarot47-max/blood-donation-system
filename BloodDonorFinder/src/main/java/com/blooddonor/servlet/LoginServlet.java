package com.blooddonor.servlet;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/blooddonor";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // your actual password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT * FROM donors WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Create session and store donor info
                HttpSession session = request.getSession();
                session.setAttribute("donorId", rs.getInt("id"));
                session.setAttribute("donorName", rs.getString("name"));
                session.setAttribute("donorEmail", rs.getString("email"));

                rs.close();
                ps.close();
                conn.close();

                response.sendRedirect("dashboard.jsp");
                return;

            } else {
                rs.close();
                ps.close();
                conn.close();
                response.sendRedirect("login.jsp?msg=invalid");
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?msg=error");
        }
    }
}