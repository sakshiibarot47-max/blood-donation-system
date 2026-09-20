<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Blood Donor Finder</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <%
        boolean loggedIn = (session.getAttribute("donorName") != null);
    %>

    <div class="hero">
        <div class="hero-drop"></div>
        <h1>Donate Blood, <span>Save Real Lives.</span></h1>
        <p>Blood is the most precious gift anyone can give another person — the gift of life. Find donors near you or request emergency help in seconds.</p>
        <div class="btn-group">
        <% if (!loggedIn) { %>
            <a href="registerDonor.jsp" class="hero-btn primary">Become a Donor</a>
            <a href="login.jsp" class="hero-btn outline">Login</a>
        <% } else { %>
            <a href="dashboard.jsp" class="hero-btn primary">Go to Dashboard</a>
            <a href="emergencyRequest.jsp" class="hero-btn outline">Emergency Request</a>
        <% } %>
        </div>
    </div>

    <div class="features">
    <% if (!loggedIn) { %>
        <a href="registerDonor.jsp" class="feature-card">
            <div class="icon">📝</div>
            <h4>Register</h4>
            <p>Sign up as a donor</p>
        </a>
        <a href="login.jsp" class="feature-card">
            <div class="icon">🔐</div>
            <h4>Login</h4>
            <p>Access your account</p>
        </a>
    <% } else { %>
        <a href="dashboard.jsp" class="feature-card">
            <div class="icon">🏠</div>
            <h4>Dashboard</h4>
            <p>Donate or search</p>
        </a>
        <a href="searchByCity.jsp" class="feature-card">
            <div class="icon">📍</div>
            <h4>Search by City</h4>
            <p>Find donors near you</p>
        </a>
        <a href="searchByBloodGroup.jsp" class="feature-card">
            <div class="icon">🩸</div>
            <h4>Search by Group</h4>
            <p>Match by blood type</p>
        </a>
        <a href="RequestHistoryServlet" class="feature-card">
            <div class="icon">📋</div>
            <h4>History</h4>
            <p>View past requests</p>
        </a>
    <% } %>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>
