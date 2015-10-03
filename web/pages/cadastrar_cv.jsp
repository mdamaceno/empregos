<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2>Minhas Informações</h2>

<div class="row">
    <div class="col-md-12">
        <form method="POST" action="admin" class="form-horizontal">
            <input type="hidden" name="action" value="create_resume" />

            <input type="hidden" name="resume_id" value="${user.id}" />

            <div class="form-group">
                <label class="col-sm-2 control-label">Nome</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_name" value="${user.nome}" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Email</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_email" value="${user.email}" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Telefone Fixo</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_phone" value="${user.telefone}" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Celular</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_cellphone" value="${user.celular}" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">Escolaridade</label>
                <div class="col-sm-10">
                    <select class="form-control" name="resume_school">
                        <option value="">Selecione</option>
                        <option value="fundamental" ${user.escolaridade == 'fundamental' ? 'selected' : ''}>Fundamental</option>
                        <option value="medio" ${user.escolaridade == 'medio' ? 'selected' : ''}>Médio</option>
                        <option value="superior" ${user.escolaridade == 'superior' ? 'selected' : ''}>Superior</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Função 1</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_function_1" value="${user.funcao1}" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Função 2</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_function_2" value="${user.funcao2}" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Função 3</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_function_3" value="${user.funcao3}" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Senha</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" name="resume_password" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Confirmação de senha</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" name="resume_password_confirmation" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Empregado</label>
                <div class="col-sm-10">
                    <label class="radio-inline">
                        <input type="radio" name="resume_working" value="1" ${user.empregado == true ? 'checked' : ''}> Sim
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="resume_working" value="0" ${user.empregado == false ? 'checked' : ''}> Não
                    </label>
                </div>
            </div>

            <c:if test="${user.permissao == 0}">
                  <div class="form-group">
                    <label class="col-sm-2 control-label">Permissão</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="resume_role">
                            <option value="">Selecione</option>
                            <option value="0" ${user.permissao == 0 ? 'selected' : ''}>Administrador</option>
                            <option value="1" ${user.permissao == 1 ? 'selected' : ''}>Candidato</option>
                        </select>
                    </div>
                </div>
                <div class="pull-right">
                    <button type="submit" class="btn btn-success">Salvar</button>
                </div>
            </c:if>
        </form>
    </div>
</div>