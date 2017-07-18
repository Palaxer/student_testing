<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" uri="/WEB-INF/date-format.tld"%>
<%@ taglib prefix="t" uri="/WEB-INF/complete-time-format.tld"%>1
<%@ page session="true"  isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.title.complete-test-info" /></title>
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
                    <div class="col-md-6 btn-group">
                        <h2 class="sub-header uppercase"><fmt:message key="text.title.test-info"/></h2>

                        <div class="form-group has-feedback">
                            <label class="control-label" for="category"><fmt:message key="text.category"/></label>
                            <input class="form-control" type="text" id="category" value="<c:out value="${test.category.name}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="test"><fmt:message key="text.name"/></label>
                            <input class="form-control" type="text" id="test" value="<c:out value="${test.name}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="desc"><fmt:message key="text.desc"/></label>
                            <textarea class="form-control" rows="5" id="desc" disabled><c:out value="${test.description}"/></textarea>
                        </div>
                        <div class="form-group pull-left has-feedback">
                            <label class="control-label" for="tutor"><fmt:message key="text.tutor"/></label>
                            <input class="form-control" type="text" id="tutor" value="<c:out value="${test.tutor.login}"/>" disabled>
                        </div>
                        <div class="form-group pull-right has-feedback">
                            <label class="control-label" for="q-count"><fmt:message key="text.question-count"/></label>
                            <input class="form-control" type="text" id="q-count" value="<c:out value="${test.questionCount}"/>" disabled>
                        </div>
                        <div class="form-group pull-left has-feedback">
                            <label class="control-label" for="pass-score"><fmt:message key="text.pass-score"/></label>
                            <input class="form-control" type="text" id="pass-score" value="<c:out value="${test.passedScore}"/>" disabled>
                        </div>
                        <div class="form-group pull-right has-feedback">
                            <label class="control-label" for="pass-time"><fmt:message key="text.elapsed-time-pass"/></label>
                            <input class="form-control form-inline" type="text" id="pass-time" value="<c:out value="${test.passedTime}"/>" disabled>
                        </div>
                        <div class="form-group pull-left has-feedback">
                            <label class="control-label" for="complete-time"><fmt:message key="text.test-completed-times"/></label>
                            <input class="form-control" type="text" id="complete-time" value="<c:out value="${test.completedTime}"/>" disabled>
                        </div>
                        <div class="form-group pull-right has-feedback">
                            <label class="control-label" for="percent-pass"><fmt:message key="text.pass-percent"/></label>
                            <input class="form-control form-inline" type="text" id="percent-pass" value="<c:out value="${test.passPercent}"/>" disabled>
                        </div>

                        <form method="post" action="<c:url value="/?command=test-info&id=${test.id}"/>">
                            <button type="submit" class="btn btn-block btn-lg btn-primary uppercase">
                                <fmt:message key="text.view-test-info"/>
                            </button>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <h2 class="sub-header uppercase"><fmt:message key="text.title.complete-test-info" /></h2>

                        <div class="form-group has-feedback">
                            <label class="control-label" for="login"><fmt:message key="text.student"/></label>
                            <input class="form-control" type="text" id="login" value="<c:out value="${completeTest.student.login}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="name"><fmt:message key="text.name"/></label>
                            <input class="form-control" type="text" id="name" value="<c:out value="${completeTest.student.name}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="surname"><fmt:message key="text.surname"/></label>
                            <input class="form-control" type="text" id="surname" value="<c:out value="${completeTest.student.surname}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="start-time"><fmt:message key="text.start-time"/></label>
                            <input class="form-control" type="text" id="start-time" value="<d:dateFrm date="${completeTest.startTime}" local="${language}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="elapsed-time"><fmt:message key="text.elapsed-time"/></label>
                            <input class="form-control" type="text" id="elapsed-time" value="<t:timeFrm time="${completeTest.elapsedTime}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="score"><fmt:message key="text.score"/></label>
                            <input class="form-control" type="text" id="score" value="<c:out value="${completeTest.score}"/>" disabled>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="control-label" for="pass"><fmt:message key="text.pass"/></label>
                            <input class="form-control" type="text" id="pass" value="<c:out value="${completeTest.passed}"/>" disabled>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>
