<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/before-title-main.jsp"/>

<title>Check Google Directions</title>

<jsp:include page="../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4>Check Google Directions</h4>
    </div>

    <form:form modelAttribute="dirReq" action="${pageContext.request.contextPath}/map/req"
               method="post">
        <div class="row mb-3">
            <label for="origin" class="col-sm-2 col-form-label">Origin:</label>
            <div class="col-sm-2">
                <form:input path="origin" type="text"
                            class="form-control form-control-sm" id="origin"
                            name="origin"/>
            </div>
<%--            <form:errors path="name" cssClass="alert alert-danger"/>--%>
        </div>
        <div class="row mb-3">
            <label for="destination" class="col-sm-2 col-form-label">Destination:</label>
            <div class="col-sm-2">
                <form:input path="destination" type="text"
                            class="form-control form-control-sm" id="destination"
                            name="destination"/>
            </div>
<%--            <form:errors path="name" cssClass="alert alert-danger"/>--%>
        </div>
        <button type="submit" class="btn btn-sm btn-primary">Evaluate</button>
    </form:form>
</main>

<jsp:include page="../fragments/bootstrap-core-js-main.jsp"/>

