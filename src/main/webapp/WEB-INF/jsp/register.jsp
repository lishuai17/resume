<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath }/static/js/table/tableViewer.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/table/tableViewer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/user.js"></script>
	<title>sign in</title>
	<script>
	
	
	var list=new QueryList("line_list","checkbox");
	
	$(function(){
		user.baseUrl = "${pageContext.request.contextPath }/";
		list.header=new Array("nickname","email","password","locked");
		
		list.fill=false;
	    list.getData = function (){
	    	user.list(fillTable);
		
		}; 
		list.createTable();
		list.getData();
		
		
	});
	
	function fillTable(datas){
		list.callback(datas);
	}
	
	
	
	function fun_submit(){
		
		var email = $("#email").val() ;
		var pwd = $("#pwd").val() ;
		var repwd = $("#repwd").val() ;
		if(pwd != repwd){
			return;
		}
		
		//user.register(email, pwd);
	}
	function fun_reset(){
		document.f.reset();
	}
	
	document.onkeypress=function(e){
		var code;
		if(!e){
			var e=window.event;
		}
		if(e.keyCode){
			code=e.keyCode;
		}
		else if(e.which){
			code=e.which;
		}
		if(code==13){
			fun_submit();
		}
	}
	
	</script>
</head>
<body onload='document.f.email.focus();'>
	<form  name="f">
		<input type="email" name="email" placeholder="输入邮箱" />
		<input type="password" name="password" id="pwd"/>
		<input type="password" name="repassword" id="repwd"/>
		<button onclick="fun_submit();"></button>
	</form>
	<div id="line_list" style="clear: both;"></div>
</body>
</html>