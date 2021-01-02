<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Truck</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<!-- construct an "delete" link with driver id -->
<c:url var="deleteLink" value="/drivers/delete">
    <c:param name="driverId" value="${driver.id}"/>
</c:url>
<!-- construct an "update" link with driver id -->
<c:url var="updateLink" value="/drivers/edit">
    <c:param name="driverId" value="${driver.id}"/>
</c:url>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 m-2">

    <div class="page-header">
        <h1>Truck</h1>
        <hr>
    </div>

    <form:form modelAttribute="driver" action="${pageContext.request.contextPath}/drivers/${driver.id}">
        <form:hidden path="id"/>
        <div class="row mb-3">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration Number:</label>
            <div class="col-sm-2 ">
                <form:input path="registrationNumber" type="text" class="form-control form-control-sm"
                            id="registrationNumber"
                            name="registrationNumber" disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="teamSize" class="col-sm-2 col-form-label">Team Size:</label>
            <div class="col-sm-2">
                <form:input path="teamSize" type="number" class="form-control form-control-sm" id="teamSize"
                            name="teamSize" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity (kg):</label>
            <div class="col-sm-2">
                <form:input path="capacity" type="number" class="form-control form-control-sm" id="capacity"
                            name="capacity" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="status" id="status" name="status"
                             disabled="true">
                    <form:options itemValue="name" itemLabel="name" items="${statuses}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current City:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id" cssClass="form-control form-control-sm" disabled="true">
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>

        <a class="btn btn-sm btn-success" href="${updateLink}" role="button">Edit</a>
        <a class="btn btn-sm btn-secondary" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this driver?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/drivers/list"
           role="button">Back</a>
    </form:form>

    <div class="page-header">
        <hr>
        <h3>Current Drivers</h3>
        <hr>
    </div>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

