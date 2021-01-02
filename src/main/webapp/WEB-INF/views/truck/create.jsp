<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>New Truck</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 m-2">

    <div class="page-header">
        <h2>Create New Truck</h2>
        <hr>
    </div>

    <form:form modelAttribute="truck" action="${pageContext.request.contextPath}/trucks/save" method="post">
        <div class="row mb-3">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration Number:</label>
            <div class="col-sm-2">
                <form:input path="registrationNumber" type="text" class="form-control form-control-sm"
                            id="registrationNumber" name="registrationNumber"/>
            </div>
            <form:errors path="registrationNumber" cssClass="alert alert-danger"/>

        </div>
        <div class="row mb-3">
            <label for="teamSize" class="col-sm-2 col-form-label">Team Size:</label>
            <div class="col-sm-2">
                <form:input path="teamSize" type="number" class="form-control form-control-sm" id="teamSize"
                            name="teamSize"/>
            </div>
            <form:errors path="teamSize" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity (kg):</label>
            <div class="col-sm-2">
                <form:input path="capacity" type="number" class="form-control form-control-sm" id="capacity"
                            name="capacity"/>
            </div>
            <form:errors path="capacity" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="status" id="status" name="status">
                    <form:options itemValue="name" itemLabel="name" items="${statuses}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current City:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty truck.currentCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
            <form:errors path="currentCity" cssClass="alert alert-danger"/>
        </div>
        <button type="submit" class="btn btn-sm btn-primary">Save</button>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/trucks/list" role="button">Back</a>
    </form:form>
</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

