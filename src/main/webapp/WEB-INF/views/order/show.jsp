<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Truck</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
    <form modelAttribute="truck" action="${pageContext.request.contextPath}/trucks/${truck.id}">
        <div class="row mb-3">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration Number:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="registrationNumber" name="registrationNumber" value="${truck.registrationNumber}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="teamSize" class="col-sm-2 col-form-label">Team Size:</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" id="teamSize" name="teamSize" value="${truck.teamSize}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity (kg):</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" id="capacity" name="capacity" value="${truck.capacity}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="status" class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="status" name="status" value="${truck.status}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="currentCity" class="col-sm-2 col-form-label">Current City:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="currentCity" name="currentCity" value="${truck.currentCity}" readonly>
            </div>
        </div>
        <!-- construct an "delete" link with truck id -->
        <c:url var="deleteLink" value="/trucks/delete">
            <c:param name="truckId" value="${truck.id}" />
        </c:url>

        <a class="btn btn-success" href="${pageContext.request.contextPath}/trucks/edit" role="button">Edit</a>
        <a class="btn btn-secondary" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this truck?'))) return false" role="button">Delete</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/trucks/list" role="button">Back</a>
    </form>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

