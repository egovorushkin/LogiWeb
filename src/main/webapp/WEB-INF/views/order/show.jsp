<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- construct an "delete" link with order id -->
<c:url var="deleteLink" value="/orders/delete">
    <c:param name="orderId" value="${order.id}"/>
</c:url>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Order</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Order</h1>
    </div>

    <form:form modelAttribute="order"
               action="${pageContext.request.contextPath}/orders/${order.id}">
        <div class="row mb-3">
            <label for="id" class="col-sm-2 col-form-label">Unique
                Number:</label>
            <div class="col-sm-2">
                <input type="text" class="form-control form-control-sm" id="id"
                       name="id" value="${order.id}" readonly>
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
            <label for="truck" class="col-sm-2 col-form-label">Truck:</label>
            <div class="col-sm-2">
                <form:input path="truck.registrationNumber" type="text"
                            class="form-control form-control-sm" id="truck"
                            name="truck" readonly="true"/>
            </div>
        </div>

        <a class="btn btn-success btn-sm"
           href="${pageContext.request.contextPath}/orders/edit" role="button">Edit</a>
        <a class="btn btn-danger btn-sm" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this order?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-secondary btn-sm"
           href="${pageContext.request.contextPath}/orders/list" role="button">Back</a>

        <div>
            <h3>Waypoint List</h3>
            <hr>
        </div>
        <table class="table table-responsive-sm table-hover table-striped table-bordered table-sm">
            <thead>
            <tr>
                <th>Unique Number</th>
                <th>From City</th>
                <th>To City</th>
                <th>Cargo</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <tr class='table-row'>
                <td class="align-middle"></td>
                <td class="align-middle"></td>
                <td class="align-middle"></td>
                <td class="align-middle"></td>
                <td class="align-middle"></td>
            </tr>
            </tbody>
        </table>
    </form:form>

</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

