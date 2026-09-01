<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Hello Test 
	<c:forEach var="index" begin="1" end="10">
		I = <c:out value="${index}"/> <br>
	</c:forEach>
</body>
</html>