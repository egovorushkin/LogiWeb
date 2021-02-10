<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Trucks</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4 class="h4"><em class="fas fa-truck-moving"></em> | Trucks</h4>
    </div>

    <table id="tables" class="table-hover table-striped">
        <caption></caption>
        <thead>
        <tr>
            <th scope="col">Registration №</th>
            <th scope="col">Capacity (kg)</th>
            <th scope="col">State</th>
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
                       onclick="if (!(confirm('Are you sure you want to delete ' +
                        'this truck?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-sm btn-success"
       href="${pageContext.request.contextPath}/trucks/create" role="button">Add
        Truck</a>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>
