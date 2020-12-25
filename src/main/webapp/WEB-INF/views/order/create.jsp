<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="../fragments/page-before-title.jsp"/>

<title>New Order</title>

<jsp:include page="../fragments/page-after-title-with-navs.jsp"/>


<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 m-2">
    <form:form modelAttribute="order" action="${pageContext.request.contextPath}/orders/save" method="post">
        <div class="row mb-3">
            <label for="uniqueNumber" class="col-sm-2 col-form-label">Unique Number:</label>
            <div class="col-sm-2">
                <form:input path="uniqueNumber" type="text" class="form-control" id="uniqueNumber" name="uniqueNumber"/>
            </div>
            <form:errors path="uniqueNumber" cssClass="alert alert-danger"/>
        </div>
        <div class="row mb-3">
            <label class="col-sm-2 col-form-label">Current Status:</label>
            <div class="col-sm-2">
                <form:select class="form-control" path="orderStatus" id="orderStatus" name="orderStatus">
                    <form:options itemValue="name" itemLabel="name" items="${statuses}"/>
                </form:select>
            </div>
        </div>
        <%--        <div class="row mb-3">--%>
        <%--            <label for="currentCity" class="col-sm-2 col-form-label">Current City:</label>--%>
        <%--            <div class="col-sm-3">--%>
        <%--                <form:input path="currentCity" type="text" class="form-control" id="currentCity" name="currentCity"/>--%>
        <%--            </div>--%>
        <%--            <form:errors path="currentCity" cssClass="alert alert-danger"/>--%>
        <%--        </div>--%>
        <button type="submit" class="btn btn-primary">Save</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/orders/list" role="button">Back</a>
    </form:form>
</main>
</div>
</div>

<jsp:include page="../fragments/bootstrap-core-js.jsp"/>

