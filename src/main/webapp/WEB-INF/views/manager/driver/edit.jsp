<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Edit Driver</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4"><i class="fas fa-user-edit"></i> | Edit Driver</h4>
    </div>

    <form:form modelAttribute="driver" action="${pageContext.request.contextPath}/drivers/update" method="post">
        <form:hidden path="id"/>
        <div class="row mb-3">
            <label for="firstName" class="col-sm-2 col-form-label">First Name:</label>
            <div class="col-sm-2">
                <form:input path="firstName" type="text" class="form-control form-control-sm" id="firstName"
                            name="firstName"
                            value="${driver.firstName}"/>
            </div>
            <form:errors path="firstName" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="lastName" class="col-sm-2 col-form-label">Last Name:</label>
            <div class="col-sm-2">
                <form:input path="lastName" type="text" class="form-control form-control-sm" id="lastName"
                            name="lastName"
                            value="${driver.lastName}"/>
            </div>
            <form:errors path="lastName" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="personalNumber" class="col-sm-2 col-form-label">Personal Number:</label>
            <div class="col-sm-2">
                <form:input path="personalNumber" type="number" class="form-control form-control-sm" id="personalNumber"
                            name="personalNumber" value="${driver.personalNumber}"/>
            </div>
            <form:errors path="personalNumber" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="workedHoursPerMonth" class="col-sm-2 col-form-label">Worked Hours / Month:</label>
            <div class="col-sm-2">
                <input class="form-control form-control-sm" id="workedHoursPerMonth" name="workedHoursPerMonth"
                       type="number" value="${driver.workedHoursPerMonth}">
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="status" id="driverStatus"
                             name="driverStatus">
                    <form:options itemValue="title" itemLabel="name" items="${statuses}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current City:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty driver.currentCity}">
                        <form:option value="${driver.currentCity.name}" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
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
            <hr/>
        </div>

        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Available Trucks:</label>
            <div class="col-sm-2">
                <form:select path="truck.id" cssClass="form-control form-control-sm">
                    <form:options items="${trucks}" itemValue="id" itemLabel="registrationNumber"/>
                </form:select>
            </div>
        </div>

        <button type="submit" class="btn btn-sm btn-primary">Update</button>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this driver?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/drivers/list"
           role="button">Cancel</a>
    </form:form>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>

