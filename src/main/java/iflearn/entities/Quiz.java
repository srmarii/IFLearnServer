package iflearn.entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Quiz {

	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 100)
	private String nome;
	@Column(name= "descricao", length = 300)
	private String desc;
	@Column
	private String feedback;
	
	//transformar o tipo pra data!
	@Column
	private Integer dataCriacao;
	
	//como atualizar no banco que apaguei a coluna nota??
//	@Column
//	private Double nota;
	//-
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "quiz")
	private List<Pontuacao> pontos = new ArrayList<>();
	
	@OneToMany(mappedBy = "quiz")
	private List<Questao> questoes = new ArrayList<>();
	
	
	//construtores
	public Quiz() {
		super();
	}
	public Quiz(Integer id, String nome, String desc, String feedback, Integer dataCriacao, double nota) {
		super();
		this.id = id;
		this.nome = nome;
		this.desc = desc;
		this.feedback = feedback;
		this.dataCriacao = dataCriacao;
	//	this.nota = nota;
	}
	//-
	
	
	//getters and setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public Integer getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Integer dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
//	public Double getNota() {
//		return nota;
//	}
//	public void setNota(Double nota) {
//		this.nota = nota;
//	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Pontuacao> getPontos() {
		return pontos;
	}
	public void setPontos(List<Pontuacao> pontos) {
		this.pontos = pontos;
	}
	
	public List<Questao> getQuestoes() {
		return questoes;
	}
	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}
	//-
	
	
	
}
