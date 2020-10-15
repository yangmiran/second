<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPage" value="${ requestScope.page }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testm</title>
</head>
<body>
<c:import url="/views/common/header.jsp" />
<hr>
<h1 align="center">${ requestScope.board.boardNum } 번 게시글 수정 페이지</h1>
<!-- 원글 수정 폼 -->
<c:if test="${ board.boardLevel == 0 }">
<%-- <% if(board.getBoardLevel() == 0){ //원글이면  %> --%>
<form action="/testm/boriginup" method="post" enctype="multipart/form-data">
<input type="hidden" name="bnum" value="${ board.boardNum }">
<input type="hidden" name="page" value="${ currentPage }">
<input type="hidden" name="ofile" value="${ board.boardOriginalFileName }">
<input type="hidden" name="rfile" value="${ board.boardRenameFileName }">
	<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
		<tr>
			<th width="120">제 목</th>
			<td><input type="text" name="title" size="50" value="${ board.boardTitle }"></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="writer" readonly value="${ board.boardWriter }"></td>
		</tr>
		<tr>
			<th>파일 선택 : </th>
			<td>
				<c:if test="${ !empty board.boardOriginalFileName }">
					${ board.boardOriginalFileName } &nbsp;
					<input type="checkbox" name="delflag" value="yes"> 파일삭제
					<br>
				</c:if>
				<input type="file" name="upfile">
			</td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><textarea rows="5" cols="50" name="content">${ board.boardContent }</textarea></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="수정하기"> &nbsp;
				<input type="reset" value="작성취소"> &nbsp;
				<input type="button" value="이전 페이지로 이동" onclick="javascript:history.go(-1); return false;">
			</th>
		</tr>
	</table>
</form>
</c:if>
<c:if test="${ board.boardLevel ne 0 }">
<form action="/testm/breplyup" method="post">
<input type="hidden" name="bnum" value="${ board.boardNum }">
<input type="hidden" name="page" value="${ currentPage }">
	<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
		<tr>
			<th>제 목</th>
			<td><input type="text" name="title" size="50" value="${ board.boardTitle }"></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="writer" readonly value="${ board.boardWriter }"></td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><textarea rows="5" cols="50" name="content">${ board.boardContent }</textarea></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value="수정하기"> &nbsp;
				<input type="reset" value="작성취소"> &nbsp;
				<input type="button" value="이전페이지로 이동" onclick="javascript:history.go(-1); return false;">
			</th>
		</tr>
	</table>
</form>
</c:if>
</body>
</html>