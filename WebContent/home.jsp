<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.yigwoo.bean.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HOME</title>
</head>
<body>
<% User user = (User) session.getAttribute("user"); %>
<h1>Hi <%=user.getName() %></h1>
<strong>Email Address</strong>: <%=user.getEmail() %><br>
<br>
<form action="logout" method="post">
    <input type="submit" value="LOGOUT">  
</form>
</body>
</html>