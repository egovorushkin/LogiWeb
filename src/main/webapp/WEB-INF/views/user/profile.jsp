<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../views/fragments/before-title-main.jsp"/>

<title>My Information</title>

<jsp:include page="../../views/fragments/after-title-with-nav-driver.jsp"/>

<main class="col-md-auto ml-sm-auto col-lg-10 px-4">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h4><em class="far fa-address-card"></em>
            | ${user.firstName} ${user.lastName}, №${user.id}</h4>
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

    <div class="row mb-3">
        <label for="userCity" class="col-sm-2 col-form-label">Current city:</label>
        <div class="col-sm-2">
            <input value="${user.currentCity.name}"
                   class="form-control form-control-sm" id="userCity"
                   name="userCity" disabled/>
        </div>
    </div>

    <hr>

    <h5>Current State:
        <c:if test="${user.inShift == true}">
            <span class="badge rounded-pill bg-primary text-light">In shift</span>
        </c:if>
        <c:if test="${user.inShift == false}">
            <span class="badge rounded-pill bg-primary text-light">Not in shift</span>
        </c:if>
    </h5>
    <h5>Current Status:
        <c:if test="${user.status eq 'RESTING'}">
            <span class="badge rounded-pill bg-warning text-light">Resting</span>
        </c:if>
        <c:if test="${user.status eq 'DRIVING'}">
            <span class="badge rounded-pill bg-warning text-light">Driving</span>
        </c:if>
        <c:if test="${user.status eq 'SECOND_DRIVER'}">
            <span class="badge rounded-pill bg-warning text-light">Second driver</span>
        </c:if>
    </h5>
    <hr>

    <c:if test="${user.truck != null}">
        <c:choose>
            <c:when test="${user.inShift == false}">
                <fieldset>
                    <legend>Change State</legend>
                    <c:url var="changeState" value="/user/update-state">
                        <c:param name="userState" value="${user.inShift = true}"/>
                    </c:url>
                    <a href="${changeState}">
                        <button class="btn btn-sm  btn-success">In shift</button>
                    </a>
                    <c:url var="changeState" value="/user/update-state">
                        <c:param name="userState" value="${user.inShift = false}"/>
                    </c:url>
                </fieldset>
            </c:when>
            <c:when test="${user.inShift == true}">
                <fieldset>
                    <legend>Chahge State</legend>
                    <c:url var="changeState" value="/user/update-state">
                        <c:param name="userState" value="${user.inShift = true}"/>
                    </c:url>
                    <c:url var="changeState" value="/user/update-state">
                        <c:param name="userState" value="${user.inShift = false}"/>
                    </c:url>
                    <a href="${changeState}">
                        <button class="btn btn-sm  btn-success">Not in Shift
                        </button>
                    </a>
                </fieldset>

                <c:if test="${user.status eq 'RESTING'}">
                    <legend>Change Status</legend>
                    <c:url var="changeStatusDriving"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'DRIVING'}"/>
                    </c:url>
                    <a href="${changeStatusDriving}">
                        <button class="btn btn-sm  btn-success"
                        >Driving
                        </button>
                    </a>

                    <c:url var="changeStatusSecondDriver"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'SECOND_DRIVER'}"/>
                    </c:url>
                    <a href="${changeStatusSecondDriver}">
                        <button class="btn btn-sm  btn-success"
                        >Second Driver
                        </button>
                    </a>

                    <c:url var="changeStatusLoadUnload"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'LOADING_UNLOADING'}"/>
                    </c:url>
                    <a href="${changeStatusLoadUnload}">
                        <button class="btn btn-sm  btn-success"
                        >Loading-Unloading
                        </button>
                    </a>

                    <c:url var="changeStatusLoadUnload"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'RESTING'}"/>
                    </c:url>
                </c:if>

                <c:if test="${user.status eq 'DRIVING'}">
                    <legend>Status</legend>

                    <c:url var="changeStatusSecondDriver"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'SECOND_DRIVER'}"/>
                    </c:url>
                    <a href="${changeStatusSecondDriver}">
                        <button class="btn btn-sm  btn-success"
                        >Second Driver
                        </button>
                    </a>

                    <c:url var="changeStatusLoadUnload"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'LOADING_UNLOADING'}"/>
                    </c:url>
                    <a href="${changeStatusLoadUnload}">
                        <button class="btn btn-sm  btn-success"
                        >Loading-Unloading
                        </button>
                    </a>

                    <c:url var="changeStatusLoadUnload"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'RESTING'}"/>
                    </c:url>
                    <a href="${changeStatusLoadUnload}">
                        <button class="btn btn-sm  btn-success"
                        >Resting
                        </button>
                    </a>
                </c:if>
                <c:if test="${user.status eq 'SECOND_DRIVER'}">
                    <legend>Change Status</legend>
                    <c:url var="changeStatusDriving"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'DRIVING'}"/>
                    </c:url>
                    <a href="${changeStatusDriving}">
                        <button class="btn btn-sm  btn-success"
                        >Driving
                        </button>
                    </a>

                    <c:url var="changeStatusLoadUnload"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'LOADING_UNLOADING'}"/>
                    </c:url>
                    <a href="${changeStatusLoadUnload}">
                        <button class="btn btn-sm  btn-success"
                        >Loading-Unloading
                        </button>
                    </a>

                    <c:url var="changeStatusLoadUnload"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'RESTING'}"/>
                    </c:url>
                    <a href="${changeStatusLoadUnload}">
                        <button class="btn btn-sm  btn-success"
                        >Resting
                        </button>
                    </a>
                </c:if>

                <c:if test="${user.status eq 'LOADING_UNLOADING'}">
                    <legend>Change Status</legend>
                    <c:url var="changeStatusDriving"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'DRIVING'}"/>
                    </c:url>
                    <a href="${changeStatusDriving}">
                        <button class="btn btn-sm  btn-success"
                        >Driving
                        </button>
                    </a>

                    <c:url var="changeStatusSecondDriver"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'SECOND_DRIVER'}"/>
                    </c:url>
                    <a href="${changeStatusSecondDriver}">
                        <button class="btn btn-sm  btn-success"
                        >Second Driver
                        </button>
                    </a>

                    <c:url var="changeStatusLoadUnload"
                           value="/user/update-status">
                        <c:param name="userStatus"
                                 value="${user.status = 'RESTING'}"/>
                    </c:url>
                    <a href="${changeStatusLoadUnload}">
                        <button class="btn btn-sm  btn-success"
                        >Resting
                        </button>
                    </a>
                </c:if>
            </c:when>
        </c:choose>
    </c:if>

    <hr>

    <div>
        <h4>My colleague</h4>
    </div>

    <c:choose>
        <c:when test="${colleague eq null}">
            <h6>You don't have a colleague yet</h6>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-responsive-sm table-striped
                            table-bordered table-sm">
                <caption></caption>
                <thead>
                <tr>
                    <th scope="col">Personal №</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Worked Hours / Month</th>
                    <th scope="col">State</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="align-middle">${colleague.id}</td>
                    <td class="align-middle">${colleague.firstName}</td>
                    <td class="align-middle">${colleague.lastName}</td>
                    <td class="align-middle">${colleague.workedHoursPerMonth}</td>
                    <c:if test="${colleague.inShift == true}">
                        <td class="align-middle">In Shift</td>
                    </c:if>
                    <c:if test="${colleague.inShift == false}">
                        <td class="align-middle">Not in shift</td>
                    </c:if>
                    <td class="align-middle">${colleague.status.toString()}</td>
                </tr>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

</main>

<jsp:include page="../../views/fragments/bootstrap-core-js-main.jsp"/>

