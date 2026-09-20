<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Search by City - Blood Donor Finder</title>
<link rel="stylesheet" type="text/css" href="style.css"></head>
<body>
<jsp:include page="navbar.jsp" />
    <div class="container" style="max-width: 600px;">
        <h2>🔍 Search Donors by City</h2>
        <form action="SearchByCityServlet" method="get">
            <label>City</label>
            <input type="text" name="city" required placeholder="e.g. Ahmedabad">
            <input type="submit" value="Search">
        </form>

        <%
            String msg = request.getParameter("msg");
            if ("notfound".equals(msg)) {
        %>
            <p class="error">No donors found in that city.</p>
        <%
            }
        %>

        <span class="link"><a href="home.jsp">Back to Home</a></span>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>