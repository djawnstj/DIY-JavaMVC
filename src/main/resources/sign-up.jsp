<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="or">
<head>
    <meta charset="UTF-8"/>
    <title>sign-up</title>
</head>
<body>
Sign-Up 페이지
<form method="post" action="/sign-up">
    <input type="text" name="id" placeholder="id 를 입력하세요.">
    <input type="text" name="pw" placeholder="pw 를 입력하세요.">
    <button type="submit">회원 가입</button>
</form>
</body>
</html>
