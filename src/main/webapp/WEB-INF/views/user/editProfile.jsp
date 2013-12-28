<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="page.editProfile.title"/></title>
</head>
<body>
	<fieldset>
		<legend>
			<small><spring:message code="page.editProfile.formTitleHead"/></small>
			<small>${account.username}</small>
			<small><spring:message code="page.editProfile.formTitleTail"/></small>
		</legend>
		<form id="editForm" action="${ctx}/profile/edit" method="post" modelAttribute="account"
			class="form-horizontal">
			<%@ include file="/WEB-INF/views/reusables/editAccountForm.jsp"%>
		</form>
	</fieldset>
	<script>
		$(document).ready(function() {
			$("#email").focus();
			$("#editForm").validate({
				rules : {
					email : {
						remote : "${ctx}/profile/checkEmail"
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