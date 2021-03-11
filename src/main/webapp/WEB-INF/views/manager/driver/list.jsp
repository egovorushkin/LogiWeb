<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Drivers</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2">
        <h4><em class="fas fa-users"></em> | Drivers</h4>
    </div>

    <table class="table table-hover table-striped">
        <thead>
        <tr>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Personal №</th>
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
                <td class="align-middle">${driver.id}</td>
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
                       onclick="if (!(confirm('Are you sure you want to delete ' +
                        'this driver?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${totalPages > 1}">
        <ul class="pagination pagination-sm">
            <c:choose>
                <c:when test="${pageId == 1}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" aria-disabled="true">Previous</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <c:url value="${pageContext.request.contextPath}/drivers/list/${pageId - 1}"
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
                        <c:url value="${pageContext.request.contextPath}/drivers/list/${tagStatus.index}"
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
                    <c:url value="${pageContext.request.contextPath}/drivers/list/${pageId + 1}"
                           var="url"/>
                    <li class="page-item">
                        <a class="page-link" href='<c:out value="${url}" />'>Next</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </c:if>

    <a class="btn btn-sm  btn-success"
       href="${pageContext.request.contextPath}/drivers/create" role="button">Add
        Driver</a>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>
