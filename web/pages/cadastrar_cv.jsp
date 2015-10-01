<h1>Cadastrar Currículo</h1>

<div class="row">
    <div class="col-md-12">
        <form method="POST" action="admin" class="form-horizontal">
            <input type="hidden" name="action" value="create_resume" />
            
            <div class="form-group">
                <label class="col-sm-2 control-label">Nome</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_name" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Email</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_email" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Telefone Fixo</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_phone" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Celular</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_cellphone" />
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">Escolaridade</label>
                <div class="col-sm-10">
                    <select class="form-control" name="resume_school">
                        <option value="">Selecione</option>
                        <option value="fundamental">Fundamental</option>
                        <option value="medio">Médio</option>
                        <option value="superior">Superior</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Função 1</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_function_1" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Função 2</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_function_2" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">Função 3</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="resume_function_3" />
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
                        <input type="radio" name="resume_working" value="1"> Sim
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="resume_working" value="0"> Não
                    </label>
                </div>

            </div>
            <div class="pull-right">
                <button type="submit" class="btn btn-success">Cadastrar</button>
            </div>
        </form>
    </div>
</div>