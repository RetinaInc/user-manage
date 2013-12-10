<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<script>
	$().ready(function() {
		$("#username").focus();
		$("#loginForm").validate({
			errorElement : "span",
			rules : {
				username : {
					required : true,
					minlength : 5,
				},
				password : {
					required : true,
					minlength : 6
				}
			},
			messages : {
				username : {
					required : "Please enter your username",
					minlength : "Username must be at least 5 characters"
				},
				password : {
					required : "Please enter your password",
					minlength : "Password must be at least 6 characters"
				}
			}
		});
	});
</script>
</head>
<body>
	<shiro:guest>
		<form id="loginForm" action="${ctx}/login" method="post"
			class="form-horizontal">
			<%
				String error = (String) request
							.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
					if (error != null) {
			%>
			<div class="alert alert-error alert-dismissable">
				Login failure, try again.
				<button class="close" data-dismiss="alert">x</button>
			</div>
			<%
				}
			%>
			<div class="control-group">
				<label for="username" class="control-label">Username: </label>
				<div class="controls">
					<input type="text" id="username" name="username"
						value="${username }" class="input-medium required" />
				</div>
			</div>
			<div class="control-group">
				<label for="password" class="control-label">Password: </label>
				<div class="controls">
					<input type="password" id="password" name="password"
						class="input-medium required" />
				</div>
			</div>

			<div class="control-group">
				<div class="controls">
					<label class="checkbox" for="rememberMe"><input
						type="checkbox" id="rememberMe" name="rememberMe" />Remember Me</label> <input
						id="submit_btn" class="btn btn-primary" type="submit"
						value="Login" /> <a class="btn" href="${ctx}/register">Register</a>
				</div>
			</div>
		</form>
	</shiro:guest>
	<shiro:user>
		<c:redirect url="/" />
	</shiro:user>
</body>
</html>