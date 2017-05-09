<%@ page session="true" isELIgnored="false" contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <c:choose>
                <c:when test="${empty sessionScope.startTestToken}">
                    <a class="navbar-brand uppercase" href="<c:url value="/"/>"><fmt:message key="text.title" /></a>
                    <a class="navbar-brand navbar-user" href="<c:url value="/?command=redirect-profile"/> ">
                        <c:out value="${sessionScope.user.login}"/>(<c:out value="${sessionScope.user.role.name()}"/>)
                    </a>
                </c:when>
                <c:otherwise>
                    <a class="navbar-brand uppercase" href="#"><fmt:message key="text.title" /></a>
                    <a class="navbar-brand navbar-user" href="#">
                        <c:out value="${sessionScope.user.login}"/>(<c:out value="${sessionScope.user.role.name()}"/>)
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>