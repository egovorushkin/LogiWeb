<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Waypoint List</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Waypoint List</h1>
    </div>

        <table class="table table-responsive-sm table-hover table-striped table-bordered table-sm">
            <thead>
            <tr>
                <th>Unique Number</th>
                <th>From City</th>
                <th>To City</th>
                <th>Cargo</th>
                <th>Status</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${waypointLists}" var="waypointList">
                <tr class='table-row' data-href='${pageContext.request.contextPath}/waypoint-lists/${waypointList.id}'>
                    <td class="align-middle">${waypointList.id}</td>
                    <td class="align-middle">${waypointList.fromCity.name}</td>
                    <td class="align-middle">${waypointList.toCity.name}</td>
                    <td class="align-middle">${waypointList.cargo.name}</td>
                    <td class="align-middle">${waypointList.status}</td>

                    <!-- construct an "delete" link with waypointList id -->
                    <c:url var="deleteLink" value="/waypoint-lists/delete">
                        <c:param name="waypointListId" value="${waypointList.id}"/>
                    </c:url>
                    <!-- construct an "update" link with waypointList id -->
                    <c:url var="updateLink" value="/waypoint-lists/edit">
                        <c:param name="waypointListId" value="${waypointList.id}"/>
                    </c:url>

                    <td><a class="nav-link" href="${updateLink}"><i data-feather="edit"></i></a></td>
                    <td><a class="nav-link" href="${deleteLink}"
                           onclick="if (!(confirm('Are you sure you want to delete this waypoint list?'))) return false"><i data-feather="x-square"></i></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/waypoint-lists/create"
       role="button">Add Waypoint List</a>
</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

