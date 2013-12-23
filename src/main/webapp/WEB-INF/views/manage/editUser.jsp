<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit User ${account.username}</title>
</head>
<body>
	<fieldset>
		<legend>
			<small>Edit Profile</small> <small>${account.username }</small>
		</legend>
		<form id="editForm" action="${ctx}/manage/users/edit" method="post"
			class="form-horizontal">
			<%@ include file="/WEB-INF/views/reusables/editAccountForm.jsp" %>
		</form>
	</fieldset>
	<script>
		$(document).ready(function() {
			$("#email").focus();
			$("#editForm").validate({
				rules : {
					email : {
						remote : "${ctx}/manage/checkEmail/${account.id}"
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
