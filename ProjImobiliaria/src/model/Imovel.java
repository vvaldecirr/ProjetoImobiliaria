package model;

import java.util.Set;
import java.util.TreeSet;

public class Imovel {
	
	private int cod;
	private String uf;
	private String cidade;
	private String bairro;
	private String logradouro;
	private int numero;
	private String complemento;
	private float valorBase;
	private String dimensoes;
	private int qtdQuartos;
	private String descricao;
	private String finalidade;
	private String tipo;
	private boolean status;
	private Proprietario proprietario;
	private Set<Contrato> listaContratos;
		
	public Imovel(int cod, String uf, String cidade, String bairro,
			String logradouro, int numero, String complemento, float valorBase,
			String dimensoes, int qtdQuartos, String descricao,
			String finalidade, String tipo, boolean status, Proprietario proprietario) {
		this.setCod(cod);
		this.setUf(uf);
		this.setCidade(cidade);
		this.setBairro(bairro);
		this.setLogradouro(logradouro);
		this.setNumero(numero);
		this.setComplemento(complemento);
		this.setValorBase(valorBase);
		this.setDimensoes(dimensoes);
		this.setQtdQuartos(qtdQuartos);
		this.setDescricao(descricao);
		this.setFinalidade(finalidade);
		this.setStatus(status);
		this.setProprietario(proprietario);
		this.setTipo(tipo);
		this.listaContratos = new TreeSet<Contrato>();
	}

	public int getCod() {
		return this.cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public float getValorBase() {
		return this.valorBase;
	}

	public void setValorBase(float valorBase) {
		this.valorBase = valorBase;
	}

	public String getDimensoes() {
		return this.dimensoes;
	}

	public void setDimensoes(String dimensoes) {
		this.dimensoes = dimensoes;
	}

	public int getQtdQuartos() {
		return this.qtdQuartos;
	}

	public void setQtdQuartos(int qtdQuartos) {
		this.qtdQuartos = qtdQuartos;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFinalidade() {
		return this.finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public String getTipo() {
		return this.tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Proprietario getProprietario() {
		return this.proprietario;
	}

	/**
	 * Método que garante o relacionamento entre o Imovel e Proprietario
	 * seguindo as probilidades eventuais:
	 * @param proprietario
	 */
	public void setProprietario(Proprietario proprietario) {
		if(this.proprietario == proprietario)
			return;
		
		if(proprietario == null){
			Proprietario antigo = this.proprietario;
			this.proprietario = null;
			antigo.removeImovel(this);
		}else{
			if(this.proprietario !=null)
				this.proprietario.removeImovel(this);
			this.proprietario = proprietario;
			proprietario.addImovel(this);
		}
	}
	
	public void addContrato(Contrato novoContrato){
		if(this.listaContratos.contains(novoContrato))
			return;
		
		this.listaContratos.add(novoContrato);
		novoContrato.setImovel(this);
	}
	
	public void removeContrato(Contrato exContrato){
		if(! this.listaContratos.contains(exContrato))
			return;
		this.listaContratos.remove(exContrato);
		exContrato.setImovel(null);
	}

	public Set<Contrato> getContratos(){
		return this.listaContratos;
	}
}
