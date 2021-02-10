<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Order</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4"><em class="fas fa-clipboard-list"></em> | Order
            №${order.id}</h4>
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
        <h6>Cargo</h6>
        <hr>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Cargo:</label>
            <div class="col-sm-2">
                <form:input path="cargo.name" class="form-control
                        form-control-sm" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="cargoWeight" class="col-sm-2 col-form-label">
                Cargo Weight:
            </label>
            <div class="col-sm-2">
                <form:input path="cargo.weight" type="text"
                            class="form-control form-control-sm"
                            id="cargoWeight"
                            name="cargoWeight" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Cargo
                Status:</label>
            <div class="col-sm-2">
                <form:input path="cargo.status.name" class="form-control
                        form-control-sm" readonly="true"/>
            </div>
        </div>

        <c:url var="deleteLink" value="/orders/delete">
            <c:param name="orderId" value="${order.id}"/>
        </c:url>

        <c:url var="updateLink" value="/orders/edit">
            <c:param name="orderId" value="${order.id}"/>
        </c:url>

        <c:choose>
            <c:when test="${order.status.title == 'COMPLETED'}">
            </c:when>
            <c:otherwise>
                <a class="btn btn-sm btn-success" href="${updateLink}"
                   role="button">
                    Edit
                </a>
            </c:otherwise>
        </c:choose>

        <a class="btn btn-danger btn-sm" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this order?')))
               return false"
           role="button">Delete</a>
        <a class="btn btn-secondary btn-sm"
           href="${pageContext.request.contextPath}/orders/list" role="button">
            Back
        </a>
    </form:form>

    <div class="page-header">
        <h4>The truck carrying out the order</h4>
    </div>

    <c:choose>
        <c:when test="${empty order.truck}">
            <h6>No truck has been assigned for this order yet.</h6>
            <a class="btn btn-sm btn-success"
               href="${pageContext.request.contextPath}/trucks/create"
               role="button">AddTruck</a>
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
                    data-href='${pageContext.request.contextPath}/trucks/${order.truck.id}'>
                    <td class="align-middle"><em class="fas fa-truck-moving"></em>
                    </td>
                    <td class="align-middle">${order.truck.registrationNumber}</td>
                    <td class="align-middle">${order.truck.capacity}</td>
                    <td class="align-middle">${order.truck.state.toString()}</td>
                    <td class="align-middle">${order.truck.status.toString()}</td>
                    <td class="align-middle">${order.truck.currentCity.name}</td>

                    <c:url var="unbindOrderLink" value="/orders/unbind-truck">
                        <c:param name="truckId" value="${order.truck.id}"/>
                        <c:param name="orderId" value="${order.id}"/>
                    </c:url>

                    <td><a class="nav-link" href="${unbindOrderLink}"><em
                            class="fas fa-minus" style="color: red"></em></a>
                    </td>
                </tr>
                </tbody>
            </table>

            <hr>

            <div class="page-header">
                <h4>Drivers carrying out the order</h4>
            </div>

            <c:choose>
                <c:when test="${empty order.truck.currentDrivers}">
                    <h6>No drivers has been assigned for this order yet.</h6>
                    <a class="btn btn-sm  btn-success"
                       href="${pageContext.request.contextPath}/drivers/create"
                       role="button">Add Driver</a>
                </c:when>
                <c:otherwise>
                    <table class="table table-hover table-responsive-sm
                                    table-striped table-bordered table-sm">
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
                        <c:forEach items="${order.truck.currentDrivers}"
                                   var="currentDriver">
                            <tr class='table-row'
                                data-href='${pageContext.request.contextPath}/drivers/${currentDriver.id}'>
                                <td class="align-middle"><em
                                        class="fas fa-user-alt"></em></td>
                                <td class="align-middle">${currentDriver.firstName}</td>
                                <td class="align-middle">${currentDriver.lastName}</td>
                                <td class="align-middle">${currentDriver.id}</td>
                                <td class="align-middle">${currentDriver.workedHoursPerMonth}</td>
                                <td class="align-middle">${currentDriver.status.toString()}</td>
                                <td class="align-middle">${currentDriver.currentCity.name}</td>

                                <c:url var="unbindDriverLink"
                                       value="/trucks/unbind-driver">
                                    <c:param name="truckId"
                                             value="${order.truck.id}"/>
                                    <c:param name="driverId"
                                             value="${currentDriver.id}"/>
                                </c:url>

                                <td><a class="nav-link"
                                       href="${unbindDriverLink}"
                                       onclick="if (!(confirm
                                       ('Are you sure you want to unbind this ' +
                                        'driver from truck?'))) return false"><em
                                        class="fas fa-minus"
                                        style="color: red"></em></a>
                                </td>
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

