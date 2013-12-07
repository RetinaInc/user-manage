<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Page</title>
<script>
$().ready(function(){
    $("#username").focus();
    $("#registrationForm").validate({
    	errorElement: "span",
    	rules : {
    		username : {
    			required: true,
    			minlength: 5,
    			remote : "${ctx}/register/checkUsername"
    		},
    		email: {
    			required: true,
    			email: true,
    			remote : "${ctx}/register/checkEmail"
    		},
    		plainPassword : {
    			required: true,
    			minlength: 6
    		},
    		confirmPassword : {
    			required: true,
    			equalTo: "#plainPassword"
    		}
    	},
    	messages : {
    		username : {
    			required: "Please enter a username",
    			minlength: "Username must contain at least 5 characters",
    			remote : "Username occupied, please choose another username"
    		},
    	    email : {
    	    	email: "Please enter a valid email address",
    	    	remote : "Email occupied, please use another address"
    	    },
    		plainPassword: {
    			required: "Please enter your password",
    			minlength: "A password must contain at least 6 characters"
    		},
    		confirmPassword: {
    			required: "Please re-enter your password",
    			minlength: "A password must contain at least 6 characters",
    			equalTo: "Two passwords do not match"
    		}
    	}
    });
});
</script>
</head>
<body>
	<form id="registrationForm" action="${ctx}/register" method="post"
		class="form-horizontal">
		<fieldset>
			<legend>
				<small>User Registration(all fields required)</small>
			</legend>
			<div class="control-group">
				<label for="username" class="control-label">Username: </label>
				<div class="controls">
					<input type="text" id="username" name="username" 
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="email" class="control-label">Email: </label>
				<div class="controls">
					<input type="text" id="email" name="email"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">Password: </label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">Confirm
					Password: </label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="input-large required" />
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="Register" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="Cancel" onclick="history.back()" />
			</div>
		</fieldset>
	</form>
</body>
</html>