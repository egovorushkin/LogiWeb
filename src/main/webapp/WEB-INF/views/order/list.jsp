<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Orders</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Orders</h1>
    </div>

    <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
        <thead>
        <tr>
            <th scope="col">Unique Number</th>
            <th scope="col">Status</th>
            <th scope="col">Waypoint List</th>
            <th scope="col">Truck</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr class='table-row' data-href='${pageContext.request.contextPath}/orders/${order.id}'>
                <td class="align-middle">${order.id}</td>
                <td class="align-middle">${order.orderStatus}</td>
                <td class="align-middle">${order.waypointList.id}</td>
                <td class="align-middle">${order.truck.registrationNumber}</td>

                <!-- construct an "delete" link with order id -->
                <c:url var="deleteLink" value="/orders/delete">
                    <c:param name="orderId" value="${order.id}"/>
                </c:url>
                <!-- construct an "update" link with order id -->
                <c:url var="updateLink" value="/orders/edit">
                    <c:param name="orderId" value="${order.id}"/>
                </c:url>

                <td class="align-middle"><a class="nav-link" href="${updateLink}"><span data-feather="edit"></span></a></td>
                <td class="align-middle"><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this order?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/orders/create" role="button">Add Order</a>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>
