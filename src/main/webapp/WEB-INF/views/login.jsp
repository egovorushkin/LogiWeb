<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="icon" href="${pageContext.request.contextPath}/resources/img/truck.png">

    <title>Sign in</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/resources/css/signin.css" rel="stylesheet">
</head>

<body class="text-center">
<form class="form-signin" action="${pageContext.request.contextPath}/authenticateTheUser" method="POST">
    <h1 class="h3 mb-3 font-weight-normal">Sign in</h1>

    <!-- Place for messages: error, alert etc ... -->
    <div class="form-group">
        <div class="col-xs-15">
            <div>
                <!-- Check for login error -->
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                        Invalid username and password.
                    </div>
                </c:if>
                <!-- Check for logout -->
                <c:if test="${param.logout != null}">
                    <div class="alert alert-success col-xs-offset-1 col-xs-10">
                        You have been logged out.
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- User name -->
    <label for="username" class="sr-only">Username</label>
    <input type="text" name="username" id="username" class="form-control" placeholder="username">

    <!-- Password -->
    <label for="password" class="sr-only">Password</label>
    <input type="password" name="password" id="password" class="form-control" placeholder="password">

    <!-- Login/Submit Button -->
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    <p class="mt-5 mb-3 text-muted">&copy; 2020</p>

    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>
<jsp:include page="fragments/bootstrap-core-js.jsp"/>
