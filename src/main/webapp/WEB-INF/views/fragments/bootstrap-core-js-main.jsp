<%@ page contentType="text/html;charset=UTF-8" %>
    </div>
</div>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<%--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"--%>
<%--        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>--%>
<%--<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
<script>window.jQuery || document.write('<script src="${pageContext.request.contextPath}/resources/js/jquery-slim.min.js"><\/script>')</script>
<!-- Bootstrap Bundle (+Popper) -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.5.1.slim.min.js"></script>
<!-- FeatherIcons -->
<script src="${pageContext.request.contextPath}/resources/js/feather.min.js"></script>
<!-- FontAwesome Icons -->
<script src="https://kit.fontawesome.com/daa4b0d652.js" crossorigin="anonymous"></script>
<!-- jQuery DataTables -->
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.js"></script>

<script>
    // Clickable table rows
    $(document).ready(function ($) {
        $(".table-row").click(function () {
            window.document.location = $(this).data("href");
        });
    });

    // For Feather Icons
    feather.replace();


    // Using DataTable
    $(document).ready(function() {
        $('#tables').dataTable({
            "lengthMenu": [[ 1, 3, 5, 7, 10, -1], [ 1, 3, 5, 7, 10, "All"]],
            "pageLength": 7
        });
    });

    // For refresh Page
    $('#refresh').click(function() {
        location.reload();
    });

</script>

</body>
</html>
