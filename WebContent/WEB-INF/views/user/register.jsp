<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Page</title>
</head>
<body>
	<fieldset>
		<legend>
			<small>User Registration(all fields required)</small>
		</legend>
		<form id="registrationForm" action="${ctx}/register" method="post"
			class="form-horizontal">

			<div class="control-group">
				<label for="username" class="control-label">Username: </label>
				<div class="controls">
					<input type="text" id="username" name="username" minLength="5"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="email" class="control-label">Email: </label>
				<div class="controls">
					<input type="email" id="email" name="email"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">Password: </label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword"
						minLength="6" class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">Confirm
					Password: </label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="input-large required" equalTo="#plainPassword" />
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="Register" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="Cancel" onclick="history.back()" />
			</div>
		</form>
	</fieldset>
	<script>
		$(document).ready(function() {
			$("#username").focus();
			$("#registrationForm").validate({
				rules : {
					username : {
						remote : "${ctx}/register/checkUsername"
					},
					email : {
						remote : "${ctx}/register/checkEmail"
					}
				},
				messages : {
					username : {
						remote : "Uername occupied"
					},
					email : {
						remote : "Email occupied"
					}
				}
			});
		});
	</script>
</body>
</html>