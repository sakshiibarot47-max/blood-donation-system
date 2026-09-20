<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Blood Donor Finder</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <%
        if (session.getAttribute("donorName") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String choice = request.getParameter("choice");
    %>

    <div class="page-wrap">
    <div class="container wide">
        <h2>Welcome, <%= session.getAttribute("donorName") %> 👋</h2>
        <p class="subtitle">What would you like to do today?</p>

        <div class="home-grid">
            <a href="dashboard.jsp?choice=donate" class="btn" style="<%= "donate".equals(choice) ? "background-color:#27ae60;" : "" %>">🩸 I want to Donate</a>
            <a href="dashboard.jsp?choice=search" class="btn" style="<%= "search".equals(choice) ? "background-color:#27ae60;" : "" %>">🔍 I need Blood</a>
        </div>
<% if ("donate".equals(choice)) { %>
    <hr style="margin:30px 0; border:none; border-top:1px solid #eee;">
    <div style="text-align:center; padding: 10px 0;">
        <div style="font-size:50px; margin-bottom:10px;">❤️</div>
        <h3 style="color:#d6336c; margin-bottom:12px;">Thank you for saving lives.</h3>
        <p style="color:#666; max-width:420px; margin:0 auto; line-height:1.7;">
            You're already part of our donor family. Somewhere out there, a person you'll never meet might get to hug their family again — because you said yes.
        </p>
        <p style="color:#999; font-size:13px; margin-top:20px;">
            We'll keep you visible to people searching in your city and blood group. No action needed — just be ready when someone calls.
        </p>
    </div>
<% } else if ("search".equals(choice)) { %>
            <hr style="margin:30px 0; border:none; border-top:1px solid #eee;">
            <h3 style="text-align:center; color:#d6336c;">Find blood donors</h3>
            <div class="home-grid" style="margin-top:20px;">
                <a href="searchByCity.jsp" class="btn">📍 Search by City</a>
                <a href="searchByBloodGroup.jsp" class="btn">🩸 Search by Blood Group</a>
                <a href="emergencyRequest.jsp" class="btn" style="background-color:#c0392b;">🚨 Emergency Request</a>
            </div>
        <% } %>
    </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>