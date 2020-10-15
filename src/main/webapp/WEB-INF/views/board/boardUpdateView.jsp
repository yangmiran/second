<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="currentPage" value="${page}" />
<!-- 값은 board로 받음 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>second</title>
</head>
<body>
<c:import url="/WEB-INF/views/common/header.jsp" />
<hr>
<h1 align="center">${ board.board_num } 번 게시글 수정 페이지</h1>
<!-- 원글 수정 폼 -->
<c:if test="${ board.board_level == 0 }">
<%-- <% if(board.getboard_level() == 0){ //원글이면  %> --%>
<form action="boriginup.do" method="post" enctype="multipart/form-data">
<input type="hidden" name="board_num" value="${ board.board_num }">
<input type="hidden" name="page" value="${ currentPage }">
<input type="hidden" name="ofile" value="${ board.board_original_filename }">
<input type="hidden" name="rfile" value="${ board.board_rename_filename }">
	<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
		<tr>
			<th width="120">제 목</th>
			<td><input type="text" name="board_title" size="50" value="${ board.board_title }"></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="board_writer" readonly value="${ board.board_writer }"></td>
		</tr>
		<tr>
			<th>파일 선택 : </th>
			<td>
				<c:if test="${ !empty board.board_original_filename }">
					${ board.board_original_filename } &nbsp;
					<input type="checkbox" name="delflag" value="yes"> 파일삭제
					<br>
				</c:if>
				<input type="file" name="upfile">
			</td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><textarea rows="5" cols="50" name="board_content">${ board.board_content }</textarea></td>
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
<c:if test="${ board.board_level ne 0 }">
<form action="breplyup.do" method="post">
<input type="hidden" name="bnum" value="${ board.board_num }">
<input type="hidden" name="page" value="${ currentPage }">
	<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
		<tr>
			<th>제 목</th>
			<td><input type="text" name="board_title" size="50" value="${ board.board_title }"></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="board_writer" readonly value="${ board.board_writer }"></td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><textarea rows="5" cols="50" name="board_content">${ board.board_content }</textarea></td>
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