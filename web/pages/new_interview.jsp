<h2>Agendamento de Entrevista</h2>

<div class="row">
    <div class="col-md-12">
        <p><strong>Nome Completo</strong>: ${candidate.nome}</p>
        <p><strong>Telefone Fixo</strong>: ${candidate.telefone}</p>
        <p><strong>Celular</strong>: ${candidate.celular}</p>
        <p><strong>Escolaridade</strong>: ${candidate.escolaridade}</p>
        <p><strong>Empregado</strong>: ${candidate.empregado ? 'Sim' : 'Não'}</p>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <form method="POST" action="admin" class="form-inline">
            <input type="hidden" name="action" value="save_interview"/>
            <input type="hidden" name="candidate" value="${candidate.id}"/>
            <div class="form-group">
                <label>Dia da Entrevista</label>
                <input type="date" class="form-control" name="interview_date" placeholder="Exemplo: 10/10/2015" />
            </div>
            <button type="submit" class="btn btn-success">Confirmar</button>
        </form>
    </div>
</div>