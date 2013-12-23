<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All Users List</title>
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
			<input id="submit_btn" class="btn" type="submit" value="Add Admin">
		</form>
	</shiro:hasRole>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="sortable">Id</th>
				<th class="sortable">Username</th>
				<th class="sortable">Email</th>
				<th>Roles</th>
				<th id="registerDate">Register Date</th>
				<shiro:hasRole name="super admin">
					<th>Admin</th>
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
					<td><fmt:formatDate value="${user.registerDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
					<shiro:hasRole name="super admin">
						<td><a href="${ctx}/manage/users/edit/${user.id}">edit</a> /
							<a href="${ctx}/manage/users/delete/${user.id}">delete</a></td>
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