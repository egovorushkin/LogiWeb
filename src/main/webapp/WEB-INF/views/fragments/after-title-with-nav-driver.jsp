<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="canonical" href="https://getbootstrap.com/docs/4.1/examples/dashboard/">

<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/css/dashboard.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>

<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow nav">
    <a class="navbar-brand col-md-3 col-lg-2 me-0 px-2"
       href="${pageContext.request.contextPath}/driver">LogiWeb</a>
    <ul class="navbar-nav px-2">
        <li class="nav-item text-nowrap d-inline-block">

            <form:form cssClass="form-inline"
                       action="${pageContext.request.contextPath}/logout"
                       method="POST">
                <a class="nav-link mr-1" href="#">Hi,
                    <security:authorize access="isAuthenticated()">
                        <security:authentication property="principal.username"/>
                    </security:authorize>
                </a>
                <input class="btn btn-outline-success btn-sm" type="submit"
                       value="Logout" />
            </form:form>
        </li>
    </ul>
</header>

<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar collapse">
            <div class="sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/info">
                            <span data-feather="user"></span>
                            My profile
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/orders">
                            <span data-feather="clipboard"></span>
                            My orders
                        </a>
                    </li>
                </ul>
            </div>
        </nav>