<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="locale" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Not Found</title>
</head>
<body>
<h2 class="page-header"><fmt:message key="errorpage.header"/></h2>
<a href="index"><fmt:message key="errorpage.mainPageLink"/></a>
</body>
</html>
