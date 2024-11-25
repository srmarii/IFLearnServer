package iflearn.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Registro {
	
	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime dataExecucaoQuiz = LocalDateTime.now();
	//-
	
	@ManyToOne
	private Quiz quiz;
	@ManyToOne
	private Usuario usuario; // o query method usa o nome do atributo mais o tipo, por isso deu erro... mas agora vai.
	
	//construtores
	public Registro(Quiz qi, Usuario u) {
		super();
		this.quiz = qi;
		this.usuario = u;
		this.dataExecucaoQuiz = LocalDateTime.now();
	}
	public Registro() {
		super();
	}
	//-
	
	//getters and setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public LocalDateTime getDataExecucaoQuiz() {
		return dataExecucaoQuiz;
	}
	public void setDataExecucaoQuiz(LocalDateTime dataExecucaoQuiz) {
		this.dataExecucaoQuiz = dataExecucaoQuiz;
	}
	
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz qi) {
		this.quiz = qi;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario u) {
		this.usuario = u;
	}
	//-
	
}
