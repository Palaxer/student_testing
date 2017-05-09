<%@ page session="true" isELIgnored="false" contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<div class="col-xs-2 sidebar">
    <c:choose>
        <c:when test="${empty sessionScope.startTestToken}">
            <p class="category uppercase"><fmt:message key="text.sidebar.student" /></p>
            <ul class="nav nav-sidebar">
                <li class="uppercase ${allTestSelect}"><a href="<c:url value="/?command=tests&category=all"/>"><fmt:message key="text.sidebar.all-test" /></a></li>
                <li class="uppercase ${testHistorySelect}"><a href="<c:url value="/?command=test-history"/>"><fmt:message key="text.sidebar.test-history" /></a></li>
            </ul>
            <c:if test="${'TUTOR' == sessionScope.user.role.name() ||
                            'ADMIN' == sessionScope.user.role.name()}">
                <p class="category uppercase"><fmt:message key="text.sidebar.tutor" /></p>
                <ul class="nav nav-sidebar">
                    <li class="uppercase ${myTestSelect}"><a href="<c:url value="/?command=tutor-test"/>"><fmt:message key="text.sidebar.my-test" /></a></li>
                    <li class="uppercase ${createTestSelect}"><a href="<c:url value="/?command=redirect-create-test"/>"><fmt:message key="text.sidebar.create-test" /></a></li>
                    <li class="uppercase ${createCategorySelect}"><a href="<c:url value="/?command=redirect-create-category"/>"><fmt:message key="text.sidebar.create-category" /></a></li>
                </ul>
            </c:if>
            <c:if test="${'ADMIN' == sessionScope.user.role.name()}">
                <p class="category uppercase"><fmt:message key="text.sidebar.admin" /></p>
                <ul class="nav nav-sidebar">
                    <li class="uppercase ${allUserSelect}"><a href="<c:url value="/?command=users&role=all"/>"><fmt:message key="text.sidebar.users" /></a></li>
                    <li class="uppercase ${allCategorySelect}"><a href="<c:url value="/?command=categories"/>"><fmt:message key="text.sidebar.categories" /></a></li>

                </ul>
            </c:if>
            <p class="category uppercase"><fmt:message key="text.sidebar.setting" /></p>
            <ul class="nav nav-sidebar">
                <li class="uppercase ${profileSelect}"><a href="<c:url value="/?command=redirect-profile"/>"><fmt:message key="text.sidebar.profile" /></a></li>
                <li class="uppercase"><a href="<c:url value="/?command=logout"/>"><fmt:message key="text.sidebar.logout" /></a></li>
            </ul>
        </c:when>
        <c:otherwise>
            <p class="category uppercase"><fmt:message key="text.sidebar.student" /></p>
            <ul class="nav nav-sidebar">
                <li class="uppercase ${allTestSelect}"><a href="#"><fmt:message key="text.sidebar.all-test" /></a></li>
                <li class="uppercase ${testHistorySelect}"><a href="#"><fmt:message key="text.sidebar.test-history" /></a></li>
            </ul>
            <c:if test="${'TUTOR' == sessionScope.user.role.name() ||
                            'ADMIN' == sessionScope.user.role.name()}">
                <p class="category uppercase"><fmt:message key="text.sidebar.tutor" /></p>
                <ul class="nav nav-sidebar">
                    <li class="uppercase ${myTestSelect}"><a href="#"><fmt:message key="text.sidebar.my-test" /></a></li>
                    <li class="uppercase ${createTestSelect}"><a href="#"><fmt:message key="text.sidebar.create-test" /></a></li>
                    <li class="uppercase ${createCategorySelect}"><a href="#"><fmt:message key="text.sidebar.create-category" /></a></li>
                </ul>
            </c:if>
            <c:if test="${'ADMIN' == sessionScope.user.role.name()}">
                <p class="category uppercase"><fmt:message key="text.sidebar.admin" /></p>
                <ul class="nav nav-sidebar">
                    <li class="uppercase ${allUserSelect}"><a href="#"><fmt:message key="text.sidebar.users" /></a></li>
                    <li class="uppercase ${allCategorySelect}"><a href="#"><fmt:message key="text.sidebar.categories" /></a></li>
                </ul>
            </c:if>
            <p class="category uppercase"><fmt:message key="text.sidebar.setting" /></p>
            <ul class="nav nav-sidebar">
                <li class="uppercase ${profileSelect}"><a href="#"><fmt:message key="text.sidebar.profile" /></a></li>
                <li class="uppercase"><a href="#"><fmt:message key="text.sidebar.logout" /></a></li>
            </ul>
        </c:otherwise>
    </c:choose>
</div>
