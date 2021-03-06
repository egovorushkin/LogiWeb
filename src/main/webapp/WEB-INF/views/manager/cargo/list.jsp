<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Cargoes</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                align-items-center pt-3 pb-2">
        <h4 class="h4"><em class="fas fa-boxes"></em> | Cargoes</h4>
    </div>

    <table class="table table-hover table-striped">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Weight (kg)</th>
            <th scope="col">Status</th>
            <th scope="col">Edit</th>
            <th scope="col">Delete</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${cargoes}" var="cargo">
            <tr class='table-row'
                data-href='${pageContext.request.contextPath}/cargoes/${cargo.id}'>
                <td class="align-middle">${cargo.id}</td>
                <td class="align-middle">${cargo.name}</td>
                <td class="align-middle">${cargo.weight}</td>
                <td class="align-middle">${cargo.status.toString()}</td>

                <!-- construct an "delete" link with cargo id -->
                <c:url var="deleteLink" value="/cargoes/delete">
                    <c:param name="cargoId" value="${cargo.id}"/>
                </c:url>
                <!-- construct an "update" link with cargo id -->
                <c:url var="updateLink" value="/cargoes/edit">
                    <c:param name="cargoId" value="${cargo.id}"/>
                </c:url>

                <td><a class="nav-link" href="${updateLink}"><span
                        data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this cargo?')))
                           return false"><span
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
                <c:url value="${pageContext.request.contextPath}/cargoes/list/${pageId - 1}"
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
                    <c:url value="${pageContext.request.contextPath}/cargoes/list/${tagStatus.index}"
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
                <c:url value="${pageContext.request.contextPath}/cargoes/list/${pageId + 1}"
                       var="url"/>
                <li class="page-item">
                    <a class="page-link" href='<c:out value="${url}" />'>Next</a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>

    <a class="btn btn-sm btn-success"
       href="${pageContext.request.contextPath}/cargoes/create" role="button">
        Add Cargo
    </a>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>
