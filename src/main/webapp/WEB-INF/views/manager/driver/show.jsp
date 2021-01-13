<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Driver</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Driver</h1>
    </div>

    <form:form modelAttribute="driver"
               action="${pageContext.request.contextPath}/drivers/${driver.id}">
        <form:hidden path="id"/>
        <div class="row mb-3">
            <label for="firstName" class="col-sm-2 col-form-label">First
                Name:</label>
            <div class="col-sm-2">
                <form:input path="firstName" type="text"
                            class="form-control form-control-sm" id="firstName"
                            name="firstName"
                            value="${driver.firstName}" disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="lastName" class="col-sm-2 col-form-label">Last
                Name:</label>
            <div class="col-sm-2">
                <form:input path="lastName" type="text"
                            class="form-control form-control-sm" id="lastName"
                            name="lastName"
                            value="${driver.lastName}" disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="personalNumber" class="col-sm-2 col-form-label">Personal
                Number:</label>
            <div class="col-sm-2">
                <form:input path="personalNumber" type="number"
                            class="form-control form-control-sm"
                            id="personalNumber"
                            name="personalNumber"
                            value="${driver.personalNumber}" disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="workedHoursPerMonth" class="col-sm-2 col-form-label">Worked
                Hours / Month:</label>
            <div class="col-sm-2">
                <input class="form-control form-control-sm" disabled="true"
                       id="workedHoursPerMonth"
                       name="workedHoursPerMonth" type="number"
                       value="${driver.workedHoursPerMonth}">
            </div>
        </div>
        <div class="row mb-3">
            <label for="status" class="col-sm-2 col-form-label">Status:</label>
            <div class="col-sm-2">
                <form:input path="status.name" type="text"
                            class="form-control form-control-sm" id="status"
                            name="status" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="currentCity" class="col-sm-2 col-form-label">Current
                City:</label>
            <div class="col-sm-2">
                <input value="${driver.currentCity.toString()}"
                       class="form-control form-control-sm" id="currentCity"
                       name="currentCity" disabled/>
            </div>
        </div>

        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Truck:</label>
            <div class="col-sm-2">
                <c:choose>
                    <c:when test="${empty driver.truck}">
                        <input value="None"
                               class="form-control form-control-sm" id="none"
                               name="none" disabled/>
                    </c:when>
                    <c:otherwise>
                        <input value="${driver.truck.registrationNumber}"
                               class="form-control form-control-sm"
                               id="currentTruck"
                               name="currentTruck" disabled/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- construct an "delete" link with driver id -->
        <c:url var="deleteLink" value="/drivers/delete">
            <c:param name="driverId" value="${driver.id}"/>
        </c:url>
        <!-- construct an "update" link with driver id -->
        <c:url var="updateLink" value="/drivers/edit">
            <c:param name="driverId" value="${driver.id}"/>
        </c:url>

        <a class="btn btn-sm btn-success" href="${updateLink}" role="button">Edit</a>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this driver?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/drivers/list"
           role="button">Back</a>
    </form:form>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>

