<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<%@ page session="true"  isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.test-history" /></title>
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
                <h2 class="sub-header uppercase"><fmt:message key="text.test-history" /></h2>
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="uppercase">
                        <tr>
                            <th><fmt:message key="text.test" /></th>
                            <th><fmt:message key="text.score" /></th>
                            <th><fmt:message key="text.start-time" /></th>
                            <th><fmt:message key="text.elapsed-time" /></th>
                            <th><fmt:message key="text.pass" /></th>
                            <th><fmt:message key="text.info" /></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${completeTests}" var="completeTest">
                            <tr>
                                <td><c:out value="${completeTest.test.name}"/></td>
                                <td><c:out value="${completeTest.score}"/></td>
                                <td><d:dateFrm date="${completeTest.startTime}" local="${language}"/></td>
                                <td><c:out value="${completeTest.elapsedTime}"/></td>
                                <td><c:out value="${completeTest.passed}"/></td>
                                <td>
                                    <a class="btn btn-primary btn-xs uppercase" href="<c:url value="/?command=complete-test-info&id=${completeTest.id}"/>">
                                        <fmt:message key="text.info"/>
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
                                <li class="active"><a href="<c:url value="/?command=test-history&page=${i}"/>">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="<c:url value="/?command=test-history&page=${i}"/>">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</body>

</html>
