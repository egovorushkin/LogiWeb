<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>New Order</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><em class="fas fa-clipboard-list"></em> | Create New Order</h4>
    </div>

    <form:form modelAttribute="order"
               action="${pageContext.request.contextPath}/orders/save"
               method="post">
        <div class="row mb-3">
            <label for="fromCity" class="col-sm-2 col-form-label">From City</label>
            <div class="col-sm-3">
                <form:input path="fromCity" type="text"
                            class="form-control form-control-sm" id="fromCity"
                            name="fromCity"/>
            </div>
            <form:errors path="fromCity" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="toCity" class="col-sm-2 col-form-label">To City</label>
            <div class="col-sm-3">
                <form:input path="toCity" type="text"
                            class="form-control form-control-sm" id="tCity"
                            name="tCity"/>
            </div>
            <form:errors path="toCity" cssClass="alert alert-danger"/>
        </div>

        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Cargo:</label>
            <div class="col-sm-3">
                <form:select path="cargo.id"
                             cssClass="form-control form-control-sm">
                    <c:if test="${empty order.cargo}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cargoes}" itemValue="id"/>
                </form:select>
            </div>
        </div>
        <button type="submit" class="btn btn-sm btn-primary">Save</button>
        <a class="btn btn-sm btn-secondary"
           href="${pageContext.request.contextPath}/orders/list" role="button">Back</a>
    </form:form>
</main>
</div>
</div>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>

