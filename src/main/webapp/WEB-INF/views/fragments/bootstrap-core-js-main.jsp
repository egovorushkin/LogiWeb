<%@ page contentType="text/html;charset=UTF-8" %>
</div>
</div>

<!-- Bootstrap Bundle (+Popper) -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.5.1.js"></script>
<!-- FeatherIcons -->
<script src="${pageContext.request.contextPath}/resources/js/feather.min.js"></script>
<!-- FontAwesome Icons -->
<script src="https://kit.fontawesome.com/daa4b0d652.js"
        crossorigin="anonymous"></script>

<script>
    // Clickable table rows
    $(document).ready(function ($) {
        $(".table-row").click(function () {
            window.document.location = $(this).data("href");
        });
    });

    // For Feather Icons
    feather.replace();


    // For refresh Page
    $('#refresh').click(function () {
        console.log("!!!refresh page button pressed!!!")
        location.reload();
    });

</script>

</body>
</html>
