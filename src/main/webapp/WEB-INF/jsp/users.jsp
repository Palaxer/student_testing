<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.title.users" /></title>
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
                <h1 class="sub-header uppercase"><fmt:message key="text.sub-title.all-users" /></h1>
                <c:if test="${not empty notFound}">
                    <div class="alert alert-danger capitalize">
                        <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.not-found" />
                    </div>
                </c:if>
                <c:if test="${not empty deleteSuccess}">
                    <div class="alert alert-success capitalize">
                        <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.delete-user" />
                    </div>
                </c:if>
                <form action="<c:url value="/?command=users"/>" method="post">
                    <div class="radio">
                        <label class="uppercase">
                            <c:choose>
                                <c:when test="${role == 'all'}">
                                    <input checked type="radio" onchange="this.form.submit()" name="role" value="all">
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" onchange="this.form.submit()" name="role" value="all">
                                </c:otherwise>
                            </c:choose>
                            <fmt:message key="text.all"/>
                        </label>
                        <label class="uppercase">
                            <c:choose>
                                <c:when test="${role == 'admin'}">
                                    <input checked type="radio" onchange="this.form.submit()" name="role" value="admin">
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" onchange="this.form.submit()" name="role" value="admin">
                                </c:otherwise>
                            </c:choose>
                            <fmt:message key="text.admin"/>
                        </label>
                        <label class="uppercase">
                            <c:choose>
                                <c:when test="${role == 'tutor'}">
                                    <input checked type="radio" onchange="this.form.submit()" name="role" value="tutor">
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" onchange="this.form.submit()" name="role" value="tutor">
                                </c:otherwise>
                            </c:choose>
                            <fmt:message key="text.tutor"/>
                        </label>
                        <label class="uppercase">
                            <c:choose>
                                <c:when test="${role == 'student'}">
                                    <input checked type="radio" onchange="this.form.submit()" name="role" value="student">
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" onchange="this.form.submit()" name="role" value="student">
                                </c:otherwise>
                            </c:choose>
                            <fmt:message key="text.student"/>
                        </label>
                    </div>
                </form>
                <form action="<c:url value="/?command=find-user"/>" method="post">
                    <div class="input-group search">
                        <input type="hidden" name="currentPage" value="${currentPage}">
                        <input type="text" name="login" class="form-control" placeholder="<fmt:message key="text.login" />">
                        <span class="input-group-btn">
                            <button type="submit" class="btn btn-primary uppercase"><fmt:message key="text.search" /></button>
                        </span>
                    </div>
                </form>
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="uppercase">
                            <tr>
                                <th><fmt:message key="text.id" /></th>
                                <th><fmt:message key="text.login" /></th>
                                <th><fmt:message key="text.name" /></th>
                                <th><fmt:message key="text.surname" /></th>
                                <th><fmt:message key="text.role" /></th>
                                <th><fmt:message key="text.info" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td><c:out value="${user.id}"/></td>
                                    <td><c:out value="${user.login}"/></td>
                                    <td><c:out value="${user.name}"/></td>
                                    <td><c:out value="${user.surname}"/></td>
                                    <td><c:out value="${user.role.name()}"/></td>
                                    <td>
                                        <a class="btn btn-info btn-xs uppercase" href="<c:url value="/?command=user-info&id=${user.id}"/>">
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
                                <li class="active"><a href="<c:url value="/?command=users&role=${role}&page=${i}"/>">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="<c:url value="/?command=users&role=${role}&page=${i}"/>">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</body>

</html>
