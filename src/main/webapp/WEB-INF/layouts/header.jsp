<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="header">
	<div id="title"> <h1><spring:message code="header.banner"/></h1> </div>
	<shiro:user>
		<ul class="nav nav-pills nav-justified">
			<li><a href="${ctx}/profile"><shiro:principal /><spring:message code="header.label.profile"/></a></li>
			<li><a href="${ctx}/profile/edit"><spring:message code="header.label.editProfile"/></a></li>
			<shiro:hasAnyRoles name="admin, super admin">
				<li><a href="${ctx}/manage/users"><spring:message code="header.label.manageUsers"/></a></li>
			</shiro:hasAnyRoles>
			<li><a href="${ctx}/logout"><spring:message code="header.label.logout"/></a></li>
		</ul>
	</shiro:user>

</div>