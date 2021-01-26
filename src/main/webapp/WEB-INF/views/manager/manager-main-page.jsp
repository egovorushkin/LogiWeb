<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../fragments/before-title-main.jsp"/>

<title>Home</title>

<jsp:include page="../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4">Welcome to LogiWeb,
            <security:authorize access="isAuthenticated()">
                <security:authentication property="principal.username"/>
            </security:authorize></h4>
    </div>
</main>

<jsp:include page="../fragments/bootstrap-core-js-main.jsp"/>

