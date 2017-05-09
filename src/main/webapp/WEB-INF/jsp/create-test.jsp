<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<%@ page session="true"  isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.create-test" /></title>
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
                    <h2 class="sub-header uppercase"><fmt:message key="text.create-test" /></h2>

                    <c:if test="${not empty createFailure}">
                        <div class="alert alert-danger capitalize">
                            <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.create-test" /><br>
                        </div>
                    </c:if>

                    <c:if test="${not empty invalidData}">
                        <div class="alert alert-danger capitalize">
                            <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.alert.invalid-data" /><br>
                            <c:if test="${not empty invalidData.invalidName}">
                                -<fmt:message key="text.alert.invalid-name" />([\W\w]{5,60})<br>
                            </c:if>
                            <c:if test="${not empty invalidData.invalidDesc}">
                                -<fmt:message key="text.alert.invalid-desc" />([\W\w]{5,255})<br>
                            </c:if>
                            <c:if test="${not empty invalidData.invalidPassTime}">
                                -<fmt:message key="text.alert.invalid-pass-time" />(>=0)<br>
                            </c:if>
                        </div>
                    </c:if>

                    <form method="post" action="<c:url value="/?command=create-test"/>">
                        <div class="form-group has-feedback">
                            <label class="control-label" for="category"><fmt:message key="text.category"/></label>
                            <select class="form-control" name="category" id="category">
                                <c:forEach items="${categories}" var="category">
                                    <option value="<c:out value="${category.name}"/>"><c:out value="${category.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group ${invalidData.invalidName} has-feedback">
                            <label class="control-label" for="name"><fmt:message key="text.name" /></label>
                            <input class="form-control" type="text" id="name" name="name" value="<c:out value="${test.name}"/>">
                        </div>
                        <div class="form-group ${invalidData.invalidDesc} has-feedback">
                            <label class="control-label" for="desc"><fmt:message key="text.desc" /><c:out value="${test.description}"/></label>
                            <textarea class="form-control" rows="7" id="desc" name="desc"></textarea>
                        </div>
                        <div class="form-group ${invalidData.invalidPassTime} has-feedback">
                            <label class="control-label" for="pass-time"><fmt:message key="text.elapsed-time-pass" /></label>
                            <input class="form-control form-inline" type="number" id="pass-time" name="pass-time" value="<c:out value="${test.passedTime}"/>">
                        </div>
                        <br>
                        <button type="submit" class="btn btn-lg btn-primary btn-block uppercase">
                            <fmt:message key="text.create-test" />
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
