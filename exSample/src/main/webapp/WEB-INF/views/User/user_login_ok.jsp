<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--  
<c:if test="${empty session.user}">
	이런식도 하나의 방법이다 세션을 이용하는것. 아래는 로그인해서 userDTO에 담긴 정보들이 넘어 왔냐 안넘어 왔냐로 판단
	세션이 속도상으로 빠르다. 
</c:if>
-->
<c:if test="${empty user}">
	<script>
		alert("아이디가 없거나 비밀번호가 맞지 않습니다.");
		location.history.back();
	</script>
</c:if>
<c:if test="${not empty user}">
	<script>
		alert("로그인에 성공하였습니다.");
		location.href="/";
	</script>
</c:if>
</body>
</html>