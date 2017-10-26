<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale"/>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <a class="navbar-brand" href="/index"><fmt:message key="navbar.title"/></a>
    </div>
    <!-- /.navbar-header -->
    <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-language fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li>
                    <a href="${requestScope['javax.servlet.forward.request_uri']}?
<c:if test="${!empty action}">action=${action}&</c:if>
language=ru&
<c:if test="${!empty id}">id=${id}&</c:if>
<c:if test="${!empty searchRequest}">searchRequest=${searchRequest}&</c:if>
<c:if test="${!empty currentPage}">page=${currentPage}</c:if>">
<i class="${language != 'en' ? 'fa fa-check-circle fa-fw' : ''}"></i>Русский</a>
                </li>
                <li>
                    <a href="${requestScope['javax.servlet.forward.request_uri']}?
<c:if test="${!empty action}">action=${action}&</c:if>
language=en&
<c:if test="${!empty id}">id=${id}&</c:if>
<c:if test="${!empty searchRequest}">searchRequest=${searchRequest}&</c:if>
<c:if test="${!empty currentPage}">page=${currentPage}</c:if>">
<i class="${language == 'en' ? 'fa fa-check-circle fa-fw' : ''}"></i>English</a>
                </li>
            </ul>
        </li>
        <!-- /.dropdown -->
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <c:if test="${empty sessionScope.hasRole}">
                    <li><a href="/login"><i class="fa fa-user fa-fw"></i> <fmt:message key="index.login"/></a></li>
                    <li><a href="/register"><i class="fa fa-user fa-fw"></i> <fmt:message key="index.register"/></a>
                    </li>
                </c:if>
                <c:if test="${not empty sessionScope.hasRole}">
                    <li><a href="/logout"><i class="fa fa-user fa-fw"></i><fmt:message key="index.logout"/></a></li>
                </c:if>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>

</nav>