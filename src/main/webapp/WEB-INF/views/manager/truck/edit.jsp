<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../../fragments/before-title-main.jsp"/>

<title>Edit Truck</title>

<jsp:include page="../../fragments/after-title-with-nav-manager.jsp"/>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">

    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2"><i class="fas fa-edit"></i> | Edit Truck</h1>
    </div>

    <form:form modelAttribute="truck" action="${pageContext.request.contextPath}/trucks/update" method="post">

        <form:hidden path="id"/>

        <div class="row mb-3">
            <label for="registrationNumber" class="col-sm-2 col-form-label">Registration Number:</label>
            <div class="col-sm-2 ">
                <form:input path="registrationNumber" type="text" class="form-control form-control-sm"
                            id="registrationNumber"
                            name="registrationNumber"/>
            </div>
            <form:errors path="registrationNumber" cssClass="alert alert-danger"/>
        </div>

        <formfieldset class="form-group">
            <div class="row">
                <legend class="col-form-label col-sm-2 pt-0">Team Size:</legend>
                <div class="col-sm-10">
                    <div class="form-check form-check-inline">
                        <form:radiobutton path="teamSize"
                                          class="form-check-input"
                                          name="oneDriver" value="0"
                                          id="oneDriver"/>
                        <label class="form-check-label" for="oneDriver">
                            None
                        </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <form:radiobutton path="teamSize"
                                          class="form-check-input"
                                          name="oneDriver" value="1"
                                          id="oneDriver"/>
                        <label class="form-check-label" for="oneDriver">
                            1
                        </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <form:radiobutton path="teamSize"
                                          class="form-check-input"
                                          name="twoDrivers" value="2"
                                          id="twoDrivers"/>
                        <label class="form-check-label" for="twoDrivers">
                            2
                        </label>
                    </div>
                </div>
            </div>
        </formfieldset>
        <div class="row mb-3">
            <label for="capacity" class="col-sm-2 col-form-label">Capacity
                (kg):</label>
            <div class="col-sm-2">
                <form:input path="capacity" type="number"
                            class="form-control form-control-sm" id="capacity"
                            name="capacity"/>
            </div>
            <form:errors path="capacity" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Condition:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="state"
                             id="state" name="name">
                    <form:options itemValue="title" itemLabel="name"
                                  items="${states}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control form-control-sm" path="status"
                             id="status" name="name">
                    <form:options itemValue="title" itemLabel="name"
                                  items="${statuses}"/>
                </form:select>
            </div>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current City:</label>
            <div class="col-sm-2">
                <form:select path="currentCity.id"
                             cssClass="form-control form-control-sm">
                    <form:options items="${cities}" itemValue="id"
                                  itemLabel="name"/>
                </form:select>
            </div>
        </div>

        <!-- construct an "delete" link with truck id -->
        <c:url var="deleteLink" value="/trucks/delete">
            <c:param name="truckId" value="${truck.id}"/>
        </c:url>

        <button type="submit" class="btn btn-sm btn-primary">Save</button>
        <a class="btn btn-sm btn-secondary btn-danger" href="${deleteLink}"
           onclick="if (!(confirm('Are you sure you want to delete this truck?'))) return false"
           role="button">Delete</a>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/trucks/list"
           role="button">Cancel</a>
    </form:form>
</main>

<jsp:include page="../../fragments/bootstrap-core-js-main.jsp"/>




