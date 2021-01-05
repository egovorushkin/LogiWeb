<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<link rel="canonical" href="https://getbootstrap.com/docs/4.1/examples/dashboard/">

<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/css/dashboard.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>

<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="${pageContext.request.contextPath}/">LogiWeb</a>
    <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="#">
                <security:authorize access="isAuthenticated()">
                    <security:authentication property="principal.username"/>
                </security:authorize>
            </a>
        </li>
    </ul>
</header>

<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block bg-light sidebar collapse">
            <div class="sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/">
                            <i class="fas fa-home"></i>
                            Home <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/trucks/list">
                            <i class="fas fa-truck"></i>
                            Trucks
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/drivers/list">
                            <i class="fas fa-users"></i>
                            Drivers
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/orders/list">
                            <i class="fas fa-clipboard-list"></i>
                            Orders
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/cargoes/list">
                            <i class="fas fa-dolly-flatbed"></i>
                            Cargos
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/waypoint-lists/list">
                            <span data-feather="trending-down"></span>
                            Waypoint Lists
                        </a>
                    </li>
                </ul>

                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                    <span>Saved reports</span>
                    <a class="d-flex align-items-center text-muted" href="#">
                        <span data-feather="plus-circle"></span>
                    </a>
                </h6>
                <ul class="nav flex-column mb-2">
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <span data-feather="file-text"></span>
                            Current month
                        </a>
                    </li>
                </ul>
                <ul class="nav flex-column mb-2">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/map">
                            <i class="fas fa-map-marked-alt"></i>
                            Google Maps Test
                        </a>
                    </li>
                </ul>
            </div>
        </nav>