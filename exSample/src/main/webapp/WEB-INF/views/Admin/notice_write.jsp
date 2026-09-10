<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>공지사항 관리 - 관리자페이지</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
	color: #606060;
}
body {
	margin-left: 0px;
	margin-top: 0px;
}
-->
</style>
<script type="text/javascript">
function notice_write(){
	if(!notice.subject.value){
		alert("제목이 입력되지 않았습니다.");
		notice.subject.focus();
		return;
	}
	if(!notice.contents.value){
		alert("내용이 입력되지 않았습니다.");
		notice.contents.focus();
		return;
	}
	notice.submit();
}
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td><jsp:include page="top_menu.jsp" flush="true" /></td></tr>
	<tr>
		<td align="center" height="100%" valign=middle><br>
			<table width="30%" border="1" cellspacing="0" cellpadding="3" bgcolor="#FFCC66" bordercolor="#FFFFFF" bordercolorlight="#000000">
				<tr> 
					<td height=40 align="center" style="font-size: 15px;"><b>공지사항 [쓰기][수정]</b></a>
					</b></td>
				</tr>
			</table><br>
			<form name="notice" method="post"
				action="${empty notice.idx ? '/Admin/Notice/notice_write' : '/Admin/Notice/notice_modify_pro'}">
			<c:if test="${!empty notice.idx}">
				<input type="hidden" name="idx" value="${notice.idx}">
			</c:if>
			<input type="hidden" name="adid" value="${notice.adid}">
			<table width="60%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><table width="100%" border="0" cellpadding="6" cellspacing="1" bgcolor="DDDDDD">
							<tr>
								<td width="20%" align="center" bgcolor="EcECEC"><strong>제목</strong></td>
								<td bgcolor="ffffff"><input name="subject" type="text" value="${notice.subject}"  style="width:450; height:18; padding:2; border:1 solid slategray" size="120"></td>
							</tr>
							<tr bgcolor="EcECEC">
								<td align="center" bgcolor="EcECEC"><strong>내용</strong></td>
								<td bgcolor="ffffff"><textarea name="contents" cols="10" rows="10" style="width:490; height:200; padding:2; border:1 solid slategray" tabindex="2">${notice.contents}</textarea></td>
							</tr>
						</table>
					</td>
				</tr>

			</table><br>
			<table width="60%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align=center><a href="#" onclick="notice_write(); return false;"><b>[${empty notice.idx ? '등록' : '수정'}]</b></a>&nbsp; <a href="/Admin/Notice/notice_list"><b>[취소]</b></a></td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</table>			
</body>
</html>
