<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />

<!DOCTYPE html>

<head>
    <title><fmt:message key="text.test" /></title>
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
                <h2 class="sub-header uppercase"><c:out value="${test.name}"/></h2>
                <form id="form" method="post" action="<c:url value="/?command=submit-test&id=${test.id}"/>">
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead class="uppercase">
                            <tr>
                                <th colspan="2"><fmt:message key="text.questions" /></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${test.questions}" var="question">
                                <tr>
                                    <td colspan="2"><c:out value="${question.text}"/></td>
                                </tr>
                                <c:forEach items="${question.answers}" var="answer">
                                    <tr>
                                        <td width="30px">
                                            <input type="checkbox" name="${answer.id}" value="checked">
                                        </td>
                                        <td><c:out value="${answer.text}"/></td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <b class="time" id="time"><c:out value="${test.passedTime}"/>:00</b>
                    <button type="submit" class="btn btn-success btn-lg uppercase submit">
                        <input type="hidden" name="startTestToken" value="${startTestToken}">
                        <fmt:message key="text.submit" />
                    </button>
                </form>
            </div>
        </div>
    </div>
</body>

<script>
    var minutes;
    var seconds;

    var time;
    var form;

    window.onblur = function() {
        submit();
    };

    window.onload = function() {
        time = document.getElementById("time");
        form = document.getElementById("form");

        minutes = time.innerHTML.split(":")[0];
        seconds = time.innerHTML.split(":")[1];

        timer();
    };

    function submit() {
        form.submit();
    }

    function timer() {
        if(seconds == 0) {
            if(minutes == 0) submit();

            minutes--;

            if(minutes < 10) minutes = "0" + minutes;

            seconds = 59;
        } else seconds--;

        if(seconds < 10) seconds = "0" + seconds;

        time.innerHTML = minutes + ":" + seconds;

        setTimeout(timer, 1000);
    }

</script>

</html>
