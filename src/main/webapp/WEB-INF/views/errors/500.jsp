<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="error.500.title"/></title>
</head>
<body>
    <h1><spring:message code="error.500.message"/></h1>
</body>
</html>