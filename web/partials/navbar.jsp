<%@page import="models.Pessoa"%>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="admin">Admin</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
                <li><a href="#">Link</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <% Pessoa p = (Pessoa) session.getAttribute("current_user");
                    if (p != null) {
                %>
                <% if (p.getPermissao() == 0) { %>
                <form class="navbar-form navbar-left" method="POST" action="admin">
                    <input type="hidden" name="action" value="view_companies" />
                    <button type="submit" class="btn btn-sm btn-primary">Empresas</button>
                </form>
                <form class="navbar-form navbar-left" method="POST" action="admin">
                    <input type="hidden" name="action" value="view_users" />
                    <button type="submit" class="btn btn-sm btn-primary">Usuários</button>
                </form>
                <form class="navbar-form navbar-left" method="POST" action="admin">
                    <input type="hidden" name="action" value="view_interviews" />
                    <button type="submit" class="btn btn-sm btn-primary">Reuniões</button>
                </form>
                <% } %>
                <form class="navbar-form navbar-right" method="POST" action="admin">
                    <input type="hidden" name="action" value="logout" />
                    <button type="submit" class="btn btn-sm btn-danger">Sair</button>
                </form>
                <form class="navbar-form navbar-left" method="POST" action="admin">
                    <input type="hidden" name="action" value="edit_resume" />
                    <button type="submit" class="btn btn-sm btn-primary">Minhas informações</button>
                </form>
                <% }%>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
