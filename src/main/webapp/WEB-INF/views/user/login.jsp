<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="page.login.title" /></title>
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
				<spring:message code="error.loginFailure"/>
				<button class="close" data-dismiss="alert">x</button>
			</div>
			<%
				}
			%>
			<div class="control-group">
				<label for="username" class="control-label"><spring:message
						code="label.username" /></label>
				<div class="controls">
					<input type="text" id="username" name="username" minLength="5"
						value="${username }" class="input-medium required" required />
				</div>
			</div>
			<div class="control-group">
				<label for="password" class="control-label"><spring:message
						code="label.password" /></label>
				<div class="controls">
					<input type="password" id="password" name="password" minLength="6"
						class="input-medium required" required />
				</div>
			</div>

			<div class="control-group">
				<div class="controls">
					<label class="checkbox" for="rememberMe"><input
						type="checkbox" id="rememberMe" name="rememberMe" />
					<spring:message code="label.rememberMe" /></label> <input id="submit_btn"
						class="btn btn-primary" type="submit"
						value=<spring:message code="button.login"/> /> <a class="btn"
						href="${ctx}/register"><spring:message code="button.register" /></a>
				</div>
			</div>
		</form>
	</shiro:guest>
	<shiro:user>
		<c:redirect url="/" />
	</shiro:user>
	<script>
		$(document).ready(function() {
			$("#username").focus();
			$("#loginForm").validate();
		});
	</script>
</body>
</html>