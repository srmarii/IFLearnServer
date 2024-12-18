package iflearn.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	private Integer categoria;
	private Boolean usuarioNovo;
	private int somaPontos;
	// -

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private List<Quiz> quizzes = new ArrayList<>();

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private List<Pontuacao> pontos = new ArrayList<>();

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private List<Material> materiais = new ArrayList<>();
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private List<Registro> registros = new ArrayList<>();

	// construtores
	public Usuario() {
		super();
	}

	public Usuario(Integer id, String nome, String sobrenome, String email, String senha, Integer categoria,
			Boolean usuarioNovo, int somaPontos) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.categoria = categoria;
		this.usuarioNovo = usuarioNovo;
		this.somaPontos = somaPontos;
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
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

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Boolean getUsuarioNovo() {
		return usuarioNovo;
	}

	public void setUsuarioNovo(Boolean usuarioNovo) {
		this.usuarioNovo = usuarioNovo;
	}
	
	public int getSomaPontos() {
		return somaPontos;
	}
	public void setSomaPontos(int somaPontos) {
		this.somaPontos = somaPontos;
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

	public List<Registro> getRegistros() {
		return registros;
	}
	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
	// -
}
