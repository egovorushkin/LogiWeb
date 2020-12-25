<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>New Driver</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>


<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 m-2">
    <form:form modelAttribute="driver" action="${pageContext.request.contextPath}/drivers/save" method="post">
        <div class="row mb-3">
            <label for="firstName" class="col-sm-2 col-form-label">First Name:</label>
            <div class="col-sm-2">
                <form:input path="firstName" type="text" class="form-control" id="firstName" name="firstName"/>
            </div>
            <form:errors path="firstName" cssClass="alert alert-danger"/>

        </div>
        <div class="row mb-3">
            <label for="lastName" class="col-sm-2 col-form-label">Last Name:</label>
            <div class="col-sm-2">
                <form:input path="lastName" type="text" class="form-control" id="lastName" name="lastName"/>
            </div>
            <form:errors path="lastName" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="personalNumber" class="col-sm-2 col-form-label">Personal Number:</label>
            <div class="col-sm-2">
                <form:input path="personalNumber" type="number" class="form-control" id="personalNumber"
                            name="personalNumber"/>
            </div>
            <form:errors path="personalNumber" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="workedHoursPerMonth" class="col-sm-2 col-form-label">Worked Hours / Month:</label>
            <div class="col-sm-2">
                <input type="workedHoursPerMonth" class="form-control" id="workedHoursPerMonth"
                       name="workedHoursPerMonth">
            </div>
        </div>
        <div class="row mb-3">
            <label for="driverStatus" class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-3">
                <input path="driverStatus" type="text" class="form-control" id="driverStatus" name="driverStatus"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="currentCity" class="col-sm-2 col-form-label">Current City:</label>
            <div class="col-sm-3">
                <input path="currentCity" type="text" class="form-control" id="currentCity" name="currentCity"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="currentTruck" class="col-sm-2 col-form-label">Current Truck:</label>
            <div class="col-sm-3">
                <input path="currentTruck" type="text" class="form-control" id="currentTruck" name="currentTruck"/>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/drivers/list" role="button">Back</a>
    </form:form>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

