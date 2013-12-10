<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Administrator</title>
</head>
<body>
	<fieldset>
        <legend>
            <small>Create an administrator</small>
        </legend>
		<form id="registrationForm" action="${ctx}/manage/admins/create"
			method="post" class="form-horizontal">
			<div class="control-group">
				<label for="username" class="control-label">Admin Name:</label>
				<div class="controls">
					<input type="text" id="username" name="username"
						class="input-large required" minLength=5/>
				</div>
			</div>
			<div class="control-group">
				<label for="email" class="control-label">Email:</label>
				<div class="controls">
					<input type="email" id="email" name="email"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">Password:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword"
						class="input-large required" minLength="6"/>
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">Confirm Password:</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="input-large required" equalTo="#plainPassword"/>
				</div>
			</div>

			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="Create" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="Cancel" onclick="history.back()"/>
			</div>
		</form>
	</fieldset>
    <script>
    $(document).ready(function(){
    	$("#username").focus();
    	$("#registrationForm").validate();
    });
    </script>
</body>
</html>