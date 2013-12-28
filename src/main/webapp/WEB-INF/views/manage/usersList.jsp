<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="page.userList.title"/></title>
</head>
<body>
	<c:if test="${not empty message}">
		<div class="alert alert-success alert-dismissable">
			${message}
			<button class="close" data-dismiss="alert">x</button>
		</div>
	</c:if>
	<shiro:hasRole name="super admin">
		<form action="${ctx}/manage/users/create">
			<input id="submit_btn" class="btn" type="submit" value=<spring:message code="button.addAdmin"/>>
		</form>
	</shiro:hasRole>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="sortable"><spring:message code="label.id"/></th>
				<th class="sortable"><spring:message code="label.username"/></th>
				<th class="sortable"><spring:message code="label.email"/></th>
				<th class="sortable"><spring:message code="label.roles"/></th>
				<th class="sortable"><spring:message code="label.birthdayNoFormat"/></th>
				<th><spring:message code="label.age"/></th>
				<th id="registerDate"><spring:message code="label.registerDate"/></th>
				<shiro:hasRole name="super admin">
					<th><spring:message code="label.manage"/></th>
				</shiro:hasRole>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.username }</td>
					<td>${user.email}</td>
					<td><c:forEach items="${user.roles}" var="role">
					       ${role}<br>
						</c:forEach></td>
					<td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></td>
					<td>${user.age}</td>
					<td><fmt:formatDate value="${user.registerDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
					<shiro:hasRole name="super admin">
						<td><a href="${ctx}/manage/users/edit/${user.id}"><spring:message code="label.edit"/></a> /
							<a href="${ctx}/manage/users/delete/${user.id}"><spring:message code="label.delete"/></a></td>
					</shiro:hasRole>
				</tr>
			</c:forEach>
		</tbody>
	</table>

    <%-- 
	<tags:pagination paginationSize="5" page="${users}"></tags:pagination>

	<script type="text/javascript">
		$(".sortable").click(
				function() {
					var sortColumn = $(this).text().toLowerCase();
					var page = "${users.getNumber()+1}";
					var direction = "${sortDirection}";
					if (sortColumn === "${sortColumn}") {
						if (direction === "ASC") direction = "DESC";
						else direction = "ASC";
					}
						
					window.location.href = "?page=" + page
							+ "&sortColumn=" + sortColumn + "&sortDirection=" + direction;
				});
	</script>
	 --%>
</body>
</html>