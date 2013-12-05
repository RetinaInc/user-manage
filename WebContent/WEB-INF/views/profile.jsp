<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		<div>
			<label for="username" class="control-label">Username: </label>
			<div class="control" id="username">
			<c:out value = "${user.username}"/>
			</div>
		</div>
		<div>
			<label for="email" class="control-label">Email Address: </label>
			<div class="control" id="email">
			<c:out value = "${user.email}"/>
			</div>
		</div>
		<div>
			<form action="${ctx}/logout">
				<input type="submit" value="Logout">
			</form>
		</div>

	</fieldset>
</body>
</html>