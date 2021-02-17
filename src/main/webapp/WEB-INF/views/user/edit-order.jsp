<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/before-title-main.jsp"/>

<title>Order №${userOrder.id}</title>

<jsp:include page="../fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
    align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><em class="fas fa-clipboard-list"></em> | Edit Order
            №${userOrder.id}
        </h4>
    </div>

    <form:form modelAttribute="userOrder"
               action="${pageContext.request.contextPath}/user/update-order-status"
               method="post">

        <form:hidden path="id"/>

        <div class="row mb-3">
            <label for="status" class="col-sm-2 col-form-label">Current
                Status:</label>
            <div class="col-sm-2">
                <c:choose>
                    <c:when test="${userOrder.status.title == 'COMPLETED'}">
                        <form:input path="status.name" type="text"
                                    class="form-control form-control-sm"
                                    id="status"
                                    name="status" readonly="true"/>
                    </c:when>
                    <c:otherwise>
                        <form:select class="form-control form-control-sm"
                                     path="status"
                                     id="status"
                                     name="status">
                            <form:options itemValue="title" itemLabel="name"
                                          items="${orderStatuses}"/>
                        </form:select>
                    </c:otherwise>
                </c:choose>
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
                <form:input path="distance" type="number"
                            class="form-control form-control-sm" id="distance"
                            name="distance" readonly="true"/>
            </div>
            <form:errors path="distance" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="duration" class="col-sm-2 col-form-label">Travel time
                (hr):</label>
            <div class="col-sm-2">
                <form:input path="duration" type="text"
                            class="form-control form-control-sm" id="duration"
                            name="duration" readonly="true"/>
            </div>
        </div>
        <c:choose>
            <c:when test="${userOrder.status.title == 'COMPLETED'}">
            </c:when>
            <c:otherwise>
                <button type="submit" class="btn btn-primary btn-sm">Update
                </button>
            </c:otherwise>
        </c:choose>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/user/orders"
           role="button">Back</a>
    </form:form>

</main>

<jsp:include page="../fragments/bootstrap-core-js-main.jsp"/>




