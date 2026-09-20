<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Emergency Request - Blood Donor Finder</title>
<link rel="stylesheet" type="text/css" href="style.css"></head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="container">
        <h2 style="color:#c0392b;">🚨 Emergency Blood Request</h2>
        <p style="color:#666; font-size:14px; text-align:center;">Fill this form and nearby donors will be visible to help fast.</p>

        <form action="EmergencyRequestServlet" method="post">
            <label>Patient Name</label>
            <input type="text" name="patientName" required>

            <label>Blood Group Needed</label>
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

            <label>Contact Number</label>
            <input type="text" name="contactNumber" required>

            <label>Hospital Name</label>
            <input type="text" name="hospitalName">

            <label>Message (optional)</label>
            <input type="text" name="message" placeholder="e.g. Needed urgently by tonight">

            <input type="submit" value="Submit Emergency Request" style="background-color:#c0392b;">
        </form>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>