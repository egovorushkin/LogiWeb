<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Waypoint List</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Waypoint List</h1>
    </div>

    <form:form modelAttribute="waypointList" action="${pageContext.request.contextPath}/waypoint-lists/${waypointList.id}">
        <div class="row mb-3">
            <label for="id" class="col-sm-2 col-form-label">Unique Number:</label>
            <div class="col-sm-2 ">
                <form:input path="id" type="number" class="form-control form-control-sm" id="id"
                            name="id" disabled="true"/>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">From City:</label>
            <div class="col-sm-2">
                <form:select path="fromCity.id" cssClass="form-control form-control-sm" disabled="true">
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">To City:</label>
            <div class="col-sm-2">
                <form:select path="toCity.id" cssClass="form-control form-control-sm" disabled="true">
                    <form:options items="${cities}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Cargoes:</label>
            <div class="col-sm-2">
                <form:select path="cargo.id" cssClass="form-control form-control-sm" disabled="true">
                    <form:options items="${cargoes}" itemValue="id" itemLabel="name"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="status" id="status" name="status"
                             disabled="true">
                    <form:options itemValue="name" itemLabel="name" items="${statuses}"/>
                </form:select>
            </div>
        </div>

        <!-- construct an "delete" link with waypoint list id -->
        <c:url var="deleteLink" value="/waypoint-lists/delete">
            <c:param name="waypointListId" value="${waypointList.id}"/>
        </c:url>

        <!-- construct an "update" link with waypoint list id -->
        <c:url var="updateLink" value="/waypoint-lists/edit">
            <c:param name="waypointListId" value="${waypointList.id}"/>
        </c:url>

        <a class="btn btn-sm btn-success" href="${updateLink}" role="button">Edit</a>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this waypoint list?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/waypoint-lists/list" role="button">Back</a>
    </form:form>
</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>