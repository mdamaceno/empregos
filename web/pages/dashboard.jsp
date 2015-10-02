<%-- 
    Document   : dashboard
    Created on : 22/09/2015, 00:49:35
    Author     : Marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1>Painel Administrativo</h1>
<div class="row">
    <div class="col-md-4">
        <form method="POST" action="admin">
            <input type="hidden" name="action" value="edit_resume" />
            <button type="submit" class="btn btn-primary">Minhas informações</button>
        </form>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <h2>Reuniões Agendadas</h2>
        <table class="table">
            <thead>
                <tr>
                    <th width="5">ID</th>
                    <th>Empresa</th>
                    <th>Data</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
    </div>
</div>