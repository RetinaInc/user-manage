<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profile</title>
</head>
<body>
	<fieldset>
		<legend>
			<small>Your Profile</small>
		</legend>
		<div class="control-group">
			Username: <c:out value = "${user.username}"/>
		</div>
		<div class="control-group">
			Email Address: <c:out value = "${user.email}"/>
		</div>
		<div class="control-group">
			<form action="${ctx}/logout">
				<input id="submit_btn" class="btn" type="submit" value="Logout">
			</form>
		</div>
	</fieldset>
</body>
</html>