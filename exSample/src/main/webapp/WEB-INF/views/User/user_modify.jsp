<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>회원등록</title>
<STYLE TYPE="text/css">
<!--
body { font-family: 돋움, Verdana; font-size: 9pt}
td   { font-family: 돋움, Verdana; font-size: 9pt; text-decoration: none; color: #000000; BACKGROUND-POSITION: left top; BACKGROUND-REPEAT: no-repeat;}
-->
.formbox {
	BACKGROUND-COLOR: #F0F0F0; FONT-FAMILY: "Verdana", "Arial", "Helvetica", "돋움"; FONT-SIZE:9pt
} 
--->
<link rel="stylesheet" type="text/css" href="/css/stylesheet.css">
</STYLE>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
<script type="text/javascript">
$(function(){
	/*
	var gubun= ${user.gubun};
	if(gubun==1){
		$("#smscheck").hide();
		$("#emailcheck").hide();
		$("#email").hide();
	}else{
		$("#smscheck").hide();
		$("#emailcheck").hide();
		$("#sms").hide();
	}
	}
	*/
	$("#smscheck").hide();
	$("#emailcheck").hide();
	//저장된 인증방법(gubun)에 맞춰 초기 화면 표시
	if($("#mode1").is(":checked")){
		$("#email").hide();
	}else{
		$("#phone").hide();
	}

	//인증 완료 여부 플래그
	var phoneVerified = false;
	var emailVerified = false;


	//라디오 버튼 선택시
	$("input[name='mode']").change(function() {
        if ($("#mode1").is(":checked")) {
            $("#phone").show();
            $("#email").hide();
           	$("#smscheck").hide();
           	$("#emailcheck").hide();
        } else {
            $("#email").show();
            $("#phone").hide();
           	$("#smscheck").hide();
           	$("#emailcheck").hide();
        }
    });
	
	
	//핸드폰 인증하기 버튼 클릭시
	$("#phoneBtn1").click(function(){
		$("#smscheck").show();

	});

	//이메일 인증하기 버튼 클릭시
	$("#emailBtn1").click(function(){
		var email1 = $("[name='email1']").val().trim();
		var email2 = $("[name='email2']").val().trim();
		if(email1=='' || email2==''){
			alert("이메일을 입력하세요");
			return;
		}
		var email = email1 + "@" + email2;
		$.ajax({
			url:"/User/user_email",
			type:"post",
			data:{"email": email},
			success:function(result){
				reemail_r.innerHTML="인증번호가 전송되었습니다.";
				$("#useremail").val(result);
			}
		});
		$("#emailcheck").show();
	});

	//이메일 체크(직접입력 또는 선택)
	$("#email3").on("change",function(){
		if($("#email3").prop("selectedIndex") !=0 ){
			$('#email2').prop('readonly', true);//읽기 전용으로
			$('#email2').val($("#email3").val());
		}else{
			$('#email2').prop('readonly', false);//읽기 전용 해제
			$('#email2').val('');
		}
	});

	//id 중복검사(Ajax))
	var useridChecked = false;
	$("#userid").change(function(){
		var userid = $("#userid").val();
		useridChecked = false;
		//alert("AAA");

		$.ajax({
			url:"/User/user_idCheck",
			type:"post",
			data:{"userid":userid},
			success:function(result){
				if(result==0){
					userID_c.innerHTML="사용 가능한 아이디입니다";
					useridChecked = true;
				}else{
					userID_c.innerHTML="이미 사용중인 아이디입니다";
					$("#userid").val('');
					$("#userid").focus();
				}
			}
		});
	});

	//비밀번호 확인
	$("#repasswd").change(function(){
		if($("#passwd").val() == $("#repasswd").val()){
			repasswd_c.innerHTML="확인 되었습니다."
		}else{
			repasswd_c.innerHTML="비밀번호를 다시 입력하세요"
				$("#repasswd").val('');
				$("#repasswd").focus();
		}
	});
	// sms 본인 인증(인증번호 발송)
	$("#phoneBtn1").click(function(){
		// 전화번호유효성 검사
		if($("#tel").val()==''){
			alert("전화번호를 입력하세요");
			$("#tel").focus();
			return;
		}
		var tel = $("#tel").val();
		$.ajax({
			url:"/User/user_sms",
			type:"post",
			data:{"tel": tel},
			success:function(result){
				phone_c.innerHTML="인증번호가 전송되었습니다.";
				$("#usersms").val(result);
			}
		});
		$("#smscheck").show();
	});
	// sms 본인 인증(인증번호 확인)
	$("#phoneBtn3").click(function(){
		if($("#resms").val() == $("#usersms").val()){
			resms_c.innerHTML="인증되었습니다."
			phoneVerified = true;
		}else{
			resms_c.innerHTML="인증번호가 일치하지 않습니다. 다시입력하세요"
			phoneVerified = false;
			$("#resms").val('');
			$("#resms").focus();
			return;
		}
	});
	// email 본인인증
	$("#emailBtn3").click(function(){
		if($("#reemail").val() == $("#useremail").val()){
			reemail_c.innerHTML="인증되었습니다."
			emailVerified = true;
		}else{
			reemail_c.innerHTML="인증번호가 일치하지 않습니다. 다시입력하세요"
			emailVerified = false;
			$("#reemail").val('');
			$("#reemail").focus();
			return;
		}
	});

	//수정하기 버튼 클릭 시 전체 유효성 검사
	$("#usersend").click(function(){
		var passwd = $("#passwd").val();
		var repasswd = $("#repasswd").val();
		var mode = $("input[name='mode']:checked").val();

		//비밀번호
		var passwdPattern = /^[A-Za-z0-9]{6,12}$/;
		if(passwd==''){
			alert("비밀번호를 입력하세요");
			$("#passwd").focus();
			return false;
		}
		if(!passwdPattern.test(passwd)){
			alert("비밀번호는 6~12자 이내의 영문이나 숫자만 가능합니다.");
			$("#passwd").focus();
			return false;
		}
		if(repasswd==''){
			alert("비밀번호 확인을 입력하세요");
			$("#repasswd").focus();
			return false;
		}
		if(passwd != repasswd){
			alert("비밀번호가 일치하지 않습니다");
			$("#repasswd").focus();
			return false;
		}
		//인증방법별 인증여부
		if(mode=='1'){
			if($("#tel").val().trim()==''){
				alert("전화번호를 입력하세요");
				$("#tel").focus();
				return false;
			}
			if(!phoneVerified){
				alert("휴대폰 본인인증을 완료해주세요");
				$("#tel").focus();
				return false;
			}
		}else{
			if($("[name='email1']").val().trim()=='' || $("[name='email2']").val().trim()==''){
				alert("이메일을 입력하세요");
				return false;
			}
			if(!emailVerified){
				alert("이메일 본인인증을 완료해주세요");
				return false;
			}
		}
		user.submit();
		return true;
	});

	//취소하기 버튼 클릭 시
	$("#userscancle").click(function(){
		history.back();
	});
});
</script>
</head>

<body bgcolor="#FFFFFF" LEFTMARGIN=0  TOPMARGIN=0 >
 
 <!-- 탑 메뉴 영역 삽입-->
<%@ include file="../Include/topmenu.jsp" %>

<table border="0" width="800">
<tr>
  <td width="20%"  bgcolor="#ecf1ef" valign="top" style="padding-left:0;">
	
	<!--로그인 영역 삽입-->
	<%@ include file="../Include/login_form.jsp" %>
	
  </td>
  <td width="80%" valign="top">&nbsp;<img src="/Images/img/title1.gif" ><br>    
	<form name="user" method=post action="user_modify">
	<input type="hidden" id="usersms" name="usersms">
	<input type="hidden" id="useremail" name="useremail">
	<table border=0 cellpadding=0 cellspacing=0 width=730 valign=top>
		<tr><td align=center><br>                            
			<table cellpadding=0 cellspacing=0 border=0 width=650 align=center>       
				<tr>
					<td bgcolor="#7AAAD5">            
						<table cellpadding=0 cellspacing=0 border=0 width=100%>
							<tr bgcolor=#7AAAD5>
								<td align=left BORDER="0" HSPACE="0" VSPACE="0"><img src="/Images/img/u_b02.gif"></td>
								<td align=center bgcolor="#7AAAD5"><FONT COLOR="#FFFFFF"><b>사용자등록&nbsp;</b><font color=black>(</font><font color=red>&nbsp;*&nbsp;</font><font color=black>표시항목은 반드시 입력하십시요.)</font></FONT></td>
								<td align=right BORDER="0" HSPACE="0" VSPACE="0"><img src="/Images/img/u_b03.gif"></td>
							</tr>
						</table>
						<table cellpadding=3 cellspacing=1 border=0 width=100%>
							<tr>
								<td width=110 bgcolor=#EFF4F8>&nbsp;회원 성명<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<input type=text id=name name=name size=16 maxlength=20 value="${user.name }" readonly>
								</td>
							</tr>
							<tr>
								<TD BGCOLOR="#EFF4F8">&nbsp;회원 ID<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<table cellspacing=0 cellpadding=0>
										<tr>
											<td align=absmiddle>
												<input type=text id=userid name=userid size=12 maxlength=16 value="${user.userid }" style="width:120" readonly>
											</td>
											<td id="userID_c">
                  								[ 5~16자 이내의 영문이나 숫자만 가능합니다. ]
                  							</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<TD BGCOLOR="#EFF4F8">&nbsp;비밀번호<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
								<input type=password id=passwd name=passwd size=8 maxlength=12 style="width:80">
									6~12자 이내의 영문이나 숫자만 가능합니다.
								</td>
							</tr>
							<tr>
								<TD BGCOLOR="#EFF4F8">&nbsp;비밀번호확인<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE><input type=password id=repasswd name=repasswd size=8 maxlength=12 value="" style="width:80">
									<font id=repasswd_c color=red>&nbsp;*비밀번호 확인을 위해서 비밀번호를 한번 더 입력해주세요. </font> 
								</td>
							</tr>
							<tr>
								<TD BGCOLOR="#EFF4F8">&nbsp;인증 방법 선택<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE><input type=radio id="mode1" name=mode value="1" ${user.gubun=='1'? 'checked': ''}>핸드폰
									<input type=radio id="mode2" name="mode" value="2"  ${user.gubun=='2'? 'checked': ''}>이메일
								</td>
							</tr>
							<tr id="phone">
								<TD BGCOLOR="#EFF4F8">&nbsp;전화번호<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<input type=text id=tel name=tel size=13 maxlength=13 value="${user.tel}" placeholder="휴대전화번호 (-제외)">
									<input type="button" id="phoneBtn1" value="인증번호받기">
									<font id="phone_c" size="2" color="red">&nbsp;</font>
								</td>
							</tr>
							<tr id="smscheck">
								<TD BGCOLOR="#EFF4F8">&nbsp;인증번호<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<input type=text id=resms name="resms" size=13 maxlength=13 placeholder="인증번호를 입력하세요">
									<input type="button" id="phoneBtn2" value="재발송">
                    				<font id="resms_r" size="2" color="red">&nbsp;</font>
                    				<input type="button" value="인증" id="phoneBtn3">
                    				<font id="resms_c" size="2" color="red">&nbsp;</font>
								</td>
							</tr>			
							<tr id="email">
								<TD BGCOLOR="#EFF4F8">&nbsp;E-mail
                					<font color=red>&nbsp;</font>
								</td>
								<td bgcolor=WHITE valign=middle>
									<input type="text" name="email1" size=13 maxlength="15">
									@ <input type="text" name="email2" size=13 maxlength="15">
									<select name="email3">
		      							<option value="0">직접입력</option>
		      							<option value="naver.com">naver.com</option>
		      							<option value="daum.net">daum.net</option>
		      							<option value="nate.com">nate.com</option>
		      							<option value="gmail.com">gmail.com</option>
		  							   </select>
									 <input type="button" id="emailBtn1" value="인증하기">
								</td>
							</tr>
							<tr id="emailcheck">
								<TD BGCOLOR="#EFF4F8">&nbsp;이메일 인증번호<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<input type=text id=reemail name="reemail" size=13 maxlength=13 placeholder="이메일 인증번호를 입력하세요">
									<input type="button" id="emailBtn2" value="재발송">
                    				<font id="reemail_r" size="2" color="red">&nbsp;</font>
                    				<input type="button" value="인증" id="emailBtn3">
                    				<font id="reemail_c" size="2" color="red">&nbsp;</font>
								</td>
							</tr>							
						</table>
						<table cellpadding=0 cellspacing=0 border=0 width=100%>
							<tr bgcolor=#7AAAD5>
								<td valign=bottom>
									<img src="/Images/img/u_b04.gif" align=left hspace=0 vspace=0 border=0>
								</td>
								<td align=center></td>
								<td valign=bottom>
									<img src="/Images/img/u_b05.gif" align=right hspace=0 vspace=0 border=0>
								</td>
							</tr>
							<tr bgcolor=#ffffff>
								<td colspan=3 align=center>
									<input type="button" id="usersend" value="수정하기">
									<input type="button" id="userscancle" value="취소하기">
								</td>
							</tr>
						</table> 
					</td>
				</tr>
				</td>
			</tr>
		</table>
	</form>
	</td>
</tr>
</table>


 <!-- copyright 영역 삽입-->
  

</body>
</html>
