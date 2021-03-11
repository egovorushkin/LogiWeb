<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>New Driver</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4"><em class="fas fa-user-plus"></em> | Create New Driver
        </h4>
    </div>

    <form:form modelAttribute="userDto"
               action="${pageContext.request.contextPath}/drivers/save">

        <!-- Place for messages: error, alert etc ... -->
        <div class="form-group">
            <div class="col-xs-15">
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

        <div class="row mb-3">
            <label for="userName" class="col-sm-2 col-form-label">Username:
            </label>
            <div class="col-sm-2">
                <form:errors path="userName" cssClass="error"/>
                <form:input path="userName" type="text" placeholder="username (*)"
                            class="form-control form-control-sm" id="userName"
                            name="userName"/>
            </div>
        </div>

        <div class="row mb-3">
            <label for="password" class="col-sm-2 col-form-label">
                Password:
            </label>
            <div class="col-sm-2">
                <form:errors path="password" cssClass="error"/>
                <form:input path="password" type="password"
                            placeholder="username (*)"
                            class="form-control form-control-sm"
                            id="password" name="password"/>
            </div>
        </div>

        <div class="row mb-3">
            <label for="matchingPassword" class="col-sm-2 col-form-label">
                Confirm password:
            </label>
            <div class="col-sm-2">
                <form:errors path="matchingPassword" cssClass="error"/>
                <form:input path="matchingPassword" type="password"
                            placeholder="confirm password (*)"
                            class="form-control form-control-sm" id="firstName"
                            name="matchingPassword"/>
            </div>
        </div>

        <div class="row mb-3">
            <label for="firstName" class="col-sm-2 col-form-label">First
                Name:</label>
            <div class="col-sm-2">
                <form:input path="firstName" type="text"
                            class="form-control form-control-sm" id="firstName"
                            name="firstName"/>
            </div>
            <form:errors path="firstName" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="lastName" class="col-sm-2 col-form-label">Last
                Name:</label>
            <div class="col-sm-2">
                <form:input path="lastName" type="text"
                            class="form-control form-control-sm" id="lastName"
                            name="lastName"/>
            </div>
            <form:errors path="lastName" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current City:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id"
                             cssClass="form-control form-control-sm">
                    <c:if test="${empty userDto.currentCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id"
                                  itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <button type="submit" class="btn btn-sm btn-primary">Save</button>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/drivers/list/1"
           role="button">Back</a>
    </form:form>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>

