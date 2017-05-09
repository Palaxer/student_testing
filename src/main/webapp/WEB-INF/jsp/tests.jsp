<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.tests" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
</head>

<body>
    <!-- HEADER -->
    <jsp:include page="template/header.jsp"/>

    <!-- MAIN -->
    <div class="container-fluid">
        <div class="row">
            <!-- SIDEBAR -->
            <jsp:include page="template/sidebar.jsp"/>

            <!-- CONTENT -->
            <div class="col-xs-10 col-xs-offset-2 main">
                <div class="row">
                    <div class="col-md-3">
                        <h2 class="sub-header uppercase"><fmt:message key="text.category" /></h2>

                        <div class="list">
                            <c:choose>
                                <c:when test="${'all' == selectCategory}">
                                    <a href="<c:url value="/?command=tests&category=all"/>" class="list-group-item active uppercase"><fmt:message key="text.all" /></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="/?command=tests&category=all"/>" class="list-group-item uppercase"><fmt:message key="text.all" /></a>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach items="${categories}" var="category">
                                <c:choose>
                                    <c:when test="${category.name == selectCategory}">
                                        <a href="<c:url value="/?command=tests&category=${category.name}"/>" class="list-group-item active uppercase"><c:out value="${category.name}"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value="/?command=tests&category=${category.name}"/>" class="list-group-item uppercase"><c:out value="${category.name}"/></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <h2 class="sub-header uppercase"><fmt:message key="text.tests" /></h2>
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="uppercase">
                                <tr>
                                    <th><fmt:message key="text.name" /></th>
                                    <th><fmt:message key="text.tutor" /></th>
                                    <th><fmt:message key="text.category" /></th>
                                    <th><fmt:message key="text.pass-score" /></th>
                                    <th><fmt:message key="text.elapsed-time-pass" /></th>
                                    <th><fmt:message key="text.info" /></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tests}" var="test">
                                    <tr>
                                        <td><c:out value="${test.name}"/></td>
                                        <td><c:out value="${test.tutor.login}"/></td>
                                        <td><c:out value="${test.category.name}"/></td>
                                        <td><c:out value="${test.passedScore}"/></td>
                                        <td><c:out value="${test.passedTime}"/></td>
                                        <td>
                                            <a class="btn btn-primary btn-xs uppercase" href="<c:url value="/?command=test-info&id=${test.id}"/>">
                                                <fmt:message key="text.info" />
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <ul class="pagination">
                            <c:forEach begin="1" end="${pageNumber}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage == i}">
                                        <li class="active"><a href="<c:url value="/?command=tests&category=${selectCategory}&page=${i}"/>">${i}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="<c:url value="/?command=tests&category=${selectCategory}&page=${i}"/>">${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
