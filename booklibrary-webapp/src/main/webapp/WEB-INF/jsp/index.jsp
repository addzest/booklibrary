<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="custom" uri="bookTags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap Core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <link href="/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- DataTables CSS -->
    <link href="/css/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="/css/dataTables.responsive.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/css/metisMenu.min.css" rel="stylesheet">


    <title>Library</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="page-wrapper">
        <h1 class="page-header"><fmt:message key="index.bookList"/></h1>
        <form method="get" action="index" role="form">
            <div class="row">
                <div class="col-lg-5">
                    <div class="form-group">
                        <input class="form-control" id="searchRequest" type="text" name="searchRequest" placeholder="<fmt:message key="index.searchForm.request"/>"
                               value="${searchRequest}" escapeXml="true"/>
                    </div>
                </div>
                <div class="col-lg-3">
                    <input type="submit" class="btn btn-primary"
                           value="<fmt:message key="index.button.searchBooks" />"/>
                </div>
            </div>
        </form>


        <div class="row">
            <c:if test="${!empty bookTOList}">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <tr>
                        <td><b><fmt:message key="index.bookList.title"/></b></td>
                        <td><b><fmt:message key="index.bookList.author"/></b></td>
                        <td><b><fmt:message key="index.bookList.publishYear"/></b></td>
                        <td><b><fmt:message key="index.bookList.description"/></b></td>
                        <td><b><fmt:message key="index.bookList.count"/></b></td>
                        <c:if test="${not empty sessionScope.hasRole}">
                            <td><b><fmt:message key="index.bookList.actions"/></b></td>
                        </c:if>
                    </tr>
                    <c:forEach items="${bookTOList}" var="bookTO">
                        <tr>
                            <td><c:out value="${bookTO.title}" escapeXml="true"/></td>
                            <td><c:out value="${bookTO.author}" escapeXml="true"/></td>
                            <td><c:out value="${bookTO.publishYear}" escapeXml="true"/></td>
                            <td><c:out value="${bookTO.description}" escapeXml="true"/></td>
                            <td><c:out value="${bookTO.count}" escapeXml="true"/></td>
                                <c:if test="${sessionScope.hasRole eq 'librarian'}">
                            <td>
                                    <a href="index?action=edit&id=<c:out value='${bookTO.id}' />"><fmt:message
                                            key="index.bookList.edit"/></a>
                                    <a href="index?action=delete&id=<c:out value='${bookTO.id}' />&page=<c:out value='${currentPage}'/>"><fmt:message
                                            key="index.bookList.delete"/></a>
                            </td>
                                </c:if>
                                <c:if test="${sessionScope.hasRole eq 'reader'}">
                                    <td>
                                        <c:if test="${bookTO.count>0}">
                                    <a href="index?action=takeBook&id=<c:out value='${bookTO.id}'/>&holdType=readingRoom&page=<c:out value='${currentPage}'/>"><fmt:message
                                            key="index.bookList.readingRoom"/></a>
                                    <a href="index?action=takeBook&id=<c:out value='${bookTO.id}'/>&holdType=subscription&page=<c:out value='${currentPage}'/>"><fmt:message
                                            key="index.bookList.subscription"/></a>
                                        </c:if>
                                    </td>
                                </c:if>

                        </tr>
                    </c:forEach>
                </table>

                <custom:listLength listLength="${requestScope.listLength}" currentPage="${requestScope.currentPage}"/>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                            <ul class="pagination">

                                <c:if test="${currentPage != 1}">
                                    <li class="paginate_button previous" aria-controls="dataTables-example"
                                        tabindex="0"
                                        id="dataTables-example_previous"><a
                                            href="index?action=listBooks&page=${currentPage - 1}"><fmt:message
                                            key="index.bookList.previous"/></a></li>

                                </c:if>
                                <c:forEach begin="1" end="${numberOfPages}" var="i">
                                    <c:choose>
                                        <c:when test="${currentPage eq i}">
                                            <li class="paginate_button active" aria-controls="dataTables-example" tabindex="0"><a href="#">${i}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="paginate_button " aria-controls="dataTables-example"
                                                tabindex="0"><a href="index?action=listBooks&page=${i}">${i}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                </li>
                                <c:if test="${currentPage lt numberOfPages}">
                                    <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0"
                                        id="dataTables-example_next"><a
                                            href="index?action=listBooks&page=${currentPage + 1}"><fmt:message
                                            key="index.bookList.next"/></a></li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        <c:if test="${sessionScope.hasRole eq 'librarian'}">
            </br>
            <a href="index?action=addBook" class="btn btn-primary"><fmt:message key="index.bookList.addBook"/></a>
        </c:if>
        <div class="row">
        <c:if test="${not empty sessionScope.hasRole}">
            <h1><fmt:message key="index.bookListOnHold"/></h1>
            <c:if test="${!empty booksOnHoldList}">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <tr>
                        <td><b><fmt:message key="index.bookList.title"/></b></td>
                        <td><b><fmt:message key="index.bookList.author"/></b></td>
                        <td><b><fmt:message key="index.bookList.publishYear"/></b></td>
                        <td><b><fmt:message key="index.bookList.description"/></b></td>
                        <c:if test="${sessionScope.hasRole eq 'librarian'}">
                            <td><b><fmt:message key="index.bookListOnHold.user"/></b></td>
                        </c:if>
                        <td><b><fmt:message key="index.bookList.holdType"/></b></td>
                        <td><b><fmt:message key="index.bookList.approval"/></b></td>
                        <td><b><fmt:message key="index.bookList.actions"/></b></td>
                    </tr>
                    <c:forEach items="${booksOnHoldList}" var="bookTO">
                        <tr>
                            <td><c:out value="${bookTO.title}" escapeXml="true"/></td>
                            <td><c:out value="${bookTO.author}" escapeXml="true"/></td>
                            <td><c:out value="${bookTO.publishYear}" escapeXml="true"/></td>
                            <td><c:out value="${bookTO.description}" escapeXml="true"/></td>
                            <c:if test="${sessionScope.hasRole eq 'librarian'}">
                            <td>${bookTO.userId}</td>
                            </c:if>
                            <td><c:if test="${bookTO.holdType eq 'subscription'}">
                                <fmt:message key="index.bookListOnHold.subscription"/>
                                </c:if>
                                <c:if test="${bookTO.holdType eq 'readingRoom'}">
                                    <fmt:message key="index.bookListOnHold.readingRoom"/>
                                </c:if>
                            </td>
                            <td><c:if test="${bookTO.approved}">
                                <fmt:message key="index.bookListOnHold.approved"/>
                            </c:if>
                                <c:if test="${!bookTO.approved}">
                                    <fmt:message key="index.bookListOnHold.notApproved"/>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${sessionScope.hasRole eq 'reader'}">
                                    <a href="index?action=returnBook&id=<c:out value='${bookTO.id}'/>&operationId=<c:out value='${bookTO.operationId}'/>&page=<c:out value='${currentPage}'/>"><fmt:message
                                            key="index.bookList.returnBook"/></a>
                                </c:if>

                                <c:if test="${sessionScope.hasRole eq 'librarian' && not bookTO.approved}">
                                    <a href="index?action=approveBook&operationId=<c:out value='${bookTO.operationId}'/>&page=<c:out value='${currentPage}'/>"><fmt:message
                                            key="index.bookList.approve"/></a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <custom:listLength listLength="${requestScope.onHoldListLength}"/>
            </c:if>
        </c:if>
        </div>

    </div>
</div>
<script src="/js/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/js/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="/js/sb-admin-2.js"></script>

<c:if test="${requestScope.deleteError eq 'deleteError'}">
<script>
    $(document).ready(function(){
    alert('<fmt:message key="index.alert.onHold"/>');
    });
</script>
</c:if>
</body>
</html>
