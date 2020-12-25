<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Trucks</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <table class="table table-hover table-responsive-sm" width="100%">
        <thead>
        <tr>
            <th scope="col">Registration Number</th>
            <th scope="col">Team Size</th>
            <th scope="col">Capacity (kg)</th>
            <th scope="col">Status</th>
            <th scope="col">Current City</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${trucks}" var="truck">

            <!-- construct an "delete" link with truck id -->
            <c:url var="deleteLink" value="/trucks/delete">
                <c:param name="truckId" value="${truck.id}" />
            </c:url>
            <!-- construct an "update" link with truck id -->
            <c:url var="updateLink" value="/trucks/edit">
                <c:param name="truckId" value="${truck.id}" />
            </c:url>


            <tr class='table-row'>
                <td>${truck.registrationNumber}</td>
                <td>${truck.teamSize}</td>
                <td>${truck.capacity}</td>
                <td>${truck.status}</td>
                <td></td>
                <td><a class="nav-link" href="${updateLink}"><span data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this truck?'))) return false"><span data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <a class="btn btn-success" href="${pageContext.request.contextPath}/trucks/create" role="button">Add</a>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>
