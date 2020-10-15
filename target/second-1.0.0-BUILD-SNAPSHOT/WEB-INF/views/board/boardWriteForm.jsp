<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testm</title>
</head>
<body>
<c:import url="/views/common/header.jsp" />
<hr>
<h1 align="center">게시글 등록 페이지</h1>
<form action="/testm/binsert" method="post" enctype="multipart/form-data">
	<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
		<tr>
			<th>제 목</th>
			<td><input type="text" name="title" size="50"></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="writer" readonly value="${ loginMember.userid }"></td>
		</tr>
		<tr>
			<th>파일 선택 : </th>
			<td><input type="file" name="ofile"></td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><textarea rows="5" cols="50" name="content"></textarea></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="등록하기"> &nbsp;
				<input type="reset" value="작성취소"> &nbsp;
				<input type="button" value="목록" onclick="javascript:history.go(-1); return false;">
				<!-- return false; 사용하면 클릭이 submit에 전송되는걸 막아줌 -->
			</th>
		</tr>
	</table>
</form>
</body>
</html>