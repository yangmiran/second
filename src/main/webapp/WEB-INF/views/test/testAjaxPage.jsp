<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>second</title>
<script type="text/javascript" src="/second/resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
$(function(){ //window.onload = function(){};
	//테스트 1버튼을 클릭했을 때, 서버 컨트롤러 메소드로 값 보내기
	$("#test1").on("click", function(){
		$.ajax({
			url: "test1.do",
			data: {name: $("#name").val(), age: $("#age").val()},
			type: "post",
			success: function(result){
				if(result == "ok"){
					alert("값 보내기 성공!");
					console.log("result : " + result);
					$("#d1").html("<h5>" + result + "</h5>");
				}else{ //'ok'가 아닌 다른 문자열 값이면
					alert("값 전송 실패!");
				}
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status 
						+ "\nMessage : " + request.responseText + "\nError : " + errorData);
			}
		});	//ajax
	}); //test1 click
	
	//테스트 2 버튼을 클릭하면, 서버 컨트롤러에서 전송한 json 받아서 출력
	$("#test2").on("click", function(){
		$.ajax({
			url: "test2.do",
			type: "post", //json을 받을 때는 post 로 지정해야 함
			dataType: "json", //받는 값의 종류 지정(기본 : text)
			success: function(jsonData){
				//json 객체 한 개를 받았을 때는 바로 출력 처리할 수 있음
				console.log("jsonData : " + jsonData);
				
				$("#d2").html("번호 : " + jsonData.no
						+ "<br>제목 : " + jsonData.title
						+ "<br>작성자 : " + decodeURIComponent(jsonData.writer)
						+ "<br>내용 : " + decodeURIComponent(jsonData.content.replace(/\+/gi, " ")));
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status 
						+ "\nMessage : " + request.responseText + "\nError : " + errorData);
			}
		});//ajax
	});//test2 click
	
	//테스트 3 버튼을 클릭하면, 서버 컨트롤러에서 전송한 json 배열 받아서 출력
	$("#test3").on("click", function(){
		$.ajax({
			url: "test3.do",
			type: "post",
			dataType: "json",
			success: function(obj){
				//json 배열을 가진 객체를 받은 경우
				//object ==> string ==> parsing : json
				console.log(obj); //object
				
				//리턴된 객체를 문자열로 변환 처리
				var objStr = JSON.stringify(obj);
				//객체문자열을 다시 json 객체로 바꿈
				var jsonObj = JSON.parse(objStr);
				
				//출력용 문자열 준비
				var output = $("#d3").html();
				
				//출력용 문자열 만들기
				for(var i in jsonObj.list){
					output += jsonObj.list[i].userid
						+ ", " + jsonObj.list[i].userpwd
						+ ", " + decodeURIComponent(jsonObj.list[i].username.replace(/\+/gi, " "))
						+ ", " + jsonObj.list[i].age
						+ ", " + jsonObj.list[i].email
						+ ", " + jsonObj.list[i].phone
						+ ", " + jsonObj.list[i].birth + "<br>"
				}
				
				$("#d3").html(output);
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status 
						+ "\nMessage : " + request.responseText + "\nError : " + errorData);
			}
		});//ajax
	});//test3 click
	
	//테스트 4 버튼을 클릭하면, 서버 컨트롤러에서 전송한 Map 객체를 받아서 출력
	$("#test4").on("click", function(){
		$.ajax({
			url: "test4.do",
			type: "post",
			dataType: "json",
			success: function(jsonObj){
				console.log(jsonObj);
				
				$("#d4").html("받은 Map 안의 객체 정보 확인<br>"
						+ "이름 : " + decodeURIComponent(jsonObj.hashMap.samp.name).replace(/\+/gi, " ")
						+ ", 나이 : " + jsonObj.hashMap.samp.age);
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status 
						+ "\nMessage : " + request.responseText + "\nError : " + errorData);
			}
		}); //ajax
	});//test4 click
	
	//테스트 5 버튼을 클릭하면, json 객체를 서버 컨트롤러로 보내기
	$("#test5").on("click", function(){
		//자바스크립트 또는 제이쿼리에서 객체 만들기
		var job = new Object();
		job.name = "강감찬"; //job.name = $("#name").val();
		job.age = 33; //job.age = $("#age").val();
		//var job = {name: "강감찬", age: 33};
		
		$.ajax({
			url: "test5.do",
			type: "post",
			data: JSON.stringify(job),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				alert("전송 성공 : " + result);
				
				$("#d5").html("전송한 json 객체 값 : " + job.name + ", " + job.age);
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status 
						+ "\nMessage : " + request.responseText + "\nError : " + errorData);
			}
		});
	});//test5 click
	
	//테스트 6 버튼을 클릭하면, json 배열 보내기
	$("#test6").on("click", function(){
		//var jarr = new Array(5); //index 사용할 수 있음
		//jarr[i] = {name: "홍길동", age: 25};
		
		//var jarr = new Array(); //Stack 구조가 됨. index 가 없음
		//저장 : push(), 꺼내기 : pop() 사용함
		//jarr.push({name: "홍길동", age: 25});
		
		//배열 초기화
		var jarr = [{name: "홍길동", age: 25}, {name: "황진이", age: 22}, {name: "김춘추", age: 38}];
		
		$.ajax({
			url: "test6.do",
			type: "post",
			data: JSON.stringify(jarr),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				alert("전송 성공 : " + result);
				
				var values = $("#d6").html();
				for(var i in jarr){
					values += i + "번째 이름 : " + jarr[i].name + ", 나이 : " + jarr[i].age + "<br>";
				}
				
				$("#d6").html(values);
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status 
						+ "\nMessage : " + request.responseText + "\nError : " + errorData);
			}
		});
	});//test6 click
	
}); //document.ready
</script>
</head>
<body>
	<c:import url="/WEB-INF/views/common/header.jsp"/>
	<hr>
	<h1>spring second : Ajax test page</h1>
	<ol>
		<li>서버쪽 컨트롤러 메소드로 값 보내기 <button id="test1">테스트 1</button></li>
		이름 입력 : <input type="text" id="name"><br>
		나이 입력 : <input type="number" id="age"><br>
		<p><div id="d1"></div></p>
		<li>컨트롤러에서 보낸 json 객체 받아서 출력하기 <button id="test2">테스트 2</button></li>
		<p><div id="d2"></div></p>
		<li>컨트롤러에서 보낸 json 배열 받아서 출력하기 <button id="test3">테스트 3</button></li>
		<p><div id="d3"></div></p>
		<li>컨트롤러에서 보낸 Map 객체 받아서 출력하기 <button id="test4">테스트 4</button></li>
		<p><div id="d4"></div></p>
		<li>컨트롤러로 json 객체 보내기 <button id="test5">테스트 5</button></li>
		<p><div id="d5"></div></p>
		<li>컨트롤러로 json 배열 보내기 <button id="test6">테스트 6</button></li>
		<p><div id="d6"></div></p>
	</ol>
	<hr>
	<c:import url="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>