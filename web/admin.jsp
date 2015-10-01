<%-- 
    Document   : index
    Created on : 21/09/2015, 21:22:52
    Author     : Marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
        <link href="css/bootstrap.min.css" rel="stylesheet" />
        <link href="css/bootstrap-theme.min.css" rel="stylesheet" />
        <script src="js/jquery-1.11.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </head>
    <body>
        <%@include file="partials/navbar.jsp" %>

        <div id="main">
            <div class="container">
                <%
                    String pagina = request.getParameter("pg");
                    try {
                        if (pagina != null) {
                            pagina = "pages/" + pagina + ".jsp";
                %>
                <jsp:include page="<%= pagina%>" />
                <%
                } else {
                    pagina = (String) request.getAttribute("pg");
                    if (pagina != null) {
                        pagina = "pages/" + pagina + ".jsp";
                %>
                <jsp:include page="<%= pagina%>" />
                <%
                            } else
                                out.print("<h1>Paramêtro não encontrado.</h1>");
                        }
                    } catch (Exception se) {
                        out.print("Pagina " + pagina + " não encontrada.");
                    }
                %> 
            </div>
        </div>
    </body>
</html>
