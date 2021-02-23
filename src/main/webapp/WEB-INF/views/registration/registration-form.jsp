<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="../fragments/before-title-main.jsp"/>
    <title>Create Account</title>

    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet"
          href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">

</head>
<body>
<div class="card bg-light">
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center">Create Account</h4>
        <form:form
                action="${pageContext.request.contextPath}/register/processRegistrationForm"
                modelAttribute="userDto">

            <!-- Place for messages: error, alert etc ... -->
            <div class="form-group">
                <div class="col-xs-10">
                    <div>
                        <!-- Check for registration error -->
                        <c:if test="${registrationError != null}">
                            <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                    ${registrationError}
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <em class="fa fa-user"></em> </span>
                </div>
                <form:errors path="userName" cssClass="error"/>
                <form:input path="userName" placeholder="username (*)"
                            class="form-control"/>
            </div>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <em class="fa fa-lock"></em> </span>
                </div>
                <form:errors path="password" cssClass="error"/>
                <form:password path="password"
                               placeholder="password (*)"
                               class="form-control"/>
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <em class="fa fa-lock"></em> </span>
                </div>
                <form:errors path="matchingPassword" cssClass="error"/>
                <form:password path="matchingPassword"
                               placeholder="confirm password (*)"
                               class="form-control"/>
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <em class="fa fa-user"></em> </span>
                </div>
                <form:errors path="firstName" cssClass="error"/>
                <form:input path="firstName"
                            placeholder="First Name"
                            class="form-control"/>
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <em class="fa fa-user"></em> </span>
                </div>
                <form:errors path="lastName" cssClass="error"/>
                <form:input path="lastName"
                            placeholder="Last name"
                            class="form-control"/>
            </div>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <em
                            class="fa fa-building"></em> </span>
                </div>

                <form:select path="currentCity.id"
                             cssClass="form-control form-control-sm">
                    <c:if test="${empty userDto.currentCity}">
                        <form:option value="" disabled="true"
                                     selected="true">City</form:option>
                    </c:if>
                    <form:options items="${cities}" itemValue="id"
                                  itemLabel="name"/>
                </form:select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-dark btn-block"> Create
                    Account
                </button>
            </div>
            <p class="text-center">Have an account? <a
                    href="${pageContext.request.contextPath}/login">Sign In</a>
            </p>
        </form:form>
    </article>
</div>
</body>
</html>
