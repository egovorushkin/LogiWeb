<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- construct an "delete" link with order id -->
<c:url var="deleteLink" value="/orders/delete">
    <c:param name="orderId" value="${order.id}"/>
</c:url>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Edit Order</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Edit Order</h1>
    </div>

    <form:form modelAttribute="order" action="${pageContext.request.contextPath}/orders/update" method="post">
        <form:hidden path="id"/>
        <div class="row mb-3">
            <label for="id" class="col-sm-2 col-form-label">Unique Number:</label>
            <div class="col-sm-3">
                <input type="text" class="form-control form-control-sm" id="id" name="id" value="${order.id}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-3">
                <form:select class="form-control form-control-sm" path="status" id="orderStatus"
                             name="orderStatus">
                    <form:options itemValue="name" itemLabel="name" items="${statuses}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Waypoint List:</label>
            <div class="col-sm-3">
                <form:select path="waypointList.id" cssClass="form-control form-control-sm">
                    <form:options items="${waypointLists}" itemValue="id" itemLabel="id"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Truck:</label>
            <div class="col-sm-3">
                <form:select path="truck.registrationNumber" cssClass="form-control form-control-sm">
                    <form:options items="${trucks}" itemValue="id" itemLabel="registrationNumber"/>
                </form:select>
            </div>
        </div>

        <button type="submit" class="btn btn-primary btn-sm">Save</button>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this order?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/orders/list"
           role="button">Cancel</a>
    </form:form>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>




