<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Order</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4"><i class="fas fa-clipboard-list"></i> | Order №${order.id}</h4>
    </div>

    <form:form modelAttribute="order"
               action="${pageContext.request.contextPath}/orders/${order.id}">

        <div class="row mb-3">
            <label for="status" class="col-sm-2 col-form-label">Status:</label>
            <div class="col-sm-2">
                <form:input path="status.name" type="text"
                            class="form-control form-control-sm" id="status"
                            name="status" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="fromCity" class="col-sm-2 col-form-label">From
                City:</label>
            <div class="col-sm-2">
                <form:input path="fromCity.name" type="text"
                            class="form-control form-control-sm" id="fromCity"
                            name="fromCity" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="toCity" class="col-sm-2 col-form-label">To City:</label>
            <div class="col-sm-2">
                <form:input path="toCity.name" type="text"
                            class="form-control form-control-sm" id="toCity"
                            name="toCity" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="distance" class="col-sm-2 col-form-label">Distance
                (km):</label>
            <div class="col-sm-2">
                <form:input path="distance" type="text"
                            class="form-control form-control-sm" id="distance"
                            name="distance" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="distance" class="col-sm-2 col-form-label">Travel time
                (hr):</label>
            <div class="col-sm-2">
                <form:input path="duration" type="text"
                            class="form-control form-control-sm" id="duration"
                            name="duration" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="cargo" class="col-sm-2 col-form-label">Cargo:</label>
            <div class="col-sm-2">
                <form:input path="cargo.name" type="text"
                            class="form-control form-control-sm" id="cargo"
                            name="cargo" readonly="true"/>
            </div>
        </div>

        <c:url var="deleteLink" value="/orders/delete">
            <c:param name="orderId" value="${order.id}"/>
        </c:url>

        <c:url var="updateLink" value="/orders/edit">
            <c:param name="orderId" value="${order.id}"/>
        </c:url>

        <a class="btn btn-success btn-sm"
           href="${updateLink}" role="button">Edit</a>
        <a class="btn btn-danger btn-sm" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this order?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-secondary btn-sm"
           href="${pageContext.request.contextPath}/orders/list" role="button">Back</a>
    </form:form>

    <div class="page-header">
        <h4>The truck carrying out the order</h4>
        <hr>
    </div>

    <c:choose>
        <c:when test="${empty order.truck}">
            <h6>No truck has been assigned for this order yet.</h6>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
                <thead>
                <tr>
                    <th scope="col">Registration Number</th>
                    <th scope="col">Team Size</th>
                    <th scope="col">Capacity (kg)</th>
                    <th scope="col">State</th>
                    <th scope="col">Status</th>
                    <th scope="col">Current City</th>
                    <th scope="col">Unbind truck</th>
                </tr>
                </thead>
                <tbody>
                <tr class='table-row'
                    data-href='${pageContext.request.contextPath}/trucks/${order.truck.id}'>
                    <td class="align-middle">${order.truck.registrationNumber}</td>
                    <td class="align-middle">${order.truck.teamSize}</td>
                    <td class="align-middle">${order.truck.capacity}</td>
                    <td class="align-middle">${order.truck.state.toString()}</td>
                    <td class="align-middle">${order.truck.status.toString()}</td>
                    <td class="align-middle">${order.truck.currentCity.name}</td>

                    <c:url var="unbindOrderLink" value="/orders/unbind-truck">
                        <c:param name="truckId" value="${order.truck.id}"/>
                        <c:param name="orderId" value="${order.id}"/>
                    </c:url>

                    <td><a class="nav-link" href="${unbindOrderLink}"><i class="fas fa-minus" style="color: red"></i></a></td>
                </tr>
                </tbody>
            </table>

            <hr>

            <div class="page-header">
                <h6>Drivers carrying out the order</h6>
            </div>

            <c:choose>
                <c:when test="${empty order.truck.currentDrivers}">
                    <h6>No drivers has been assigned for this order yet.</h6>
                </c:when>
                <c:otherwise>
                    <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
                        <thead>
                        <tr>
                            <th scope="col">First Name</th>
                            <th scope="col">Last Name</th>
                            <th scope="col">Personal Number</th>
                            <th scope="col">Worked Hours / Month</th>
                            <th scope="col">Current Status</th>
                            <th scope="col">Current City</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${order.truck.currentDrivers}" var="currentDriver">
                            <tr class='table-row'
                                data-href='${pageContext.request.contextPath}/drivers/${currentDriver.id}'>
                                <td class="align-middle">${currentDriver.firstName}</td>
                                <td class="align-middle">${currentDriver.lastName}</td>
                                <td class="align-middle">${currentDriver.personalNumber}</td>
                                <td class="align-middle">${currentDriver.workedHoursPerMonth}</td>
                                <td class="align-middle">${currentDriver.status.toString()}</td>
                                <td class="align-middle">${currentDriver.currentCity.name}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>

