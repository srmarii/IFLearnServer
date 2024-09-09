package iflearn.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Resposta {

	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//-
	
	@ManyToOne
	@JoinColumn(name="id_pontuacao")
	private Pontuacao pontuacao;
	
	@ManyToOne
	@JoinColumn(name="id_alternativa")
	private Alternativa alternativa;

}
