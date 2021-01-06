<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>Cargoes</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Cargoes</h1>
    </div>

    <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
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
            <tr class='table-row' data-href='${pageContext.request.contextPath}/cargoes/${cargo.id}'>
                <td>${cargo.id}</td>
                <td>${cargo.name}</td>
                <td>${cargo.weight}</td>
                <td>${cargo.status}</td>

                <!-- construct an "delete" link with cargo id -->
                <c:url var="deleteLink" value="/cargoes/delete">
                    <c:param name="cargoId" value="${cargo.id}"/>
                </c:url>
                <!-- construct an "update" link with cargo id -->
                <c:url var="updateLink" value="/cargoes/edit">
                    <c:param name="cargoId" value="${cargo.id}"/>
                </c:url>

                <td><a class="nav-link" href="${updateLink}"><span data-feather="edit"></span></a></td>
                <td><a class="nav-link" href="${deleteLink}"
                       onclick="if (!(confirm('Are you sure you want to delete this cargo?'))) return false"><span
                        data-feather="x-square"></span></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/cargoes/create" role="button">Add</a>
</main>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>
