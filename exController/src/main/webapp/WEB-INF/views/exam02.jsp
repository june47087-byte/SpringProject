<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>학생수: ${list.size()}<br></p>
	<p>
	<c:forEach var="students" items="${list}">
		${students} <br>	
	</c:forEach>
	</p>
</body>
</html>