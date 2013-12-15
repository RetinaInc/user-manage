<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>Manage Administrators</title>
</head>
<body>
	<c:if test="${not empty message}">
		<div class="alert alert-success alert-dismissable">
			${message}
			<button class="close" data-dismiss="alert">x</button>
		</div>
	</c:if>
	<form action="${ctx}/manage/admins/create">
		<input id="submit_btn" class="btn" type="submit" value="Add Admin">
	</form>

	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>Id</th>
				<th>Username</th>
				<th>Email</th>
				<th>Roles</th>
				<th>Register Date</th>
				<th>Admin</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${admins}" var="admin">
				<tr>
					<td>${admin.id}</td>
					<td>${admin.username}</td>
					<td>${admin.email}</td>
					<td>${admin.roles}</td>
					<td><fmt:formatDate value="${admin.registerDate}"
							pattern="yyyy-MM-dd  HH:mm:ss" /></td>
					<td><a href="${ctx}/manage/admins/edit/${admin.id}">edit</a> / <a
						href="${ctx}/manage/admins/delete/${admin.id}">delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>