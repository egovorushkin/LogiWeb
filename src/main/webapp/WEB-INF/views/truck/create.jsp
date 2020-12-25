<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>New Truck</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>


<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 m-2">
    <form:form modelAttribute="truck" action="${pageContext.request.contextPath}/trucks/save" method="post">
        <div class="row mb-3">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration Number:</label>
            <div class="col-sm-2">
                <form:input path="registrationNumber" type="text" class="form-control" id="registrationNumber" name="registrationNumber"/>
            </div>
            <form:errors path="registrationNumber" cssClass="alert alert-danger"/>

        </div>
        <div class="row mb-3">
            <label for="teamSize" class="col-sm-2 col-form-label">Team Size:</label>
            <div class="col-sm-2">
                <form:input path="teamSize" type="number" class="form-control" id="teamSize" name="teamSize"/>
            </div>
            <form:errors path="teamSize" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity (kg):</label>
            <div class="col-sm-2">
                <form:input path="capacity" type="number" class="form-control" id="capacity" name="capacity" />
            </div>
            <form:errors path="capacity" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label for="status" class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <input type="text" class="form-control" id="status" name="status">
            </div>
        </div>
<%--        <div class="row mb-3">--%>
<%--            <label for="currentCity" class="col-sm-2 col-form-label">Current City:</label>--%>
<%--            <div class="col-sm-3">--%>
<%--                <form:input path="currentCity" type="text" class="form-control" id="currentCity" name="currentCity"/>--%>
<%--            </div>--%>
<%--            <form:errors path="currentCity" cssClass="alert alert-danger"/>--%>
<%--        </div>--%>
        <button type="submit" class="btn btn-primary">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/trucks/list" role="button">Back</a>
    </form:form>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

