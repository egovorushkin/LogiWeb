<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:include page="../fragments/before-title-main.jsp"/>
<title>
    Error
</title>
<hr>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags" %>

<link rel="canonical"
      href="https://getbootstrap.com/docs/4.1/examples/dashboard/">

<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
      rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/dashboard.css"
      rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css"
      rel="stylesheet">
</head>
<body>

<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow nav">
    <a class="navbar-brand col-md-3 col-lg-2 me-0 px-2">LogiWeb</a>
    <ul class="navbar-nav px-2">
        <li class="nav-item text-nowrap d-inline-block">
        </li>
    </ul>
</header>
<main>
    <hr>
    <div class="text-center">
        <h3>Application has encountered an error. Please contact support on
            ...</h3>

        <sec:authorize access="hasRole('ADMIN')">
            <a role="button" class="btn btn-sm btn-secondary"
               href="${pageContext.request.contextPath}/admin">
                Back to main page
            </a>
        </sec:authorize>

        <sec:authorize access="hasRole('DRIVER')">
            <a role="button" class="btn btn-sm btn-secondary"
               href="${pageContext.request.contextPath}/driver">
                Back to main page
            </a>
        </sec:authorize>
    </div>


</main>

<jsp:include page="../fragments/bootstrap-core-js-main.jsp"/>