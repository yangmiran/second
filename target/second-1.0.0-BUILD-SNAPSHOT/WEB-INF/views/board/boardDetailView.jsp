<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="currentPage" value="${ requestScope.currentPage }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testm</title>
</head>
<body>
<c:import url="/views/common/header.jsp" />
<hr>
<h2 align="center">${ requestScope.board.boardNum } 번 게시글 상세보기</h2>
<br>
<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
	<tr>
		<th>제 목</th>
		<td>${ board.boardTitle }</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${ board.boardWriter }</td>
	</tr>
	<tr>
		<th>등록날짜</th>
		<td><fmt:formatDate value="${ board.boardDate }" type="date" pattern="yyyy-MM-dd" /></td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td>
			<c:if test="${ !empty board.boardOriginalFileName }">
				<c:url var="ubf" value="/bfdown">
					<c:param name="ofile" value="${ board.boardOriginalFileName }" />
					<c:param name="rfile" value="${ board.boardRenameFileName }" />
				</c:url>
			<a href="${ ubf }">${ board.boardOriginalFileName }</a>
			</c:if>
			<c:if test="${ empty board.boardOriginalFileName }">
			&nbsp;
			</c:if>
		</td>
	</tr>
	<tr>
		<th>내 용</th>
		<td>${ board.boardContent }</td>
	</tr>
	<tr>
		<th colspan="2">
			<c:if test="${ !empty sessionScope.loginMember }">
			<%-- <% if(loginMember != null){ //댓글달기, 수정페이지로 이동 둘 다 로그인 상태여야 함 --%>
				<c:if test="${ loginMember.getUserid() eq board.boardWriter }">
				<c:url var="ubup" value="/bupview">
					<c:param name="bnum" value="${ board.boardNum }"/>
					<c:param name="page" value="${ currentPage }"/>
				</c:url>
				<a href="${ ubup }">[수정페이지로 이동]</a>
				&nbsp; &nbsp;
				<c:url var="ubd" value="/bdelete">
					<c:param name="bnum" value="${ board.boardNum }"/>
					<c:param name="level" value="${ board.boardLevel }"/>
					<c:param name="rfile" value="${ board.boardRenameFileName }"/>
				</c:url>
				<a href="${ ubd } %>">[글삭제]</a>
				&nbsp; &nbsp;
				</c:if>
				<c:if test="${ loginMember.getUserid() ne board.boardWriter }">
					<c:url var="brf" value="/views/board/boardReplyForm.jsp">
						<c:param name="bnum" value="${ board.boardNum }" />
						<c:param name="page" value="${ currentPage }" />
					</c:url>
					<a href="${ brf }">[댓글달기]</a>
				</c:if>
			</c:if>
			&nbsp; &nbsp;
			<c:url var="ubl" value="/blist">
				<c:param name="page" value="${ currentPage }" />
			</c:url>
			<button onclick="javascript:location.href='${ ubl }'">목록</button>
		</th>
	</tr>
</table>
</body>
</html>