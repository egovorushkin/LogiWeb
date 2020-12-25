<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Drivers</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <table class="table table-hover table-responsive-sm" width="100%">
        <thead>
        <tr>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Personal Number</th>
            <th scope="col">Worked Hours / Month)</th>
            <th scope="col">Current Status</th>
            <th scope="col">Current City</th>
            <th scope="col">Current Truck</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${drivers}" var="driver">

            <!-- construct an "delete" link with driver id -->
            <c:url var="deleteLink" value="/drivers/delete">
                <c:param name="driverId" value="${driver.id}"/>
            </c:url>
            <!-- construct an "update" link with driver id -->
            <c:url var="updateLink" value="/drivers/edit">
                <c:param name="driverId" value="${driver.id}"/>
            </c:url>

            <tr class='table-row'>
                <td>${driver.firstName}</td>
                <td>${driver.lastName}</td>
                <td>${driver.personalNumber}</td>
                <td>${driver.workedHoursPerMonth}</td>
                <td>${driver.driverStatus}</td>
                <td>${driver.currentCity}</td>
                <td>${driver.currentTruck}</td>
                <td><a class="nav-link" href="${updateLink}"><span data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this driver?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <a class="btn btn-success" href="${pageContext.request.contextPath}/drivers/create" role="button">Add</a>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>
