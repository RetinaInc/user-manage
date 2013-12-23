<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="header">
	<div id="title"> <h1>A Login/Register Demo Web Application</h1> </div>
	<shiro:user>
		<ul class="nav nav-pills nav-justified">
			<li><a href="${ctx}/profile"><shiro:principal />'s Profile</a></li>
			<li><a href="${ctx}/profile/edit">Edit Profile</a></li>
			<shiro:hasAnyRoles name="admin, super admin">
				<li><a href="${ctx}/manage/users">Manage Users</a></li>
			</shiro:hasAnyRoles>
			<li><a href="${ctx}/logout">Logout</a></li>
		</ul>
	</shiro:user>

</div>