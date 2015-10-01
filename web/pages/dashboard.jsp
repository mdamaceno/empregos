<%-- 
    Document   : dashboard
    Created on : 22/09/2015, 00:49:35
    Author     : Marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1>Admin</h1>

<div class="row">
    <div class="col-md-4">
        <form method="POST" action="admin">
            <input type="hidden" name="action" value="new_resume" />
            <button type="submit" class="btn btn-primary">Cadastrar Currículo</button>
        </form>
    </div>
</div>