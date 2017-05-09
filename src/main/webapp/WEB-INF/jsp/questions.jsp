<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.questions" /></title>
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
                    <div class="col-md-4">
                        <h2 class="sub-header uppercase"><fmt:message key="text.add-question" /></h2>

                        <c:if test="${not empty updateSuccess}">
                            <div class="alert alert-success capitalize">
                                <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.update-question" />
                            </div>
                        </c:if>
                        <c:if test="${not empty deleteSuccess}">
                            <div class="alert alert-success capitalize">
                                <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.delete-question" />
                            </div>
                        </c:if>
                        <c:if test="${not empty addFailure}">
                            <div class="alert alert-danger capitalize">
                                <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.add-question" /><br>
                            </div>
                        </c:if>
                        <c:if test="${not empty updateFailure}">
                            <div class="alert alert-danger capitalize">
                                <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.update-question" /><br>
                            </div>
                        </c:if>
                        <c:if test="${not empty deleteFailure}">
                            <div class="alert alert-danger capitalize">
                                <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.delete-question" /><br>
                            </div>
                        </c:if>

                        <c:if test="${not empty activeTest}">
                            <c:set var="active" scope="request" value="disabled"/>
                        </c:if>

                        <c:if test="${not empty invalidData}">
                            <div class="alert alert-danger capitalize">
                                <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.alert.invalid-data" /><br>
                                <c:if test="${not empty invalidData.invalidText}">
                                    -<fmt:message key="text.alert.invalid-text" />([\W\w]{5,400})<br>
                                </c:if>
                            </div>
                        </c:if>

                        <form method="post" action="<c:url value="/?command=add-question&id=${test.id}"/>">
                            <div class="form-group ${invalidData.invalidText} has-feedback">
                                <label class="control-label" for="text"><fmt:message key="text.text" /></label>
                                <textarea class="form-control" rows="4" id="text" name="text" ${active}><c:out value="${text}"/></textarea>
                            </div>
                            <button type="submit" class="btn btn-lg btn-primary btn-block uppercase" ${active}>
                                <fmt:message key="text.add-question" />
                            </button>
                        </form><br>
                        <a class="btn btn-primary btn-lg block uppercase" href="<c:url value="/?command=test-info&id=${test.id}"/>">
                            <fmt:message key="text.back" />
                        </a>
                    </div>
                    <div class="col-md-8">
                        <h2 class="sub-header uppercase"><fmt:message key="text.questions" /></h2>
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="uppercase">
                                <tr>
                                    <th><fmt:message key="text.text" /></th>
                                    <th><fmt:message key="text.update" /></th>
                                    <th><fmt:message key="text.info" /></th>
                                    <th><fmt:message key="text.delete" /></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${questions}" var="question">
                                    <tr>
                                        <form method="post" action="<c:url value="/?command=update-question&id=${question.id}"/>">
                                            <td>
                                                <c:choose>
                                                    <c:when test="${question.id == updateQuestion.id}">
                                                        <div class="form-group has-error has-feedback">
                                                            <textarea class="form-control has-error" rows="2" name="text" ${active}><c:out value="${updateQuestion.text}"/></textarea>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="form-group has-feedback">
                                                            <textarea class="form-control" rows="2" name="text" ${active}><c:out value="${question.text}"/></textarea>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <button type="submit" class="btn btn-success btn-xs uppercase" ${active}>
                                                    <fmt:message key="text.update" />
                                                </button>
                                            </td>
                                        </form>
                                        <td>
                                            <a class="btn btn-primary btn-xs uppercase" href="<c:url value="/?command=question-info&id=${question.id}"/>">
                                                <fmt:message key="text.info" />
                                            </a>
                                        </td>
                                        <td>
                                            <a class="btn btn-danger btn-xs uppercase ${active}" href="<c:url value="/?command=delete-question&id=${question.id}"/>">
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
        </div>
    </div>
</body>

</html>
