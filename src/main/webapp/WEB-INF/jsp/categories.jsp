<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.sidebar.categories" /></title>
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
            <div class="col-xs-9 col-xs-offset-2 main">
                <h1 class="sub-header uppercase"><fmt:message key="text.sidebar.categories" /></h1>
                <c:if test="${not empty updateSuccess}">
                    <div class="alert alert-success capitalize">
                        <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.update-category" />
                    </div>
                </c:if>
                <c:if test="${not empty updateFailure}">
                    <div class="alert alert-danger capitalize">
                        <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.update-category" />
                    </div>
                </c:if>
                <c:if test="${not empty deleteSuccess}">
                    <div class="alert alert-success capitalize">
                        <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.delete-category" />
                    </div>
                </c:if>
                <c:if test="${not empty deleteFailure}">
                    <div class="alert alert-danger capitalize">
                        <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.delete-category" />
                    </div>
                </c:if>

                <c:if test="${not empty invalidData}">
                    <div class="alert alert-danger capitalize">
                        <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.alert.invalid-data" /><br>
                        <c:if test="${not empty invalidData.invalidName}">
                            -<fmt:message key="text.alert.invalid-name" />([\W\w]{2,45})<br>
                        </c:if>
                    </div>
                </c:if>

                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="uppercase">
                            <tr>
                                <th><fmt:message key="text.name" /></th>
                                <th><fmt:message key="text.update" /></th>
                                <th><fmt:message key="text.delete" /></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${categories}" var="category">
                                <tr>
                                    <form method="post" action="<c:url value="/?command=update-category&id=${category.id}"/>">
                                        <td>
                                            <div class="form-group has-feedback">
                                                <input class="form-control" type="text" id="name" name="name" value="<c:out value="${category.name}"/>">
                                            </div>
                                        </td>
                                        <td>
                                            <button class="btn btn-info btn-xs uppercase"><fmt:message key="text.update" /></button>
                                        </td>
                                    </form>
                                    <td>
                                        <a class="btn btn-danger btn-xs uppercase" href="<c:url value="/?command=delete-category&id=${category.id}"/>">
                                            <fmt:message key="text.delete" />
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
</body>

</html>
