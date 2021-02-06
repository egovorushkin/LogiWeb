<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>New Order</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><em class="fas fa-clipboard-list"></em> | Create New Order</h4>
    </div>

    <form:form modelAttribute="order" action="${pageContext.request.contextPath}/orders/save" method="post">

<%--        <div class="row mb-3">--%>
<%--            <label class="col-sm-2 col-form-label">Status:</label>--%>
<%--            <div class="col-sm-2">--%>
<%--                <form:select path="fromCity.id" cssClass="form-control form-control-sm">--%>
<%--                    <c:if test="${empty order.fromCity}">--%>
<%--                        <form:option value="" disabled="true" selected="true"/>--%>
<%--                    </c:if>--%>
<%--                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>--%>
<%--                </form:select>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">From City:</label>
            <div class="col-sm-2">
                <form:select path="fromCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty order.fromCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">To City:</label>
            <div class="col-sm-2">
                <form:select path="toCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty order.toCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label for="distance" class="col-sm-2 col-form-label">Distance (km):</label>
            <div class="col-sm-2">
                <form:input path="distance" type="number"
                            class="form-control form-control-sm" id="distance"
                            name="distance"/>
            </div>
            <form:errors path="distance" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Cargo:</label>
            <div class="col-sm-2">
                <form:select path="cargo.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty order.cargo}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cargoes}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <button type="submit" class="btn btn-sm btn-primary">Save</button>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/orders/list" role="button">Back</a>
    </form:form>
</main>
</div>
</div>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>

