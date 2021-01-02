<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- construct an "delete" link with order id -->
<c:url var="deleteLink" value="/orders/delete">
    <c:param name="orderId" value="${order.id}"/>
</c:url>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Truck</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 m-2">

    <div class="page-header">
        <h1>Order</h1>
        <hr>
    </div>

    <form modelAttribute="order" action="${pageContext.request.contextPath}/orders/${order.id}">
        <form:hidden path="id"/>
        <div class="row mb-3">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Unique Number:</label>
            <div class="col-sm-2">
                <input type="text" class="form-control form-control-sm" id="registrationNumber"
                       name="registrationNumber"
                       value="${order.uniqueNumber}">
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="orderStatus" id="orderStatus"
                             name="orderStatus">
                    <form:options itemValue="name" itemLabel="name" items="${statuses}"/>
                </form:select>
            </div>
        </div>

        <a class="btn btn-success" href="${pageContext.request.contextPath}/orders/edit" role="button">Edit</a>
        <a class="btn btn-secondary" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this order?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/orders/list" role="button">Back</a>
    </form>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

