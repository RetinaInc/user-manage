<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Page</title>
</head>
<body>
	<form id="inputForm" action="${ctx}/register" method="post"
		class="form-horizontal">
		<fieldset>
			<legend>
				<small>User Registration</small>
			</legend>
			<div class="control-group">
				<label for="username" class="control-label">Username: </label>
				<div class="control">
					<input type="text" id="username" name="username"
						class="input-large required" minlength="3" />
				</div>
			</div>
			<div class="control-group">
				<label for="email" class="control-label">Email: </label>
				<div class="control">
					<input type="text" id="email" name="email"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">Password: </label>
				<div class="control">
					<input type="password" id="plainPassword" name="plainPassword"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">Confirm
					Password: </label>
				<div class="control">
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="input-large required" equalTo="#plainPassword" />
				</div>
			</div>
			<div class="form-action">
				<input id="submit" class="btn btn-primary" type="submit"
					value="Register" />
			</div>
		</fieldset>
	</form>
</body>
</html>