<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>
    <form:form modelAttribute="user" action="${pageContext.request.contextPath}/registration" method="post">
        <div>
            <label for="firstName">First Name</label>
            <form:input path="firstName" type="text" class="form-control form-control-sm" id="firstName"
                        name="firstName"/>
            <form:errors path="firstName" cssClass="alert alert-danger"/>
        </div>
        <div>
            <label for="lastName">Last Name</label>
            <form:input path="lastName" type="text" class="form-control form-control-sm" id="lastName"
                        name="lastName"/>
            <form:errors path="lastName" cssClass="alert alert-danger"/>
        </div>
        <div>
            <label for="username">Username</label>
            <form:input path="username" type="text" class="form-control form-control-sm" id="username"
                        name="username"/>
            <form:errors path="username" cssClass="alert alert-danger"/>
        </div>
        <div>
            <label for="password">Password</label>
            <form:input path="password" type="text" class="form-control form-control-sm" id="password"
                        name="password"/>
            <form:errors path="password" cssClass="alert alert-danger"/>
        </div>
        <div>
            <label for="matchingPassword">Confirm Password</label>
            <form:input path="matchingPassword" type="text" class="form-control form-control-sm" id="matchingPassword"
                        name="matchingPassword"/>
        </div>
        <button type="submit" >submit</button>
    </form:form>

    <a href="${pageContext.request.contextPath}/login">login</a>
</body>
</html>
