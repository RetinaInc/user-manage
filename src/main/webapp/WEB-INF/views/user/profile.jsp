<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profile</title>
</head>
<body>
	<c:if test="${not empty message}">
		<div class="alert alert-success alert-dismissable">
			${message}
			<button class="close" data-dismiss="alert">x</button>
		</div>
	</c:if>
	<div class="panel panel-default">
		<!-- Default panel contents -->
		<!-- Table -->
		<table class="table">
			<tr>
				<td>Username</td>
				<td><c:out value="${account.username}" /></td>
			</tr>
			<tr>
				<td>Email Address</td>
				<td><c:out value="${account.email}" /></td>
			</tr>
			<tr>
				<td>Roles</td>
				<td>
				<c:forEach var="role" items="${account.roles}">
				    ${role.rolename}<br>
				</c:forEach>
				</td>
			</tr>
			<tr>
			 <td>Birthday</td>
			 <td><c:out value="${account.birthday}"></c:out>
			</tr>
			<tr>
			 <td>Age</td>
			 <td><c:out value="${account.age}"></c:out>
			</tr>
		</table>
	</div>
</body>
</html>