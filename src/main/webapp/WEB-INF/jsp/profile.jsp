<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.title.profile" /></title>
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
                    <div class="col-md-5">
                        <h2 class="sub-header uppercase"><fmt:message key="text.user-profile" /></h2>
                        <form method="post" action="<c:url value="/?command=update-profile"/>">
                            <c:if test="${not empty updateSuccess}">
                                <div class="alert alert-success capitalize">
                                    <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.update-profile" />
                                </div>
                            </c:if>
                            <c:if test="${not empty invalidUpdate}">
                                <div class="alert alert-danger capitalize">
                                    <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.update-profile" /><br>
                                </div>
                            </c:if>
                            <c:if test="${not empty invalidData}">
                                <div class="alert alert-danger capitalize">
                                    <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.alert.invalid-data" /><br>
                                    <c:if test="${not empty invalidData.invalidName}">
                                        -<fmt:message key="text.alert.invalid-name" />([A-z,А-я]{1,25})<br>
                                    </c:if>
                                    <c:if test="${not empty invalidData.invalidSurname}">
                                        -<fmt:message key="text.alert.invalid-surname" />([A-z,А-я]{1,30}-?[A-z,А-я]{1,25})<br>
                                    </c:if>
                                </div>
                            </c:if>
                            <div class="form-group ${invalidData.invalidName}  has-feedback">
                                <label class="control-label" for="name"><fmt:message key="text.name"/></label>
                                <input class="form-control" type="text" id="name" name="name" value="<c:out value="${sessionScope.user.name}"/>">
                            </div>
                            <div class="form-group ${invalidData.invalidSurname} has-feedback">
                                <label class="control-label" for="name"><fmt:message key="text.surname"/></label>
                                <input class="form-control" type="text" id="surname" name="surname" value="<c:out value="${sessionScope.user.surname}"/>">
                            </div>
                            <br>
                            <button type="submit" class="btn btn-lg btn-primary btn-block uppercase">
                                <fmt:message key="text.button.update-profile"/>
                            </button>
                        </form>
                    </div>
                    <div class="col-md-5">
                        <h2 class="sub-header uppercase"><fmt:message key="text.change-passwd" /></h2>
                        <form method="post" action="<c:url value="/?command=change-password"/>">
                            <c:if test="${not empty updateSuccessPasswd}">
                                <div class="alert alert-success capitalize">
                                    <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.change-passwd" />
                                </div>
                            </c:if>
                            <c:if test="${not empty invalidUpdatePasswd}">
                                <div class="alert alert-danger capitalize">
                                    <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.change-passwd" /><br>
                                </div>
                            </c:if>
                            <c:if test="${not empty invalidDataPasswd}">
                                <div class="alert alert-danger capitalize">
                                    <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.alert.invalid-data" /><br>
                                    <c:if test="${not empty invalidDataPasswd.invalidPasswd}">
                                        -<fmt:message key="text.alert.invalid-passwd" />([!-~]{4,15})<br>
                                    </c:if>
                                    <c:if test="${not empty invalidDataPasswd.invalidConfirmPasswd}">
                                        -<fmt:message key="text.alert.invalid-confirm-passwd" /><br>
                                    </c:if>
                                </div>
                            </c:if>
                            <div class="form-group ${invalidDataPasswd.invalidPasswd} has-feedback">
                                <label class="control-label" for="passwd"><fmt:message key="text.passwd"/></label>
                                <input class="form-control" type="password" id="passwd" name="passwd">
                            </div>
                            <div class="form-group ${invalidDataPasswd.invalidConfirmPasswd} has-feedback">
                                <label class="control-label" for="confirm-passwd"><fmt:message key="text.confirm-passwd"/></label>
                                <input class="form-control" type="password" id="confirm-passwd" name="confirmPasswd">
                            </div>
                            <br>
                            <button type="submit" class="btn btn-lg btn-primary btn-block uppercase">
                                <fmt:message key="text.button.change-passwd"/>
                            </button>
                        </form>
                    </div>
                    <div class="col-md-2">
                        <h2 class="sub-header uppercase"><fmt:message key="text.lang"/></h2>
                        <a class="uppercase" href="<c:url value="/?command=redirect-profile&language=en"/>">
                            <fmt:message key="text.lang.eng"/>
                        </a>
                        <br>
                        <a class="uppercase" href="<c:url value="/?command=redirect-profile&language=ru"/>">
                            <fmt:message key="text.lang.ru"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
