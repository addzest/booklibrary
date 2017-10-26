<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="locale" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/metisMenu.min.css" rel="stylesheet">
    <link href="/css/sb-admin-2.css" rel="stylesheet">
    <link href="/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <title>Register</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><fmt:message key="register.panel.title"/></h3>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="register">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="<fmt:message key="register.label.username"/>"
                                       name="username" type="text" autofocus required>
                                <c:if test="${not empty userExistError}">
                                    <label><fmt:message key="register.label.existError"/></label>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="<fmt:message key="register.label.password"/>"
                                       name="password" type="password" value="${userTO.password}" required>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="<fmt:message key="register.label.firstname"/>"
                                       name="firstname" type="text" value="${userTO.firstName}" required>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="<fmt:message key="register.label.lastnanme"/>"
                                       name="lastname" type="text" value="${userTO.lastName}" required>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="<fmt:message key="register.label.email"/>"
                                       name="email" type="email" value="${userTO.email}" required>
                            </div>
                            <fmt:message key="register.button.submit" var="buttonValue"/>
                            <input type="submit" class="btn btn-lg btn-success btn-block" value="${buttonValue}"/>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/js/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/js/sb-admin-2.js"></script>
</body>
</html>
