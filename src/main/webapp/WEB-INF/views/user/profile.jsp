<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../views/fragments/before-title-main.jsp"/>

<title>My Information</title>

<jsp:include page="../../views/fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-auto ml-sm-auto col-lg-10 px-4">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><em class="far fa-address-card"></em>
            | ${user.firstName} ${user.lastName}, №${user.personalNumber}</h4>
    </div>
    <div class="row mb-3">
        <label for="workedHoursPerMonth" class="col-sm-2 col-form-label">Hours
            worked this month:</label>
        <div class="col-sm-2">
            <input value="${user.workedHoursPerMonth}"
                   class="form-control form-control-sm" id="workedHoursPerMonth"
                   name="workedHoursPerMonth" disabled/>
        </div>
    </div>

    <div class="row mb-3">
        <label for="userTruck" class="col-sm-2 col-form-label">My Truck:</label>
        <div class="col-sm-2">
            <input value="${user.truck.registrationNumber}"
                   class="form-control form-control-sm" id="userTruck"
                   name="userTruck" disabled/>
        </div>
    </div>

    <%--    <form:form modelAttribute="user" action="${pageContext.request.contextPath}/user/update-status" method="post">--%>
    <%--        <form:hidden path="id"/>--%>
    <%--        <div class="row mb-3">--%>
    <%--            <label class="col-sm-2 col-form-label">Current Status:</label>--%>
    <%--            <div class="col-sm-2">--%>
    <%--                <form:select class="form-control form-control-sm" path="status" id="userStatus"--%>
    <%--                             name="userStatus">--%>
    <%--                    <form:options itemValue="title" itemLabel="name" items="${statuses}"/>--%>
    <%--                </form:select>--%>
    <%--            </div>--%>
    <%--        </div>--%>

    <%--        <button type="submit" class="btn btn-sm btn-primary">Update Status</button>--%>
    <%--    </form:form>--%>
    <form:form modelAttribute="user"
               action="${pageContext.request.contextPath}/user/update-state"
               method="post">
        <form:hidden path="id"/>
        <c:choose>
            <c:when test="${user.inShift == false}">
                <fieldset>
                    <legend>State</legend>
                    <c:url var="changeState" value="/user/update-state">
                        <c:param name="user" value="${user}"/>
                    </c:url>
                    <a class="btn btn-sm  btn-success" href="${changeState}" role="button">In shift</a>
                    <button type="button" class="btn btn-secondary btn-sm" disabled>Resting
                    </button>
                </fieldset>
            </c:when>
            <c:otherwise>
                <fieldset>
                    <legend>State</legend>
                    <button type="button" class="btn btn-secondary btn-sm" disabled>In shift
                    </button>
                    <button type="button" class="btn btn-success btn-sm">Resting
                    </button>
                </fieldset>
                <fieldset>
                    <legend>Status</legend>
                    <button type="button" class="btn btn-success btn-sm">Driving
                    </button>
                    <button type="button" class="btn btn-success btn-sm">Second
                        driver
                    </button>
                    <button type="button" class="btn btn-success btn-sm">
                        Loading-Unloading
                    </button>
                </fieldset>

            </c:otherwise>
        </c:choose>
    </form:form>

    <div>
        <h4>My colleague</h4>
    </div>

    <c:choose>
        <c:when test="${user.truck.currentDrivers.size() == 1}">
            <h6>No colleague.</h6>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-responsive-sm table-striped table-bordered table-sm">
                <caption>My colleague</caption>
                <thead>
                <tr>
                    <th scope="col">Personal №</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Worked Hours / Month</th>
                    <th scope="col">Current Status</th>
                </tr>
                </thead>
                <tbody>

                <tr>
                    <td class="align-middle">${colleague.personalNumber}</td>
                    <td class="align-middle">${colleague.firstName}</td>
                    <td class="align-middle">${colleague.lastName}</td>
                    <td class="align-middle">${colleague.workedHoursPerMonth}</td>
                    <td class="align-middle">${colleague.status.toString()}</td>
                </tr>

                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

</main>

<jsp:include page="../../views/fragments/bootstrap-core-js-main.jsp"/>

