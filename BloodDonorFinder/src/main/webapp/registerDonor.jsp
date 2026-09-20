<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Blood Donor Finder</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="page-wrap">
    <div class="container">
        <h2>🩸 Register as Donor</h2>
        <form action="RegisterDonorServlet" method="post">
            <label>Full Name</label>
            <input type="text" name="name" required>

            <label>Blood Group</label>
            <select name="bloodGroup" required>
                <option value="">-- Select --</option>
                <option value="A+">A+</option>
                <option value="A-">A-</option>
                <option value="B+">B+</option>
                <option value="B-">B-</option>
                <option value="O+">O+</option>
                <option value="O-">O-</option>
                <option value="AB+">AB+</option>
                <option value="AB-">AB-</option>
            </select>

            <label>City</label>
            <input type="text" name="city" required>

            <label>Phone</label>
            <input type="text" name="phone" required>

            <label>Email</label>
            <input type="email" name="email" required>

            <label>Password</label>
            <input type="password" name="password" required>

            <input type="submit" value="Register">
        </form>
        <span class="link">Already registered? <a href="login.jsp">Login here</a></span>
    </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>
