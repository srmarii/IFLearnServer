package iflearn.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;

@Entity
public class Usuario {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 50)
	private String nome;
	//@Email
	private String email;
	@Column(length = 15)
	private String senha;
	private String categoria;
	@Column(length = 2)
	private String turma;
	private Boolean usuarioNovo;
	// -

	@OneToMany(mappedBy = "usuario")
	private List<Quiz> quizzes = new ArrayList<>();

	@OneToMany(mappedBy = "usuario")
	private List<Pontuacao> pontos = new ArrayList<>();

	@OneToMany(mappedBy = "usuario")
	private List<Material> materiais = new ArrayList<>();

	// construtores
	public Usuario() {
		super();
	}

	public Usuario(Integer id, String nome, String email, String senha, String categoria, String turma,
			boolean usuarioNovo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.categoria = categoria;
		this.turma = turma;
		this.usuarioNovo = usuarioNovo;
	}

	// -

	// getters and setters
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTurma() {
		return turma;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}

	public Boolean getUsuarioNovo() {
		return usuarioNovo;
	}
	public void setUsuarioNovo(Boolean usuarioNovo) {
		this.usuarioNovo = usuarioNovo;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}
	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public List<Pontuacao> getPontos() {
		return pontos;
	}
	public void setPontos(List<Pontuacao> pontos) {
		this.pontos = pontos;
	}

	public List<Material> getMateriais() {
		return materiais;
	}
	public void setMateriais(List<Material> materiais) {
		this.materiais = materiais;
	}
	// -
}
