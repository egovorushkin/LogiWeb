<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../views/fragments/before-title-main.jsp"/>

<title>My Information</title>

<jsp:include page="../../views/fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-auto ml-sm-auto col-lg-10 px-4">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><i class="far fa-address-card"></i> | My Information</h4>
    </div>
    <form:form modelAttribute="user" action="${pageContext.request.contextPath}/driver/update-status" method="post">
        <div class="row mb-3">
            <label for="personalNumber" class="col-sm-2 col-form-label">Personal №:</label>
            <div class="col-sm-2 ">
                <form:input path="personalNumber" type="text" class="form-control form-control-sm" id="personalNumber" name="personalNumber"
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
            <label for="workedHoursPerMonth" class="col-sm-2 col-form-label">Hours worked this month:</label>
            <div class="col-sm-2">
                <form:input path="workedHoursPerMonth" class="form-control form-control-sm" id="workedHoursPerMonth"
                            name="workedHoursPerMonth" readonly="true"/>
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

        <!-- construct an "update" link with cargo id -->
        <c:url var="updateLink" value="/driver/update-status">
            <c:param name="userId" value="${user.id}"/>
        </c:url>

        <a class="btn btn-sm btn-success" role="button" href="${updateLink}">Save Status</a>
    </form:form>

    <div>
        <h4>My colleague(s)</h4>
    </div>

    <c:choose>
        <c:when test="${empty user.truck.currentDrivers}">
            <h6>No colleague(s).</h6>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
                <thead>
                <tr>
                    <th scope="col">Personal №</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Worked Hours / Month</th>
                    <th scope="col">Current Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${user.truck.currentDrivers}" var="currentDriver">
                    <tr>
                        <td class="align-middle">${currentDriver.personalNumber}</td>
                        <td class="align-middle">${currentDriver.firstName}</td>
                        <td class="align-middle">${currentDriver.lastName}</td>
                        <td class="align-middle">${currentDriver.workedHoursPerMonth}</td>
                        <td class="align-middle">${currentDriver.status.toString()}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

</main>

<jsp:include page="../../views/fragments/bootstrap-core-js-main.jsp"/>

