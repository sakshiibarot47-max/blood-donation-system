<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.Cookie" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search by Blood Group - Blood Donor Finder</title>
<link rel="stylesheet" type="text/css" href="style.css"></head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="container" style="max-width: 600px;">
        <h2>🔍 Search Donors by Blood Group</h2>

        <%
            String lastGroup = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("lastBloodGroup")) {
                        lastGroup = c.getValue();
                    }
                }
            }
        %>

        <% if (!lastGroup.isEmpty()) { %>
            <p style="color:#888; font-size:13px;">Last searched: <b><%= lastGroup %></b></p>
        <% } %>

        <form action="SearchByBloodGroupServlet" method="get">
            <label>Blood Group</label>
            <select name="bloodGroup" required>
                <option value="">-- Select --</option>
                <option value="A+" <%= lastGroup.equals("A+") ? "selected" : "" %>>A+</option>
                <option value="A-" <%= lastGroup.equals("A-") ? "selected" : "" %>>A-</option>
                <option value="B+" <%= lastGroup.equals("B+") ? "selected" : "" %>>B+</option>
                <option value="B-" <%= lastGroup.equals("B-") ? "selected" : "" %>>B-</option>
                <option value="O+" <%= lastGroup.equals("O+") ? "selected" : "" %>>O+</option>
                <option value="O-" <%= lastGroup.equals("O-") ? "selected" : "" %>>O-</option>
                <option value="AB+" <%= lastGroup.equals("AB+") ? "selected" : "" %>>AB+</option>
                <option value="AB-" <%= lastGroup.equals("AB-") ? "selected" : "" %>>AB-</option>
            </select>
            <input type="submit" value="Search">
        </form>

        <%
            String msg = request.getParameter("msg");
            if ("notfound".equals(msg)) {
        %>
            <p class="error">No donors found for that blood group.</p>
        <%
            }
        %>

        <span class="link"><a href="home.jsp">Back to Home</a></span>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>