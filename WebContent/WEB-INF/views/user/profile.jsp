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
				<td><c:out value="${user.username}" /></td>
			</tr>
			<tr>
				<td>Email Address</td>
				<td><c:out value="${user.email}" /></td>
			</tr>
			<tr>
				<td>Roles</td>
				<td>
				<c:forEach var="role" items="${user.roles}">
				    ${role}<br>
				</c:forEach>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>