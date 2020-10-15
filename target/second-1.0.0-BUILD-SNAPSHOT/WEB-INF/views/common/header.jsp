<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>second</title>
<style>
header {margin:0; padding:0;}
header h1#logo {
	font-size: 36pt;
	font-style: italic;
	color: navy;
	text-shadow: 2px 2px 2px gray;
}

header ul#menubar {
	list-style:none;
	text-align: center;
	padding: 0;
}
hr {clear:both;}

header ul#menubar li {
	/* float: left; */
	display:inline-block;
	width: 120px;
	height: 30px;
	margin-right: 5px;
	padding: 0;
}
header ul#menubar li a {
	text-decoration: none;
	width:120px;
	height: 30px;
	display:block;
	background-color: orange;
	text-align: center;
	color: navy;
	font-weight: bold;
	margin: 0;
	text-shadow: 1px 1px 2px white;
	line-height : 30px;
}
header ul#menubar li a:hover {
	text-decoration: none;
	width:120px;
	height: 30px;
	display:block;
	background-color: navy;
	text-align: center;
	color: white;
	font-weight: bold;
	margin: 0;
	text-shadow: 1px 1px 2px navy;
	line-height : 30px;
}
</style>
</head>
<body>
<header>
<h1 id="logo">second</h1>
<c:if test="${ !empty loginMember and loginMember.userid eq 'admin' }">
<ul id="menubar">
	<li><a href="/testm/mlist">회원관리</a></li>
	<li><a href="/testm/nlist.ad">공지글관리</a></li>
	<li><a href="/testm/blist">게시글관리</a></li>
	<li><a href="#">QnA관리</a></li>
	<li><a href="#">사진게시판관리</a></li>
	<li><a href="/testm/views/test/testPage.jsp">필터테스트</a></li>
	<li><a href="index.jsp">Home</a></li>
</ul>
</c:if>
<c:if test="${ !empty loginMember and !(loginMember.userid eq 'admin') }">
<ul id="menubar">
	<li><a href="/testm/mlist">암호화회원가입</a></li>
	<li><a href="/testm/nlist">공지사항</a></li>
	<li><a href="/testm/blist?page=1">게시글</a></li>
	<li><a href="#">QnA</a></li>
	<li><a href="#">사진게시판</a></li>
	<li><a href="/testm/views/test/testPage.jsp">필터테스트</a></li>
	<li><a href="index.jsp">Home</a></li>
</ul>
</c:if>
<c:if test="${ empty sessionScope.loginMember }">
<ul id="menubar">
	<li><a href="/testm/views/member/enrollPage.html">회원가입</a></li>
	<li><a href="/testm/nlist">공지사항</a></li>
	<li><a href="/testm/blist?page=1">게시글</a></li>
	<li><a href="#">QnA</a></li>
	<li><a href="/testm/views/test/cryptoEnrollPage.html">암호화회원가입</a></li>
	<li><a href="/testm/views/test/testPage.jsp">필터테스트</a></li>
	<li><a href="index.jsp">Home</a></li>
</ul>
</c:if>
</header>
</body>
</html>