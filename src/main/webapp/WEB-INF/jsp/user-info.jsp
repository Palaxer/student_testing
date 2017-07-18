<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<%@ taglib prefix="t" uri="/WEB-INF/complete-time-format.tld"%>
<%@ page session="true"  isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.title.user-info" /></title>
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
                    <div class="col-md-4 btn-group">
                        <h2 class="sub-header uppercase"><fmt:message key="text.title.user-info" /></h2>
                        <c:if test="${not empty updateSuccess}">
                            <div class="alert alert-success capitalize">
                                <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.change-role" />
                            </div>
                        </c:if>
                        <c:if test="${not empty invalidUpdate}">
                            <div class="alert alert-danger capitalize">
                                <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.change-role" /><br>
                            </div>
                        </c:if>
                        <c:if test="${not empty deleteFailed}">
                            <div class="alert alert-danger capitalize">
                                <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.delete-user" /><br>
                            </div>
                        </c:if>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="id"><fmt:message key="text.id" /></label>
                            <input class="form-control" type="text" id="id" name="id" value="<c:out value="${user.id}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="login"><fmt:message key="text.login"/></label>
                            <input class="form-control" type="text" id="login" name="login" value="<c:out value="${user.login}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="name"><fmt:message key="text.name"/></label>
                            <input class="form-control" type="text" id="name" name="name" value="<c:out value="${user.name}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="name"><fmt:message key="text.surname"/></label>
                            <input class="form-control" type="text" id="surname" name="surname" value="<c:out value="${user.surname}"/>" disabled>
                        </div>

                        <c:if test="${user.id == sessionScope.user.id}">
                            <c:set var="disabled" scope="request" value="disabled"/>
                        </c:if>
                        <form method="post" action="<c:url value="/?command=change-role"/>">
                            <input type="hidden" name="id" value="${user.id}">
                            <div class="form-group has-feedback">
                                <label class="control-label center-block" for="role"><fmt:message key="text.role"/></label>
                                <select class="form-control" name="role" id="role" ${disabled}>
                                    <c:forEach items="${roles}" var="role">
                                        <c:choose>
                                            <c:when test="${user.role.name() == role.name()}">
                                                <option selected value="${role.name()}">${role.name()}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${role.name()}">${role.name()}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                            <br>
                            <button type="submit" class="btn btn-lg btn-primary uppercase pull-right" ${disabled}>
                                <fmt:message key="text.change-role"/>
                            </button>
                        </form>
                        <form method="post" action="<c:url value="/?command=delete-user"/>">
                            <input type="hidden" name="id" value="${user.id}">
                            <button type="submit" class="btn btn-lg btn-danger uppercase pull-left" ${disabled}>
                                <fmt:message key="text.delete"/>
                            </button>
                        </form>
                    </div>
                    <div class="col-md-8">
                        <h2 class="sub-header uppercase"><fmt:message key="text.test-history"/></h2>
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="uppercase">
                                    <tr>
                                        <th><fmt:message key="text.id"/></th>
                                        <th><fmt:message key="text.score"/></th>
                                        <th><fmt:message key="text.start-time"/></th>
                                        <th><fmt:message key="text.elapsed-time"/></th>
                                        <th><fmt:message key="text.pass"/></th>
                                        <th><fmt:message key="text.test-id"/></th>
                                        <th><fmt:message key="text.info"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${tests}" var="test">
                                        <tr>
                                            <td><c:out value="${test.id}"/></td>
                                            <td><c:out value="${test.score}"/></td>
                                            <td><d:dateFrm date="${test.startTime}" local="${language}"/></td>
                                            <td><t:timeFrm time="${test.elapsedTime}"/></td>
                                            <td><c:out value="${test.passed}"/></td>
                                            <td><c:out value="${test.test.id}"/></td>
                                            <td>
                                                <a class="btn btn-primary btn-xs uppercase" href="<c:url value="/?command=complete-test-info&id=${test.id}"/>">
                                                    <fmt:message key="text.info"/>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
