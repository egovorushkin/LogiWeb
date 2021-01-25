<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>New Truck</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4">Create New Truck</h4>
    </div>

    <form:form modelAttribute="truck"
               action="${pageContext.request.contextPath}/trucks/save"
               method="post">
        <div class="row mb-3">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration №:</label>
            <div class="col-sm-2">
                <form:input path="registrationNumber" type="text"
                            class="form-control form-control-sm"
                            id="registrationNumber" name="registrationNumber"/>
            </div>
            <form:errors path="registrationNumber"
                         cssClass="alert alert-danger"/>
        </div>
        <formfieldset class="form-group">
            <div class="row">
                <legend class="col-form-label col-sm-2 pt-0">Team Size:</legend>
                <div class="col-sm-10">
                    <div class="form-check form-check-inline">
                        <form:radiobutton path="teamSize"
                                          class="form-check-input"
                                          name="oneDriver" value="1"
                                          id="oneDriver" checked="true"/>
                        <label class="form-check-label" for="oneDriver">
                            1
                        </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <form:radiobutton path="teamSize"
                                          class="form-check-input"
                                          name="twoDrivers" value="2"
                                          id="twoDrivers"/>
                        <label class="form-check-label" for="twoDrivers">
                            2
                        </label>
                    </div>
                </div>
            </div>
        </formfieldset>
        <div class="row mb-3">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity
                (kg):</label>
            <div class="col-sm-2">
                <form:input path="capacity" class="form-control form-control-sm" id="capacity"
                            name="capacity"/>
            </div>
            <form:errors path="capacity" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Condition:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="state"
                             id="state" name="name">
                    <form:options itemValue="title" itemLabel="name"
                                  items="${states}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current City:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id"
                             cssClass="form-control form-control-sm">
                    <c:if test="${empty truck.currentCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id"
                                  itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <button type="submit" class="btn btn-sm btn-primary">Save</button>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/trucks/list" role="button">Back</a>
    </form:form>

    <c:choose>
        <c:when test="${empty availableDrivers}">
            <p>No available drivers fo this truck</p>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-striped table-bordered">
                <thead>
                <tr>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Personal №</th>
                    <th scope="col">Worked Hours / Month</th>
                    <th scope="col">Status</th>
                    <th scope="col">City</th>
                    <th scope="col">Truck</th>
                    <th scope="col">Add To Truck</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${availableDrivers}"
                           var="availableDriver">
                    <tr class='table-row'
                        data-href='${pageContext.request.contextPath}/drivers/${availableDriver.id}'>
                        <td class="align-middle">${availableDriver.firstName}</td>
                        <td class="align-middle">${availableDriver.lastName}</td>
                        <td class="align-middle">${availableDriver.personalNumber}</td>
                        <td class="align-middle">${availableDriver.workedHoursPerMonth}</td>
                        <td class="align-middle">${availableDriver.status.toString()}</td>
                        <td class="align-middle">${availableDriver.currentCity.name}</td>
                        <td class="align-middle">${availableDriver.truck.registrationNumber}</td>

                        <!-- construct an "add" link with order id -->
                        <c:url var="addDriverLink" value="/drivers/add-driver">
                            <%--                                <c:param name="availableDriverId" value="${availableDrivers.id}"/>--%>
                            <c:param name="truckId" value="${truck.id}"/>
                            <c:param name="driverId" value="${availableDriver.id}"/>
                        </c:url>

                        <td><a class="nav-link" href="${addDriverLink}"><i class="fas fa-plus" style="color: #008000"></i></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>

