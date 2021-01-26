<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/before-title-main.jsp"/>

<title>Order №${userOrder.id}</title>

<jsp:include page="../fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
    align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><em class="fas fa-clipboard-list"></em> | Edit Order №${userOrder.id}
        </h4>
    </div>

    <form:form modelAttribute="userOrder"
               action="${pageContext.request.contextPath}/orders/update"
               method="post">

        <form:hidden path="id"/>

        <div class="row mb-3">
            <label for="fromCity" class="col-sm-2 col-form-label">Current
                Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm"
                             path="fromCity.name"
                             id="fromCity"
                             name="fromCity">
                    <form:options itemValue="title" itemLabel="name"
                                  items="${orderStatuses}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">From City:</label>
            <div class="col-sm-2">
                <form:input path="toCity.name" type="text"
                            class="form-control form-control-sm" id="toCity"
                            name="toCity" readonly="true"/>
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
                <form:input path="distance" type="number"
                            class="form-control form-control-sm" id="distance"
                            name="distance" disabled="true"/>
            </div>
            <form:errors path="distance" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="distance" class="col-sm-2 col-form-label">~Travel time
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
            <label for="cargoWeight" class="col-sm-2 col-form-label">Cargo
                Weight:</label>
            <div class="col-sm-2">
                <form:input path="cargo.weight" type="text"
                            class="form-control form-control-sm"
                            id="cargoWeight"
                            name="cargoWeight" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="cargoStatus" class="col-sm-2 col-form-label">Cargo
                Status:</label>
            <div class="col-sm-2">
                <form:select path="cargo.status"
                             cssClass="form-control form-control-sm"
                             id="cargoStatus">
                    <form:options items="${cargoStatuses}" itemValue="title"
                                  itemLabel="name"/>
                </form:select>
            </div>
        </div>

        <button type="submit" class="btn btn-primary btn-sm">Update</button>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/orders/list"
           role="button">Cancel</a>
    </form:form>


</main>

<jsp:include page="../fragments/bootstrap-core-js-main.jsp"/>




