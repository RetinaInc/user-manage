<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create User</title>
</head>
<body>
	<fieldset>
		<legend>
			<small>Create Admin</small>
		</legend>
		<form id="registrationForm" action="${ctx}/manage/users/create"
			method="post" class="form-horizontal">
			<!-- Work on later, Create a selection for super admin to choose 
			 which type of account he/she is gonna create-->
			<%@ include file="/WEB-INF/views/reusables/registerAccountForm.jsp"%>
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