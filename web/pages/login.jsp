<%-- 
    Document   : login
    Created on : 21/09/2015, 22:00:51
    Author     : Marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1>Faça o login</h1>

<div class="row">
    <div class="col-md-4">
        <form action="login" method="POST">
            <input type="hidden" name="action" value="make_login" />
            <div class="form-group">
                <label for="usuario_email">Email</label>
                <input type="email" class="form-control" id="usuario_email" name="pessoa_email" placeholder="Email">
            </div>
            <div class="form-group">
                <label for="usuario_senha">Senha</label>
                <input type="password" class="form-control" id="usuario_senha" name="pessoa_senha" placeholder="Senha">
            </div>
            <button type="submit" class="btn btn-primary">Entrar</button>
        </form>
    </div>
</div>