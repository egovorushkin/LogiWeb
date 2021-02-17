<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Driver</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><em class="fas fa-user-alt"></em> |
            Driver №${driver.id}
        </h4>
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
            <label for="id" class="col-sm-2 col-form-label">
                Personal №:
            </label>
            <div class="col-sm-2">
                <form:input path="id" type="number"
                            class="form-control form-control-sm"
                            id="id"
                            name="id"
                            value="${driver.id}" disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="workedHoursPerMonth" class="col-sm-2 col-form-label">
                Worked Hours:
            </label>
            <div class="col-sm-2">
                <input class="form-control form-control-sm" disabled
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
                <input value="${driver.currentCity.name}"
                       class="form-control form-control-sm" id="currentCity"
                       name="currentCity" disabled/>
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

        <a class="btn btn-sm btn-success" href="${updateLink}" role="button">
            Edit / Set Truck
        </a>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this driver?')))
               return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/drivers/list"
           role="button">Back</a>
    </form:form>

    <hr>

    <div class="page-header">
        <h4>Current Truck</h4>
    </div>

    <c:choose>
        <c:when test="${empty driver.truck}">
            <h6>No truck has been assigned for this driver yet.</h6>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-responsive-sm table-striped
                            table-bordered table-sm">
                <caption></caption>
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">Registration №</th>
                    <th scope="col">Capacity (kg)</th>
                    <th scope="col">State</th>
                    <th scope="col">Status</th>
                    <th scope="col">Current City</th>
                    <th scope="col">Unbind truck</th>
                </tr>
                </thead>
                <tbody>
                    <tr class='table-row'
                        data-href='${pageContext.request.contextPath}/trucks/${driver.truck.id}'>
                        <td class="align-middle">
                            <em class="fas fa-truck-moving"></em>
                        </td>
                        <td class="align-middle">
                                ${driver.truck.registrationNumber}
                        </td>
                        <td class="align-middle">
                                ${driver.truck.capacity}
                        </td>
                        <td class="align-middle">
                                ${driver.truck.state.toString()}
                        </td>
                        <td class="align-middle">
                                ${driver.truck.status.toString()}
                        </td>
                        <td class="align-middle">
                                ${driver.truck.currentCity.name}
                        </td>

                        <c:url var="unbindTruckLink" value="/drivers/unbind-truck">
                            <c:param name="truckId" value="${driver.truck.id}"/>
                            <c:param name="driverId" value="${driver.id}"/>
                        </c:url>

                        <td><a class="nav-link" href="${unbindTruckLink}">
                            <em class="fas fa-minus" style="color: red"></em></a></td>
                    </tr>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>

