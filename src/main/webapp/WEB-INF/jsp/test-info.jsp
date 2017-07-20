<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<%@ taglib prefix="t" uri="/WEB-INF/complete-time-format.tld"%>
<%@ page session="true"  isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.title.test-info" /></title>
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
                        <h2 class="sub-header uppercase"><fmt:message key="text.title.test-info" /></h2>

                        <c:if test="${not empty updateSuccess}">
                            <div class="alert alert-success capitalize">
                                <strong><fmt:message key="text.alert.success" /></strong> <fmt:message key="text.update-test" />
                            </div>
                        </c:if>
                        <c:if test="${not empty invalidUpdate}">
                            <div class="alert alert-danger capitalize">
                                <strong><fmt:message key="text.alert.error" /></strong> <fmt:message key="text.update-test" /><br>
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
                                <c:if test="${not empty invalidData.invalidPassScore}">
                                    -<fmt:message key="text.alert.invalid-pass-score" />(>=0, <= question count)<br>
                                </c:if>
                                <c:if test="${not empty invalidData.invalidPassTime}">
                                    -<fmt:message key="text.alert.invalid-pass-time" />(>=0)<br>
                                </c:if>
                            </div>
                        </c:if>

                        <c:if test="${empty permission}">
                            <c:set var="disabled" scope="request" value="disabled"/>
                        </c:if>

                        <form method="post" action="<c:url value="/?command=update-test&id=${test.id}"/>">
                            <div class="form-group has-feedback">
                                <label class="control-label" for="category"><fmt:message key="text.category"/></label>
                                <select class="form-control" name="category" id="category" ${disabled}>
                                    <c:forEach items="${categories}" var="category">
                                        <c:choose>
                                            <c:when test="${test.category.name == category.name}">
                                                <option selected value="${category.name}">${category.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${category.name}">${category.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group ${invalidData.invalidName} has-feedback">
                                <label class="control-label" for="name"><fmt:message key="text.name" /></label>
                                <input class="form-control" type="text" id="name" name="name" value="<c:out value="${test.name}"/>" ${disabled}>
                            </div>
                            <div class="form-group ${invalidData.invalidDesc} has-feedback">
                                <label class="control-label" for="desc"><fmt:message key="text.desc" /></label>
                                <textarea class="form-control" rows="4" id="desc" name="desc" ${disabled}><c:out value="${test.description}"/></textarea>
                            </div>
                            <div class="form-group ${invalidData.invalidPassScore} pull-left">
                                <label class="control-label" for="pass-score"><fmt:message key="text.pass-score" /></label>
                                <input class="form-control" type="number" id="pass-score" name="pass-score" value="<c:out value="${test.passedScore}"/>" ${disabled}>
                            </div>
                            <div class="form-group ${invalidData.invalidPassTime} pull-right">
                                <label class="control-label" for="pass-time"><fmt:message key="text.elapsed-time-pass" /></label>
                                <input class="form-control form-inline" type="number" id="pass-time" name="pass-time" value="<c:out value="${test.passedTime}"/>" ${disabled}>
                            </div>
                            <div class="form-group pull-left">
                                <label class="control-label" for="tutor"><fmt:message key="text.tutor" /></label>
                                <input class="form-control" type="text" id="tutor" value="<c:out value="${test.tutor.login}"/>" disabled>
                            </div>
                            <div class="form-group pull-right">
                                <label class="control-label" for="q-count"><fmt:message key="text.question-count" /></label>
                                <input class="form-control" type="text" id="q-count" name="q-count" value="<c:out value="${test.questionCount}"/>" readonly>
                            </div>
                            <div class="form-group pull-left">
                                <label class="control-label" for="complete-time"><fmt:message key="text.test-completed-times" /></label>
                                <input class="form-control" type="text" id="complete-time" value="<c:out value="${test.completedTime}"/>" disabled>
                            </div>
                            <div class="form-group pull-right">
                                <label class="control-label" for="percent-pass"><fmt:message key="text.pass-percent" /></label>
                                <input class="form-control form-inline" type="text" id="percent-pass" value="<c:out value="${test.passPercent}"/>" disabled>
                            </div>

                            <c:if test="${not empty permission}">
                                <button type="submit" class="btn btn-lg btn-primary btn-block uppercase">
                                    <fmt:message key="text.update-test" />
                                </button>
                            </c:if>
                        </form> <br>
                        <c:if test="${not empty permission}">
                            <div class="btn-group-justified">
                                <form method="post" action="<c:url value="/?command=questions&id=${test.id}"/>">
                                    <button type="submit" class="btn btn-lg btn-primary pull-right uppercase">
                                        <fmt:message key="text.view-quest" />
                                </button>
                                </form>
                                <c:choose>
                                    <c:when test="${test.active}">
                                        <form method="post" action="<c:url value="/?command=deactivate-test&id=${test.id}"/>">
                                            <button type="submit" class="btn btn-lg btn-primary  uppercase">
                                                <fmt:message key="text.deactivate" />
                                            </button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="post" action="<c:url value="/?command=activate-test&id=${test.id}"/>">
                                            <button type="submit" class="btn btn-lg btn-primary  uppercase">
                                                <fmt:message key="text.activate" />
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                                <br>
                            </div>
                        </c:if>
                        <c:if test="${test.active}">
                            <form onsubmit="return confirm('<fmt:message key="text.test.start-warning" />');" method="post" action="<c:url value="/?command=start-test&id=${test.id}"/>">
                                <button type="submit" class="btn btn-lg btn-success btn-block uppercase">
                                    <fmt:message key="text.start-test" />
                                </button>
                            </form>
                        </c:if>
                </div>
                <div class="col-md-7">
                    <h2 class="sub-header uppercase"><fmt:message key="text.test-history" /></h2>
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="uppercase">
                            <tr>
                                <th><fmt:message key="text.login" /></th>
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
                                    <td><c:out value="${completeTest.student.login}"/></td>
                                    <td><c:out value="${completeTest.score}"/></td>
                                    <td><d:dateFrm date="${completeTest.startTime}" local="${language}"/></td>
                                    <td><t:timeFrm time="${completeTest.elapsedTime}"/></td>
                                    <td><c:out value="${completeTest.passed}"/></td>
                                    <td>
                                        <a class="btn btn-primary btn-xs uppercase" href="<c:url value="/?command=complete-test-info&id=${completeTest.id}"/>">
                                            <fmt:message key="text.info" />
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
    </div>
</body>

</html>
