<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Edit Order</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><em class="fas fa-clipboard-list"></em> | Edit Order №${order.id}
        </h4>
    </div>

    <form:form modelAttribute="order"
               action="${pageContext.request.contextPath}/orders/update"
               method="post">

        <form:hidden path="id"/>

        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="status"
                             id="orderStatus"
                             name="orderStatus">
                    <form:options itemValue="title" itemLabel="name"
                                  items="${statuses}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label for="fromCity" class="col-sm-2 col-form-label">From
                City:</label>
            <div class="col-sm-2">
                <form:input path="fromCity" type="text"
                            class="form-control form-control-sm" id="fromCity"
                            name="fromCity"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="toCity" class="col-sm-2 col-form-label">To City:</label>
            <div class="col-sm-2">
                <form:input path="toCity" type="text"
                            class="form-control form-control-sm" id="toCity"
                            name="toCity"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="distance" class="col-sm-2 col-form-label">Distance
                (km):</label>
            <div class="col-sm-2">
                <form:input path="distance" type="number"
                            class="form-control form-control-sm" id="distance"
                            name="distance" readonly="true"/>
            </div>
            <form:errors path="distance" cssClass="alert alert-danger"/>
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
            <label class="col-sm-2 col-form-label">Cargo:</label>
            <div class="col-sm-2">
                <form:select path="cargo.id"
                             cssClass="form-control form-control-sm">
                    <c:if test="${empty order.cargo}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cargoes}" itemValue="id"
                                  itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Truck:</label>
            <div class="col-sm-2">
                <c:choose>
                    <c:when test="${empty order.truck}">
                        <input value="None"
                               class="form-control form-control-sm" id="none"
                               name="none" disabled/>
                    </c:when>
                    <c:otherwise>
                        <input value="${order.truck.registrationNumber}"
                               class="form-control form-control-sm"
                               id="currentTruck"
                               name="currentTruck" disabled/>
                    </c:otherwise>
                </c:choose>
            </div>
            <hr/>
        </div>

        <c:url var="deleteLink" value="/orders/delete">
            <c:param name="orderId" value="${order.id}"/>
        </c:url>

        <button type="submit" class="btn btn-primary btn-sm">Update
        </button>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this order?')))
               return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/orders/list/1"
           role="button">Cancel</a>
    </form:form>

    <hr>

    <h4>Available Trucks</h4>
    <c:choose>
        <c:when test="${empty availableTrucks}">
            <h6>No available trucks fo this order</h6>
            <a class="btn btn-sm btn-success"
               href="${pageContext.request.contextPath}/trucks/create"
               role="button">Add
                Truck</a>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-responsive-sm table-striped
            table-bordered table-sm">
                <thead>
                <tr>
                    <th scope="col">Registration №</th>
                    <th scope="col">Capacity (kg)</th>
                    <th scope="col">State</th>
                    <th scope="col">Status</th>
                    <th scope="col">Current City</th>
                    <th scope="col">Bind truck</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${availableTrucks}"
                           var="availableTruck">
                    <tr class='table-row'
                        data-href='${pageContext.request.contextPath}/trucks/${availableTruck.id}'>
                        <td class="align-middle">
                                ${availableTruck.registrationNumber}
                        </td>
                        <td class="align-middle">
                                ${availableTruck.capacity}
                        </td>
                        <td class="align-middle">
                                ${availableTruck.state.toString()}
                        </td>
                        <td class="align-middle">
                                ${availableTruck.status.toString()}
                        </td>
                        <td class="align-middle">
                                ${availableTruck.currentCity.name}
                        </td>

                        <c:url var="bindTruckLink" value="/orders/bind-truck">
                            <c:param name="truckId"
                                     value="${availableTruck.id}"/>
                            <c:param name="orderId" value="${order.id}"/>
                        </c:url>

                        <td><a class="nav-link" href="${bindTruckLink}">
                            <em class="fas fa-plus" style="color: limegreen"></em></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>




