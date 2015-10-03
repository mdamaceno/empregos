<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Usuários</h2>
<div class="row">
    <div class="col-md-12">
        <table class="table">
            <thead>
                <tr>
                    <th width="5">ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Empregado</th>
                    <th>Permissão</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listUsers}" var="p">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.nome}</td>
                        <td>${p.email}</td>
                        <td>
                            <c:if test="${p.permissao == 1}">
                                ${p.empregado ? 'Sim' : 'Não'}
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${p.permissao == 0}">
                                Administrador
                            </c:if>
                            <c:if test="${p.permissao == 1}">
                                Candidato
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>