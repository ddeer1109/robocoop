<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<ul>
<c:forEach var="user" items="${users}">
    <li>${user.id} : ${user.username}</li>
</c:forEach>
</ul>
</body>
</html>
