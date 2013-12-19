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
			<small>Registration Form(all fields required)</small>
		</legend>
		<form id="registrationForm" action="${ctx}/register" method="post"
			class="form-horizontal">
        <%@ include file="/WEB-INF/views/reusables/registerAccountForm.jsp" %>			
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
						remote : "Username occupied"
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