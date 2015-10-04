<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Pessoas</h2>
<div class="row">
    <div class="col-md-12">
        <div class="row">
            <form method="POST" action="admin" class="form-horizontal">
                <input type="hidden" name="action" value="new_interview"/>

                <div class="form-group pull-right">
                    <label class="col-sm-5 control-label">Busca por função</label>
                    <div class="col-sm-7">
                        <select name="opt_user" class="form-control" id="function">
                            <option value="">Selecione uma opção</option>
                            <option value="200">Gerente de TI</option>
                            <option value="201">Cientista de Dados</option>
                            <option value="202">Desenvolvedor Mobile</option>
                            <option value="203">Desenvolvedor Web</option>
                            <option value="204">Gerente de Projetos</option>
                            <option value="205">Analista de Mídias Sociais</option>
                            <option value="206">Tester</option>
                            <option value="207">Webdesign</option>
                        </select>
                        <button class="btn btn-sm btn-primary" type="submit">Buscar</button>
                    </div>
                </div>
            </form>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th width="5">ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Empregado</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listUsers}" var="p">
                    <c:if test="${p.permissao != 0}">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.nome}</td>
                            <td>${p.email}</td>
                            <td>
                                <c:if test="${p.permissao == 1}">
                                    ${p.empregado ? 'Sim' : 'Não'}
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
