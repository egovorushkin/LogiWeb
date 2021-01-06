<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Truck</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Truck</h1>
    </div>

    <form:form modelAttribute="truck" action="${pageContext.request.contextPath}/trucks/${truck.id}">
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

        <!-- construct an "delete" link with truck id -->
        <c:url var="deleteLink" value="/trucks/delete">
            <c:param name="truckId" value="${truck.id}"/>
        </c:url>

        <!-- construct an "update" link with truck id -->
        <c:url var="updateLink" value="/trucks/edit">
            <c:param name="truckId" value="${truck.id}"/>
        </c:url>

        <a class="btn btn-sm btn-success" href="${updateLink}" role="button">Edit</a>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this truck?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/trucks/list" role="button">Back</a>
    </form:form>

    <!-- Show Current Drivers-->
    <div class="page-header">
        <h3>Current Drivers</h3>
        <hr>
    </div>
    <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
        <thead>
        <tr>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Personal Number</th>
            <th scope="col">Worked Hours / Month</th>
            <th scope="col">Current Status</th>
            <th scope="col">Current City</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${drivers}" var="driver">
            <tr class='table-row' data-href='${pageContext.request.contextPath}/drivers/${driver.id}'>
                <td class="align-middle">${driver.firstName}</td>
                <td class="align-middle">${driver.lastName}</td>
                <td class="align-middle">${driver.personalNumber}</td>
                <td class="align-middle">${driver.workedHoursPerMonth}</td>
                <td class="align-middle">${driver.driverStatus}</td>
                <td class="align-middle">${driver.currentCity.name}</td>

                        <!-- construct an "delete" link with driver id -->
                <c:url var="deleteLink" value="/drivers/delete">
                    <c:param name="driverId" value="${driver.id}"/>
                </c:url>
                        <!-- construct an "update" link with driver id -->
                <c:url var="updateLink" value="/drivers/edit">
                    <c:param name="driverId" value="${driver.id}"/>
                </c:url>

                <td><a class="nav-link" href="${updateLink}"><span data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this driver?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>