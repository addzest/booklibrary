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
    <title>Login</title>
</head>
<body>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <a class="navbar-brand" href="#"><fmt:message key="navbar.title"/></a>
    </div>
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-language fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="login?language=ru"><i class="${language != 'en' ? 'fa fa-check-circle fa-fw' : ''}"></i>Русский</a>
                </li>
                <li><a href="login?language=en"><i class="${language == 'en' ? 'fa fa-check-circle fa-fw' : ''}"></i>English</a>
                </li>
            </ul>
        </li>
    </ul>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><fmt:message key="login.panel.title"/></h3>
                </div>
                <div class="panel-body">
                    <form role="form" method="post" action="login">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="<fmt:message key="login.label.username"/>" name="username" type="text" autofocus>
                                <c:if test="${not empty loginError}">
                                    <label><fmt:message key="login.label.loginerror"/></label>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="<fmt:message key="login.label.password"/>" name="password" type="password" value="">
                            </div>
                            <fmt:message key="login.button.submit" var="buttonValue"/>
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
