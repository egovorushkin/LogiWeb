<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Drivers</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2"><i class="fas fa-users"></i> | Drivers</h1>
    </div>

    <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
        <thead>
        <tr>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Personal Number</th>
            <th scope="col">Worked Hours / Month</th>
            <th scope="col">Current Status</th>
            <th scope="col">Current City</th>
            <th scope="col">Current Truck</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${drivers}" var="driver">
            <tr class='table-row'
                data-href='${pageContext.request.contextPath}/drivers/${driver.id}'>
                <td class="align-middle">${driver.firstName}</td>
                <td class="align-middle">${driver.lastName}</td>
                <td class="align-middle">${driver.personalNumber}</td>
                <td class="align-middle">${driver.workedHoursPerMonth}</td>
                <td class="align-middle">${driver.status.toString()}</td>
                <td class="align-middle">${driver.currentCity.name}</td>
                <td class="align-middle">${driver.truck.registrationNumber}</td

                        <!-- construct an "delete" link with driver id -->
                <c:url var="deleteLink" value="/drivers/delete">
                    <c:param name="driverId" value="${driver.id}"/>
                </c:url>
                        <!-- construct an "update" link with driver id -->
                <c:url var="updateLink" value="/drivers/edit">
                    <c:param name="driverId" value="${driver.id}"/>
                </c:url>

                <td><a class="nav-link" href="${updateLink}"><span
                        data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this driver?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-sm  btn-success"
       href="${pageContext.request.contextPath}/drivers/create" role="button">Add
        Driver</a>
</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>
