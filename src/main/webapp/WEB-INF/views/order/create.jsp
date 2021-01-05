<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>New Order</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 m-2">

    <div class="page-header">
        <h2>Create New Order</h2>
        <hr>
    </div>

    <form:form modelAttribute="order" action="${pageContext.request.contextPath}/orders/save" method="post">
        <div class="row mb-3">
            <label for="uniqueNumber" class="col-sm-2 col-form-label">Unique Number:</label>
            <div class="col-sm-2">
                <form:input path="uniqueNumber" type="text" class="form-control form-control-sm" id="uniqueNumber"
                            name="uniqueNumber"/>
            </div>
            <form:errors path="uniqueNumber" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Cargo:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty truck.currentCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
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
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">From City:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty truck.currentCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">To City:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty truck.currentCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Type:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty truck.currentCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Truck:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty truck.currentCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <button type="submit" class="btn btn-sm btn-primary">Save</button>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/orders/list" role="button">Back</a>
    </form:form>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

