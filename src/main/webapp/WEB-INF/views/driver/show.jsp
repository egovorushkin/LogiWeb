<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../views/fragments/before-title-main.jsp"/>

<title>Order</title>

<jsp:include page="../../views/fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4"><em class="fas fa-clipboard-list"></em> | Order №${userOrder.id}</h4>
    </div>

    <form:form modelAttribute="userOrder"
               action="${pageContext.request.contextPath}/orders/${userOrder.id}">

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
        <div class="row mb-3">
            <label for="cargoWeight" class="col-sm-2 col-form-label">Cargo Weight:</label>
            <div class="col-sm-2">
                <form:input path="cargo.weigh" type="text"
                            class="form-control form-control-sm" id="cargoWeight"
                            name="cargoWeight" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="cargoStatus" class="col-sm-2 col-form-label">Cargo Status:</label>
            <div class="col-sm-2">
                <form:input path="cargo.status" type="text"
                            class="form-control form-control-sm" id="cargoStatus"
                            name="cargoStatus" readonly="true"/>
            </div>
        </div>

        <c:url var="updateLink" value="/orders/edit-user-order">
            <c:param name="orderId" value="${userOrder.id}"/>
        </c:url>

        <a class="btn btn-success btn-sm"
           href="${updateLink}" role="button">Edit</a>
        <a class="btn btn-secondary btn-sm"
           href="${pageContext.request.contextPath}/driver/orders" role="button">Back</a>
    </form:form>
</main>

<jsp:include page="../../views/fragments/bootstrap-core-js-main.jsp"/>

