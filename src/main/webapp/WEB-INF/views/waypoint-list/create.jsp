<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>New Waypoint List</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 m-2">

    <div class="page-header">
        <h2>Create New Waypoint List</h2>
        <hr>
    </div>

    <form:form modelAttribute="waypointList" action="${pageContext.request.contextPath}/waypoint-lists/save"
               method="post">
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Unique Number:</label>
            <div class="col-sm-2">
                <form:input path="id" type="number" class="form-control form-control-sm" id="id"
                            name="id" readonly="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">From City:</label>
            <div class="col-sm-2">
                <form:select path="fromCity.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty waypointList.fromCity}">
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
                    <c:if test="${empty waypointList.toCity}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Cargo:</label>
            <div class="col-sm-2">
                <form:select path="cargo.id" cssClass="form-control form-control-sm">
                    <c:if test="${empty waypointList.cargo}">
                        <form:option value="" disabled="true" selected="true"/>
                    </c:if>
                    <form:options items="${cargoes}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
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
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/waypointLists/list" role="button">Back</a>
    </form:form>
</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

