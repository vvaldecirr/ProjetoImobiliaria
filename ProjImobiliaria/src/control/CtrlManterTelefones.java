package control;

import model.DAOTelefone;
import model.IDAO;
import model.ModelException;
import model.Proprietario;
import model.Telefone;
import view.IViewer;
import view.IViewerSalvaTelefone;
import view.JanelaExcluirProprietario;
import view.JanelaExcluirTelefone;
import view.JanelaSalvaProprietario;
import view.JanelaSalvaTelefone;
import view.JanelaTelefones;

public class CtrlManterTelefones implements ICtrlManter{
	
	private enum Operacao {
		INCLUSAO, EXCLUSAO, ALTERACAO, DISPONIVEL;
	}
	private ICtrlPrograma ctrlPrg;
	
	private IViewer jCadastro;
	
	private IViewerSalvaTelefone jTelefone;
	
	private Telefone telefoneAtual;
	
	private IDAO<Telefone> dao = DAOTelefone.getSingleton();

	private boolean emExecucao;
	
	private Operacao operacao;
	
	public CtrlManterTelefones(ICtrlPrograma p){
		this.ctrlPrg = p;
	}
	
	@Override
	public boolean iniciar() {
		if(this.emExecucao)
			return false;

		this.jCadastro = new JanelaTelefones(this);
		this.atualizarInterface();
		this.emExecucao = true;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean terminar() {
		if(!this.emExecucao)
			return false;

		this.jCadastro.setVisible(false);
		this.ctrlPrg.terminarCasoDeUsoManterTelefone();
		this.emExecucao = false;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarIncluir() {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.INCLUSAO;
		this.jTelefone = new JanelaSalvaTelefone(this);
		return true;
	}

	@Override
	public void cancelarIncluir() {
		if(this.operacao == Operacao.INCLUSAO) {
			this.jTelefone.setVisible(false);
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	public boolean incluir(String tipo, String num) throws ModelException {
		if(this.operacao != Operacao.INCLUSAO)
			return false;

		Telefone novo = new Telefone(tipo,num,null);

		dao.salvar(novo);

		this.jTelefone.setVisible(false);
		this.atualizarInterface();
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}
	
	@Override
	public boolean iniciarAlterar(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.ALTERACAO;
		this.telefoneAtual = dao.recuperar(pos);
		this.jTelefone = new JanelaSalvaTelefone(this);
		this.jTelefone.atualizarCampos(
				this.telefoneAtual.getTipo(),
				this.telefoneAtual.getNumero());
		
		return true;
	}

	@Override
	public void cancelarAlterar() {
		if(this.operacao == Operacao.ALTERACAO) {
			this.jTelefone.setVisible(false);
			this.telefoneAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}
	
	public boolean alterar(String tipo, String nome) throws ModelException {
		if(this.operacao != Operacao.ALTERACAO)
			return false;

		this.telefoneAtual.setTipo(tipo);
		this.telefoneAtual.setNumero(nome);
		

		dao.atualizar(this.telefoneAtual);

		this.jTelefone.setVisible(false);
		this.atualizarInterface();
		this.telefoneAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public boolean iniciarExcluir(int pos) {
		if(this.operacao != Operacao.DISPONIVEL)
			return false;

		this.operacao = Operacao.EXCLUSAO;
		this.telefoneAtual = dao.recuperar(pos);
		new JanelaExcluirTelefone(this, this.telefoneAtual);
		return true;
	}

	@Override
	public void cancelarExcluir() {
		if(this.operacao == Operacao.EXCLUSAO) {
			this.telefoneAtual = null;
			this.operacao = Operacao.DISPONIVEL;
		}
	}

	@Override
	public boolean excluir() throws ModelException {
		if(this.operacao != Operacao.EXCLUSAO)
			return false;

		dao.remover(this.telefoneAtual);

		this.atualizarInterface();
		this.telefoneAtual = null;
		this.operacao = Operacao.DISPONIVEL;
		return true;
	}

	@Override
	public void atualizarInterface() {
		this.jCadastro.limpar();

		for(int i = 0; i < dao.getNumObjs(); i++) {
			Telefone telefone = dao.recuperar(i);
			this.jCadastro.incluirLinha(telefone);
		}
	}

}