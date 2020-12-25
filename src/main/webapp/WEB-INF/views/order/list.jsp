<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../fragments/page-before-title.jsp"/>

<title>List of Orders</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <table class="table table-hover table-responsive-sm" width="100%">
        <thead>
        <tr>
            <th scope="col">Unique Number</th>
            <th scope="col">Status</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">

            <!-- construct an "delete" link with order id -->
            <c:url var="deleteLink" value="/orders/delete">
                <c:param name="orderId" value="${order.id}" />
            </c:url>
            <!-- construct an "update" link with order id -->
            <c:url var="updateLink" value="/orders/edit">
                <c:param name="orderId" value="${order.id}" />
            </c:url>


            <tr class='table-row'>
                <td>${order.uniqueNumber}</td>
                <td>${order.orderStatus}</td>
                <td><a class="nav-link" href="${updateLink}"><span data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this order?'))) return false"><span data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <a class="btn btn-success" href="${pageContext.request.contextPath}/orders/create" role="button">Add</a>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>
