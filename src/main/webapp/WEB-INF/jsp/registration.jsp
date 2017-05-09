<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.title.registration" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
</head>

<body>
    <div class="container login-form">
        <div class="jumbotron">
            <h2 class="text-center capitalize"><fmt:message key="text.title.registration" /></h2>
            <form method="post" action="<c:url value="/?command=registration"/>">
                <c:if test="${not empty loginExist}">
                    <div class="alert alert-danger capitalize">
                        <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.alert.login-exist" />
                    </div>
                </c:if>
                <c:if test="${not empty invalidData}">
                    <div class="alert alert-danger capitalize">
                        <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.alert.invalid-data" /><br>
                        <c:if test="${not empty invalidData.invalidLogin}">
                            -<fmt:message key="text.alert.invalid-login" />([!-~]{3,10})<br>
                        </c:if>
                        <c:if test="${not empty invalidData.invalidPasswd}">
                            -<fmt:message key="text.alert.invalid-passwd" />([!-~]{4,15})<br>
                        </c:if>
                        <c:if test="${not empty invalidData.invalidConfirmPasswd}">
                            -<fmt:message key="text.alert.invalid-confirm-passwd" /><br>
                        </c:if>
                        <c:if test="${not empty invalidData.invalidName}">
                            -<fmt:message key="text.alert.invalid-name" />([A-z,А-я]{1,25})<br>
                        </c:if>
                        <c:if test="${not empty invalidData.invalidSurname}">
                            -<fmt:message key="text.alert.invalid-surname" />([A-z,А-я]{1,30}-?[A-z,А-я]{1,25})<br>
                        </c:if>
                    </div>
                </c:if>
                <div class="form-group ${invalidData.invalidLogin} has-feedback">
                    <label class="control-label" for="login"><fmt:message key="text.login"/></label>
                    <input class="form-control" type="text" id="login" name="login" value="<c:out value="${user.login}"/>">
                </div>
                <div class="form-group ${invalidData.invalidPasswd} has-feedback">
                    <label class="control-label" for="passwd"><fmt:message key="text.passwd"/></label>
                    <input class="form-control" type="password" id="passwd" name="passwd">
                </div>
                <div class="form-group ${invalidData.invalidConfirmPasswd} has-feedback">
                    <label class="control-label" for="confirm-passwd"><fmt:message key="text.confirm-passwd"/></label>
                    <input class="form-control" type="password" id="confirm-passwd" name="confirmPasswd">
                </div>
                <div class="form-group ${invalidData.invalidName} has-feedback">
                    <label class="control-label" for="name"><fmt:message key="text.name"/></label>
                    <input class="form-control" type="text" id="name" name="name" value="<c:out value="${user.name}"/>">
                </div>
                <div class="form-group ${invalidData.invalidSurname} has-feedback">
                    <label class="control-label" for="name"><fmt:message key="text.surname"/></label>
                    <input class="form-control" type="text" id="surname" name="surname" value="<c:out value="${user.surname}"/>">
                </div>
                <br>
                <button type="submit" class="btn btn-lg btn-primary btn-block uppercase">
                    <fmt:message key="text.button.registration"/>
                </button>
            </form>
        </div>
    </div>
</body>

</html>
