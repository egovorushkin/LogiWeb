<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Truck</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
    align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4"><em class="fas fa-truck-moving"></em> |
            Truck №${truck.registrationNumber}
        </h4>
    </div>

    <form:form modelAttribute="truck"
               action="${pageContext.request.contextPath}/trucks/${truck.id}">

        <form:hidden path="id"/>

        <div class="row mb-3">
            <label for="registrationNumber" class="col-sm-2 col-form-label">
                Registration №:
            </label>
            <div class="col-sm-2 ">
                <form:input path="registrationNumber" type="text"
                            class="form-control form-control-sm"
                            id="registrationNumber"
                            name="registrationNumber" disabled="true"/>
            </div>
        </div>
        <fieldset class="form-group">
            <div class="row">
                <legend class="col-form-label col-sm-2 pt-0">Team Size:</legend>
                <div class="col-sm-10">
                    <div class="form-check form-check-inline">
                        <c:choose>
                            <c:when test="${truck.teamSize == 1}">
                                1 / ${numberOfDrivers}
                            </c:when>
                            <c:otherwise>
                                ${truck.teamSize} / ${numberOfDrivers}
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </fieldset>
        <div class="row mb-3">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity
                (kg):</label>
            <div class="col-sm-2">
                <form:input path="capacity" type="number"
                            class="form-control form-control-sm" id="capacity"
                            name="capacity" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="state" class="col-sm-2 col-form-label">State:</label>
            <div class="col-sm-2">
                <form:input path="state.name" type="text"
                            class="form-control form-control-sm" id="state"
                            name="state" readonly="true"/>
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
                <input value="${truck.currentCity.toString()}"
                       class="form-control form-control-sm" id="currentCity"
                       name="currentCity" disabled/>
            </div>
        </div>

        <c:url var="deleteLink" value="/trucks/delete">
            <c:param name="truckId" value="${truck.id}"/>
        </c:url>

        <c:url var="updateLink" value="/trucks/edit">
            <c:param name="truckId" value="${truck.id}"/>
        </c:url>

        <a class="btn btn-sm btn-success" href="${updateLink}" role="button">
            Edit/Add drivers
        </a>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this truck?')))
               return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/trucks/list" role="button">
            Back
        </a>
    </form:form>

    <div class="page-header">
        <h4>Current Drivers</h4>
        <hr>
    </div>

    <c:choose>
        <c:when test="${empty currentDrivers}">
            <h6>No drivers has been assigned for this truck yet</h6>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-responsive-sm table-striped
                            table-bordered table-sm">
                <caption></caption>
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Personal №</th>
                    <th scope="col">Worked Hours / Month</th>
                    <th scope="col">Current Status</th>
                    <th scope="col">Current City</th>
                    <th scope="col">Unbind from truck</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${currentDrivers}" var="currentDriver">
                    <tr class='table-row'
                        data-href='${pageContext.request.contextPath}/drivers/${currentDriver.id}'>
                        <td class="align-middle"><em class="fas fa-user"></em></td>
                        <td class="align-middle">${currentDriver.firstName}</td>
                        <td class="align-middle">${currentDriver.lastName}</td>
                        <td class="align-middle">${currentDriver.personalNumber}</td>
                        <td class="align-middle">${currentDriver.workedHoursPerMonth}</td>
                        <td class="align-middle">${currentDriver.status.toString()}</td>
                        <td class="align-middle">${currentDriver.currentCity.name}</td>

                        <c:url var="unbindDriverLink" value="/trucks/unbind-driver">
                            <c:param name="truckId" value="${truck.id}"/>
                            <c:param name="driverId" value="${currentDriver.id}"/>
                        </c:url>

                        <td><a class="nav-link" href="${unbindDriverLink}"
                               onclick="if (!(confirm('Are you sure you want to ' +
                                'unbind this driver from truck?'))) return false">
                            <em class="fas fa-minus" style="color: red"></em></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>