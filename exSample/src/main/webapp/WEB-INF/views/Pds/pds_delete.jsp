<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>자료 삭제</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
 <script>
 	function send(){
 		if(!pds.pass.value){
 			alert("비밀번호를 입력해 주세요.");
 			pds.pass.focus();
 			return;
 		}
 		pds.submit();
 	}
 </script>
</head>
<body>
   <c:if test="${row == 0}">
   <script>alert("비밀번호가 틀렸습니다.");</script>
   </c:if>
	<form name="pds" method="post" action="/Pds/pds_delete_pro">
	  <input type="hidden" name="idx" value="${pds.idx }">
     <input type="hidden" name="page" value="${page}">
  <table border="0" cellpadding="0" cellspacing="0" width="300" align="center">
  <tr>
    <td height="50">
    <img src="/Images/bullet-05.gif"> <b><font size="3" color="red">잠깐 !!</font></b></td></tr>
  <tr>
    <td valign="middle" height="30">
    <font size="2" face="돋움">게시물은 작성하신 분만 삭제할 수 있습니다.<br>
    글의 비밀번호를 입력해 주세요...</font></td></tr>
  <tr>
    <td valign="middle" height="40">
    <font size="2" face="돋움">비밀번호 <input type="password" name="pass" size="8"></font>
    <input type="button" value="삭제" onclick="send()">
    <input type="button" value="닫기" onclick="history.back()"> </td></tr>
  </table>
</body>
</html>
