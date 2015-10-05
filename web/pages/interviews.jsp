<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h2>Reuniões Agendadas</h2>
<div class="row">
    <!-- <c:catch var="cnpj">${current_user.cnpj}</c:catch> -->
    <c:if test="${cnpj == null}">
        <div class="col-md-4">
            <form method="POST" action="admin">
                <input type="hidden" name="action" value="candidates"/>
                <button type="submit" class="btn btn-primary">Agendar Reunião</button>
            </form>
        </div>
    </c:if>
</div>


<div class="row">
    <div class="col-md-12">
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Candidato</th>
                    <th>Empresa</th>
                    <th>Data da Reunião</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listInterviews}" var="i">
                    <tr>
                        <td class="one">${i.id}</td>
                        <td>${i.pessoaId.nome}</td>
                        <td>${i.empresaId.nome}</td>
                        <td>${i.dataReuniao}</td>
                        <c:if test="${cnpj != null}">
                            <c:if test="${empty i.confirmacao}">
                                <td>
                                    <button type="submit" class="btn btn-xs btn-success yes">ACEITAR</button>
                                    <button type="submit" class="btn btn-xs btn-danger no">REJEITAR</button>
                                </td>
                            </c:if>
                        </c:if>
                        <c:if test="${empty i.confirmacao}"><td></td></c:if>
                        <c:if test="${i.confirmacao == 'yes'}">
                            <td><span class="label label-success">ACEITO</span></td>
                        </c:if>
                        <c:if test="${i.confirmacao == 'no'}">
                            <td><span class="label label-danger">REJEITADO</span></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<form method="POST" action="admin" id="confirm_interview_form">
    <input type="hidden" name="action" value="confirm_interview"/>
    <input type="hidden" name="yes_no" id="yes_no" />
    <input type="hidden" name="interview_id" id="interview_id" />
</form>

<script>
    $(document).ready(function () {
        $("button.yes").on("click", function () {
            $("#yes_no").val("yes");
            $("#interview_id").val($(this).closest('tr').children('td.one').text());
            $("form#confirm_interview_form").submit();
        });
        $("button.no").on("click", function () {
            $("#yes_no").val("no");
            $("#interview_id").val($(this).closest('tr').children('td.one').text());
            $("form#confirm_interview_form").submit();
        });
    });
</script>