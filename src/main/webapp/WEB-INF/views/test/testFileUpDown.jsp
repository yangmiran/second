<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>second</title>
</head>
<body>
<h1>spring second : fileUpload / FileDownload Test Page</h1>
<h2>파일 업로드 테스트</h2>
<form action="tinsert.do" method="post" enctype="multipart/form-data">
이 름 : <input type="text" name="name" required><br>
나 이 : <input type="number" name="age" required><br>
첨부파일 : <input type="file" name="upfile"><br>
<input type="submit" value="전송하기">
</form>
<hr>
<h2>파일 다운로드 테스트</h2>
<a href="fdown.do?fname=로깅.txt">로깅.txt</a>
</body>
</html>