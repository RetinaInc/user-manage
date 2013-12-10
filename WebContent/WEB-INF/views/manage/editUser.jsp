<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit User ${user.username}</title>
</head>
<body>
	<fieldset>
		<legend>
			<small>Edit Profile</small> <small>${user.username }</small>
		</legend>
		<form id="editForm" action="${ctx}/manage/users/edit" method="post"
			class="form-horizontal">
			<input type="hidden" id="username" name="username"
				value="${user.username}" />
			<div class="control-group">
				<label for="email" class="control-label"}">Email:</label>
				<div class="controls">
					<input type="email" id="email" name="email" value="${user.email}"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">Password:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword"
						minLength="6" class="input-large"
						placeholder="leave it blank, if not to change" />
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">Confirm
					Password:</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="input-large" equalTo="#plainPassword" />
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="Update" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="Cancel" onclick="history.back()" />
			</div>
		</form>
	</fieldset>
	<script>
		$(document).ready(function() {
			$("#email").focus();
			$("#editForm").validate({
				rules : {
					email : {
						remote : "${ctx}/manage/checkEmail/${user.id}"
					}
				},
				messages : {
					email : {
						remote : "Email occupied"
					}
				}
			});
		});
	</script>
</body>
</html>
