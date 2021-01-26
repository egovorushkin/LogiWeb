<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../views/fragments/before-title-main.jsp"/>

<title>My Information</title>

<jsp:include page="../../views/fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-auto ml-sm-auto col-lg-10 px-4">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">My Information</h1>
    </div>
    <form:form modelAttribute="user" action="${pageContext.request.contextPath}/driver/${user.id}">
        <div class="row mb-3">
            <label for="personalNumber" class="col-sm-2 col-form-label">Personal Number:</label>
            <div class="col-sm-2 ">
                <form:input path="personalNumber" type="text" class="form-control form-control-sm" id="personalNumber" name="personalNumber"
                            disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="username" class="col-sm-2 col-form-label">Username:</label>
            <div class="col-sm-2">
                <form:input path="username" type="text" class="form-control form-control-sm" id="username" name="username"
                            disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="firstName" class="col-sm-2 col-form-label">First Name:</label>
            <div class="col-sm-2">
                <form:input path="firstName" class="form-control form-control-sm" id="firstName" name="firstName"
                            disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="lastName" class="col-sm-2 col-form-label">Last Name:</label>
            <div class="col-sm-2">
                <form:input path="lastName" class="form-control form-control-sm" id="lastName"
                            name="lastName" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="workedHoursPerMonth" class="col-sm-2 col-form-label">Worked hours:</label>
            <div class="col-sm-2">
                <form:input path="workedHoursPerMonth" class="form-control form-control-sm" id="workedHoursPerMonth"
                            name="workedHoursPerMonth" readonly="true"/>
            </div>
        </div>

        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:input path="status.name" class="form-control form-control-sm" id="status"
                            name="status" readonly="true"/>
            </div>
        </div>

        <!-- construct an "update" link with cargo id -->
        <c:url var="updateLink" value="/cargoes/edit">
            <c:param name="cargoId" value="${user.id}"/>
        </c:url>

        <a class="btn btn-sm btn-success" href="${updateLink}" role="button">Edit</a>
    </form:form>

</main>

<jsp:include page="../../views/fragments/bootstrap-core-js-main.jsp"/>

