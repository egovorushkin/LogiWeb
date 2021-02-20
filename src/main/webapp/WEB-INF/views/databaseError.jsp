
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Database Error</title>
</head>
<body>
<main>
    <h3>Application has encountered an error.</h3>

    <p><strong>Page: </strong>${url}</p>
    <p><strong>Occurred: </strong>${timestamp}</p>
    <p><strong>Exception: </strong>${exception}</p>

    <sec:authorize access="hasRole('ADMIN')">
        <a role="button" class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/admin">
            Back to main page
        </a>
    </sec:authorize>

    <sec:authorize access="hasRole('DRIVER')">
        <a role="button" class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/driver">
            Back to main page
        </a>
    </sec:authorize>




</main>


</body>
</html>
