<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Orders</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
    align-items-center pt-3 pb-2">
        <h4><em class="fas fa-clipboard-list"></em> | Orders</h4>
    </div>

    <table class=" table table-hover table-striped">
        <thead>
        <tr>
            <th scope="col">№</th>
            <th scope="col">Status</th>
            <th scope="col">From City</th>
            <th scope="col">To City</th>
            <th scope="col">Distance (km)</th>
            <th scope="col">Duration (hr)</th>
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
                <td class="align-middle">${order.fromCity}</td>
                <td class="align-middle">${order.toCity}</td>
                <td class="align-middle">${order.distance}</td>
                <td class="align-middle">${order.duration}</td>
                <td class="align-middle">${order.cargo.name}</td>
                <td class="align-middle">${order.truck.registrationNumber}</td>

                <c:url var="deleteLink" value="/orders/delete">
                    <c:param name="orderId" value="${order.id}"/>
                </c:url>

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

    <ul class="pagination pagination-sm">
        <c:choose>
            <c:when test="${pageId == 1}">
                <li class="page-item disabled">
                    <a class="page-link" href="#" aria-disabled="true">Previous</a>
                </li>
            </c:when>
            <c:otherwise>
                <c:url value="${pageContext.request.contextPath}/orders/list/${pageId - 1}"
                       var="url"/>
                <li class="page-item">
                    <a class="page-link" href='<c:out value="${url}" />'>Previous</a>
                </li>
            </c:otherwise>
        </c:choose>
        <c:forEach begin="1" end="${totalPages}" step="1" varStatus="tagStatus">
            <c:choose>
                <c:when test="${pageId == tagStatus.index}">
                    <li class="page-item active">
                        <a class="page-link" href='#'>${tagStatus.index}</a>
                    </li>
                </c:when>

                <c:otherwise>
                    <c:url value="${pageContext.request.contextPath}/orders/list/${tagStatus.index}"
                           var="url"/>
                    <li class="page-item">
                        <a class="page-link"
                           href='<c:out value="${url}" />'>${tagStatus.index}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${pageId == totalPages}">
                <li class="page-item disabled">
                    <a class="page-link" href="#" aria-disabled="true">Next</a>
                </li>
            </c:when>
            <c:otherwise>
                <c:url value="${pageContext.request.contextPath}/orders/list/${pageId + 1}"
                       var="url"/>
                <li class="page-item">
                    <a class="page-link" href='<c:out value="${url}" />'>Next</a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>

    <a class="btn btn-sm btn-success"
       href="${pageContext.request.contextPath}/orders/create" role="button">
        Add Order
    </a>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>
