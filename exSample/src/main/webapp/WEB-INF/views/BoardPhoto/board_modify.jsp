<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../Include/topmenu.jsp" %>

<html>
   <head><title>게시판 작성</title>
    <link rel="stylesheet" type="text/css" href="/stylesheet.css">
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
<script>
	$(document).ready(function() {
		$('#summernote').summernote({
		  height: 300,
		  minHeight: null,
		  maxHeight: null,
		  focus: true,
		  lang: "ko-KR",
		  placeholder: '최대 2048자까지 쓸 수 있습니다',
		  callbacks: {
		    onImageUpload: function(files) {
		      for (var i = 0; i < files.length; i++) {
		        uploadImageFile(files[i], this);
		      }
		    }
		  }
		});
	});

	function uploadImageFile(file, el) {
		var data = new FormData();
		data.append("file", file);
		$.ajax({
			data: data,
			type: "POST",
			url: "/BoardPhoto/uploadImageFile",
			contentType: false,
			processData: false,
			success: function(res) {
				if (res.responseCode === "success") {
					$(el).summernote('editor.insertImage', res.url);
				} else {
					alert("이미지 업로드에 실패했습니다.");
				}
			},
			error: function() {
				alert("이미지 업로드에 실패했습니다.");
			}
		});
	}

function board_write(){
	if(!board.name.value){
		alert("이름이 입력되지 않았습니다.");
		board.name.focus();
		return;
	}
	if(!board.subject.value){
		alert("제목이 입력되지 않았습니다.");
		board.subject.focus();
		return;
	}
	if(!board.pass.value){
		alert("비밀번호가 입력되지 않았습니다.");
		board.pass.focus();
		return;
	}
	board.submit();
}
</script>
</head>
 <body topmargin="0" leftmargin="0">
 <table border="0" width="800">
 <tr>
   <td width="20%" height="500" bgcolor="#ecf1ef" valign="top">

   <!-- 다음에 추가할 부분 -->
	<jsp:include page="../Include/login_form.jsp" /> 
   </td>

   <td width="80%" valign="top">&nbsp;<br>
     <img src="/Images/img/bullet-01.gif"><font size="3" face="돋움" color="blue"> <b>반갑습니다</b></font>
     <font size="2"> - 글수정</font><p>
     <img src="/Images/img/bullet-03.gif"><font size="2" face="돋움" color="orange"> 잠깐</font> &nbsp;
     <img src="/Images/img/bullet-02.gif"><font size="2" face="돋움">는 필수 입력 사항입니다.</font><p>
     <form name="board" method="post" action="/BoardPhoto/board_modify_pro">
     <input type="hidden" name="idx" value="${board.idx }">
     <input type="hidden" name="page" value="${page }">
	  <table border="0">
       <tr>
         <td width="5%" align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td width="15%"><font size="2" face="돋움">글쓴이</font></td>
         <td width="80%">
         <input type="text" size="20" name="name" value="${board.name}" readyonly></td>
       </tr>
	   <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">제목</font></td>
         <td><input type="text" size="60" name="subject" value="${board.subject}"></td>
       </tr>
       <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">내용</font></td>
         <td><textarea wrap="physical" rows="10" id="summernote" name="contents" cols="60">${board.contents}</textarea></td>
       </tr>
	   <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">비밀번호</font></td>
          <td><input type="password" size="10" name="pass" ><font size="2" face="돋움">*.수정과 삭제시 꼭 입력하셔야 합니다.</font></td>
        </tr>
        <tr></tr>
		<tr>
          <td align="right">&nbsp;</td>
          <td><font size="2">&nbsp;</font></td>
          <td>
                     <a href="#" onclick="board_write()"><img src="/Images/img/save.gif" border=0></a>&nbsp;&nbsp;&nbsp;
                     <a href="#"><img src="/Images/img/cancle.gif" border=0></a>
          </td>
        </tr>
      </table>
      </form>
    </td>
  </tr>
  </table>
  </body>
  </html>
