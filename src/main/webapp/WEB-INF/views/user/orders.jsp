<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../views/fragments/before-title-main.jsp"/>

<title>My Orders</title>

<jsp:include page="../../views/fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">My Orders</h1>
    </div>
    <c:choose>
        <c:when test="${empty orders}">
            <h6>No available orders</h6>
        </c:when>
        <c:otherwise>
            <table id="tables" class="table-hover table-striped">
                <caption></caption>
                <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Status</th>
                    <th scope="col">From City</th>
                    <th scope="col">To City</th>
                    <th scope="col">Distance (km)</th>
                    <th scope="col">Travel time (hr)</th>
                    <th scope="col">Name of cargo </th>
                    <th scope="col">Truck</th>
                    <th scope="col">Edit</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order">

                    <c:url var="updateLink" value="/orders/edit-user-order">
                        <c:param name="orderId" value="${order.id}"/>
                    </c:url>

                    <tr class='table-row' data-href='${updateLink}'>
                        <td class="align-middle">${order.id}</td>
                        <td class="align-middle">${order.status.toString()}</td>
                        <td class="align-middle">${order.fromCity}</td>
                        <td class="align-middle">${order.toCity}</td>
                        <td class="align-middle">${order.distance}</td>
                        <td class="align-middle">${order.duration}</td>
                        <td class="align-middle">${order.cargo.name}</td>
                        <td class="align-middle">
                                ${order.truck.registrationNumber}
                        </td>
                        <td><a class="nav-link" href="${updateLink}">
                            <span data-feather="edit"></span></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../../views/fragments/bootstrap-core-js-main.jsp"/>

