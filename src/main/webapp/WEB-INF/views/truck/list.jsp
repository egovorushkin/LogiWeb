<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Trucks</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Trucks</h1>
    </div>

    <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
        <thead>
        <tr>
            <th scope="col">Registration Number</th>
            <th scope="col">Team Size</th>
            <th scope="col">Capacity (kg)</th>
            <th scope="col">Condition</th>
            <th scope="col">Status</th>
            <th scope="col">Current City</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${trucks}" var="truck">
            <tr class='table-row'
                data-href='${pageContext.request.contextPath}/trucks/${truck.id}'>
                <td class="align-middle">${truck.registrationNumber}</td>
                <td class="align-middle">${truck.teamSize}</td>
                <td class="align-middle">${truck.capacity}</td>
                <td class="align-middle">${truck.state.toString()}</td>
                <td class="align-middle">${truck.status.toString()}</td>
                <td class="align-middle">${truck.currentCity.name}</td>

                <!-- construct an "delete" link with truck id -->
                <c:url var="deleteLink" value="/trucks/delete">
                    <c:param name="truckId" value="${truck.id}"/>
                </c:url>
                <!-- construct an "update" link with truck id -->
                <c:url var="updateLink" value="/trucks/edit">
                    <c:param name="truckId" value="${truck.id}"/>
                </c:url>

                <td><a class="nav-link" href="${updateLink}"><span
                        data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this truck?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-sm btn-success"
       href="${pageContext.request.contextPath}/trucks/create" role="button">Add
        Truck</a>
</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>
