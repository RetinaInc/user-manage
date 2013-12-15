<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />	

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Yigwoo: <sitemesh:title/></title>
<link href="${ctx}/static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/static/styles/decorators.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/static/jquery/validate.css" type="text/css" rel="stylesheet"/>
<script  src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/extend.js" type="text/javascript"></script>

<sitemesh:head/>
</head>
<body>
    <div class="container">
        <%@ include file="/WEB-INF/layouts/header.jsp" %>
        <div id="content">
            <sitemesh:body/>
        </div>
        <%@ include file="/WEB-INF/layouts/footer.jsp" %>
    </div>
    <script src="${ctx}/static/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>