<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Blood Donor Finder</title>
<link rel="stylesheet" type="text/css" href="style.css"></head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="page-wrap">
    <div class="container">
        <h2>❤️ Donor Login</h2>

        <%
            String msg = request.getParameter("msg");
            if ("invalid".equals(msg)) {
        %>
            <p class="error">Invalid email or password.</p>
        <%
            } else if ("error".equals(msg)) {
        %>
            <p class="error">Something went wrong. Please try again.</p>
        <%
            }
        %>

        <form action="LoginServlet" method="post">
            <label>Email</label>
            <input type="email" name="email" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <input type="submit" value="Login">
        </form>
        <span class="link">Don't have an account? <a href="registerDonor.jsp">Register here</a></span>
    </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>