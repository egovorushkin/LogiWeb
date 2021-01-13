<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Cargo</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Cargo</h1>
    </div>

    <form:form modelAttribute="cargo" action="${pageContext.request.contextPath}/cargoes/${cargo.id}">
        <div class="row mb-3">
            <label for="id" class="col-sm-2 col-form-label">Id:</label>
            <div class="col-sm-2 ">
                <form:input path="id" type="text" class="form-control form-control-sm" id="id" name="id"
                            disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="name" class="col-sm-2 col-form-label">Name:</label>
            <div class="col-sm-2">
                <form:input path="name" type="text" class="form-control form-control-sm" id="name" name="name"
                            disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label for="weight" class="col-sm-2 col-form-label">Weight (kg):</label>
            <div class="col-sm-2">
                <form:input path="weight" type="number" class="form-control form-control-sm" id="weight" name="weight"
                            disabled="true"/>
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
        <!-- construct an "delete" link with cargo id -->
        <c:url var="deleteLink" value="/cargoes/delete">
            <c:param name="cargoId" value="${cargo.id}"/>
        </c:url>

        <!-- construct an "update" link with cargo id -->
        <c:url var="updateLink" value="/cargoes/edit">
            <c:param name="cargoId" value="${cargo.id}"/>
        </c:url>

        <a class="btn btn-sm btn-success" href="${updateLink}" role="button">Edit</a>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this cargo?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/cargoes/list"
           role="button">Back</a>
    </form:form>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>