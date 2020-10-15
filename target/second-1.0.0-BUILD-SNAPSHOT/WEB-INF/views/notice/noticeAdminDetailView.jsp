<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testm</title>
</head>
<body>
<c:import url="/views/common/header.jsp" />
<hr>
<h2 align="center">${ notice.noticeNo  } 번 공지 상세보기</h2>
<br>
<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
	<tr>
		<th>제 목</th>
		<td>${  notice.noticeTitle }</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${  notice.noticeWriter }</td>
	</tr>
	<tr>
		<th>등록날짜</th>
		<td>${  notice.noticeDate }</td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td>
			<c:if test="${ !empty notice.originalFilepath}">
				<c:url var="unf" value="/nfdown">
					<c:param name="ofile" value="${ notice.originalFilepath }"/>
					<c:param name="rfile" value="${ notice.renameFilepath }"/>
				</c:url>
				<a href="${ unf }">${ notice.originalFilepath }</a>
			</c:if>
			<c:if test="${ empty notice.originalFilepath }">
			&nbsp;
			</c:if>
		</td>
	</tr>
	<tr>
		<th>내 용</th>
		<td>${ notice.noticeContent }</td>
	</tr>
	<tr>
		<th colspan="2">
			<c:url var="unp" value="/npmove">
				<c:param name="noticeno" value="${ notice.noticeNo }" />
			</c:url>
			<button onclick="javascript:location.href='${ unp }';">수정페이지로 이동</button> &nbsp;
			
			<c:url var="und" value="/ndel">
				<c:param name="noticeno" value="${ notice.noticeNo }" />
				<c:param name="rfile" value="${ notice.renameFilepath }" />
			</c:url>
			<button onclick="javascript:location.href='${ und }';">삭제하기</button> &nbsp;
			<button onclick="javascript:history.go(-1);">목록</button>
		</th>
	</tr>
</table>
</body>
</html>