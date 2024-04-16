<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="or">
<head>
    <meta charset="UTF-8"/>
    <title>users</title>
</head>
<body>
Users 페이지
<ul>
    <c:forEach var="user" items="${users}">
        <li>id: ${user.id}</li>
        <li>pw: ${user.pw}</li>
        <br>
    </c:forEach>
</ul>
</body>
</html>
