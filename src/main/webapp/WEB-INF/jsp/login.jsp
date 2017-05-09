<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.title.login" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css"/>">
</head>

<body>
    <div class="container login-form">
        <div class="jumbotron">
            <a class="uppercase pull-right" href="<c:url value="/?language=en"/>"><fmt:message key="text.lang.eng"/></a>
            <a class="uppercase pull-right" href="<c:url value="/?language=ru"/>"><fmt:message key="text.lang.ru"/></a>
            <h2 class="text-center capitalize"><fmt:message key="text.title.login" /></h2>
            <form method="post" action="<c:url value="/?command=login"/>">
                <c:if test="${not empty loginError}">
                    <div class="alert alert-danger capitalize">
                        <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.alert.login-invalid" />
                    </div>
                </c:if>
                <c:if test="${not empty logout}">
                    <div class="alert alert-success capitalize">
                        <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.alert.logout" />
                    </div>
                </c:if>
                <div class="form-group has-feedback">
                    <label class="control-label" for="login"><fmt:message key="text.login"/></label>
                    <input class="form-control" type="text" id="login" name="login" value="<c:out value="${login}"/>">
                </div>
                <div class="form-group has-feedback">
                    <label class="control-label" for="passwd"><fmt:message key="text.passwd"/></label>
                    <input class="form-control" type="password" id="passwd" name="passwd">
                </div>
                <br>
                <button type="submit" class="btn btn-lg btn-primary btn-block uppercase"><fmt:message key="text.button.login"/></button>
            </form><br>
            <form method="get">
                <input type="hidden" name="command" value="redirect-registration">
                <button type="submit" class="btn btn-lg btn-primary btn-block uppercase"><fmt:message key="text.button.registration"/></button>
            </form>
        </div>
    </div>
</body>

</html>
