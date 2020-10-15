<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
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
<h2 align="center">${ notice.noticeNo }번 글 수정페이지</h2>
<br>
<form action="/testm/nupdate.ad" method="post" enctype="multipart/form-data">
	<input type="hidden" name="no" value="${ notice.noticeNo }">
	<input type="hidden" name="ofile" value="${ notice.originalFilepath }">
	<input type="hidden" name="rfile" value="${ notice.renameFilepath }">
	<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
		<tr>
			<th>제 목</th>
			<td><input type="text" name="title" size="50" value="${ notice.noticeTitle }"></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="writer" readonly value="${ notice.noticeWriter }"></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<c:if test="${!empty notice.originalFilepath }">
				${ notice.originalFilepath } &nbsp;
					<input type="checkbox" name="deleteFlag" value="yes"> 파일삭제<br>
				</c:if>
				<c:if test="${empty notice.originalFilepath }">
					첨부파일 없음<br>
					<input type="file" name="upfile">
				</c:if>
			</td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><textarea rows="5" cols="50" name="content">${ notice.noticeContent }</textarea></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="수정하기"> &nbsp;
				<input type="reset" value="수정취소"> &nbsp;
				<input type="button" value="이전 페이지로" onclick="javascript:history.go(-1); return false;">
			</th>
		</tr>
	</table>
</form>
</body>
</html>