<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>New Cargo</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Create New Cargo</h1>
    </div>

    <form:form modelAttribute="cargo" action="${pageContext.request.contextPath}/cargoes/save" method="post">
        <div class="row mb-3">
            <label for="id" class="col-sm-2 col-form-label">Id:</label>
            <div class="col-sm-2">
                <form:input path="id" type="number" class="form-control form-control-sm" id="id" name="id"
                            readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="name" class="col-sm-2 col-form-label">Name:</label>
            <div class="col-sm-2">
                <form:input path="name" type="text" class="form-control form-control-sm" id="name"
                            name="name"/>
            </div>
            <form:errors path="name" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="weight" class="col-sm-2 col-form-label">Weight (kg):</label>
            <div class="col-sm-2">
                <form:input path="weight" type="number" class="form-control form-control-sm" id="weight"
                            name="weight"/>
            </div>
            <form:errors path="weight" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="status" id="status" name="status">
                    <form:options itemValue="name" itemLabel="name" items="${statuses}"/>
                </form:select>
            </div>
        </div>

        <button type="submit" class="btn btn-sm btn-primary">Save</button>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/cargoes/list"
           role="button">Back</a>
    </form:form>
</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

