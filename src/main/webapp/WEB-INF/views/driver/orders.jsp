<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../views/fragments/before-title-main.jsp"/>

<title>My Orders</title>

<jsp:include page="../../views/fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">My Orders</h1>
    </div>
    <table id="tables" class="table-hover table-striped">
        <thead>
        <tr>
            <th scope="col">Number</th>
            <th scope="col">Status</th>
            <th scope="col">From City</th>
            <th scope="col">To City</th>
            <th scope="col">Distance (km)</th>
            <th scope="col">Travel time (hr)</th>
            <th scope="col">Cargo</th>
            <th scope="col">Truck</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr class='table-row' data-href='${pageContext.request.contextPath}/orders/${order.id}'>
                <td class="align-middle">${order.id}</td>
                <td class="align-middle">${order.status.toString()}</td>
                <td class="align-middle">${order.fromCity.name}</td>
                <td class="align-middle">${order.toCity.name}</td>
                <td class="align-middle">${order.distance}</td>
                <td class="align-middle">${order.duration}</td>
                <td class="align-middle">${order.cargo.name}</td>
                <td class="align-middle">${order.truck.registrationNumber}</td>

                <!-- construct an "delete" link with order id -->
                <c:url var="deleteLink" value="/orders/delete">
                    <c:param name="orderId" value="${order.id}"/>
                </c:url>
                <!-- construct an "update" link with order id -->
                <c:url var="updateLink" value="/orders/edit">
                    <c:param name="orderId" value="${order.id}"/>
                </c:url>

                <td><a class="nav-link" href="${updateLink}"><span data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this order?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</main>

<jsp:include page="../../views/fragments/bootstrap-core-js-main.jsp"/>

