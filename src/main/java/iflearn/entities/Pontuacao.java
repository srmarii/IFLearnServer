package iflearn.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Pontuacao {
	
	//atributos
	//id é a junção da chave estrangeira de quiz e usuário
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer qtdPontos;
	//-
	
	@ManyToOne
	@JoinColumn(name= "id_quiz")
	private Quiz quiz;
	
	@ManyToOne
	@JoinColumn(name= "id_usuario")
	private Usuario usuario;
	
//	@OneToMany(mappedBy = "pontuacao")
//	private List<Resposta> respostas = new ArrayList<>();

	@ManyToMany(mappedBy = "respostas")
	Set<Alternativa> respostas;
	
	//construtores
	public Pontuacao() {
		super();
	}
	public Pontuacao(Integer qtdPontos) {
		super();
		this.qtdPontos = qtdPontos;
	}
	//-
	
	
	//getters and setters
	public Integer getQtdPontos() {
		return qtdPontos;
	}
	public void setQtdPontos(Integer qtdPontos) {
		this.qtdPontos = qtdPontos;
	}
	//-
	

}
