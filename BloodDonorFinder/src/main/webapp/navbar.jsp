<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar">
    <a href="home.jsp" class="logo">🩸 Blood Donor Finder</a>
    <div class="nav-links">
        <a href="home.jsp">Home</a>

        <%
            if (session.getAttribute("donorName") != null) {
        %>
            <a href="dashboard.jsp">Dashboard</a>
            <a href="searchByCity.jsp">Search by City</a>
            <a href="searchByBloodGroup.jsp">Search by Blood Group</a>
            <a href="emergencyRequest.jsp" style="color:#ffe3ec; font-weight:bold;">🚨 Emergency</a>
            <a href="RequestHistoryServlet">History</a>
            <span class="nav-welcome">Hi, <%= session.getAttribute("donorName") %></span>
            <a href="LogoutServlet">Logout</a>
        <%
            } else {
        %>
            <a href="registerDonor.jsp">Register</a>
            <a href="login.jsp">Login</a>
        <%
            }
        %>
    </div>
</nav>