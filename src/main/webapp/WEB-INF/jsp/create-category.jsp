<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<%@ page session="true"  isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.sidebar.create-category" /></title>
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
                <div class="col-md-7 ">
                    <h2 class="sub-header uppercase"><fmt:message key="text.sidebar.create-category" /></h2>

                    <c:if test="${not empty createSuccess}">
                        <div class="alert alert-success capitalize">
                            <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.sidebar.create-category" /><br>
                        </div>
                    </c:if>

                    <c:if test="${not empty createFailure}">
                        <div class="alert alert-danger capitalize">
                            <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.sidebar.create-category" /><br>
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

                    <form method="post" action="<c:url value="/?command=create-category"/>">
                        <div class="form-group ${invalidData.invalidName} has-feedback">
                            <label class="control-label" for="name"><fmt:message key="text.name" /></label>
                            <input class="form-control" type="text" id="name" name="name" value="<c:out value="${name}"/>">
                        </div>
                        <br>
                        <button type="submit" class="btn btn-lg btn-primary btn-block uppercase">
                            <fmt:message key="text.sidebar.create-category" />
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
