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
public class Alternativa {
	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name= "descricao", length = 300)
	private String desc;
	private Boolean correta = false;
	//-
	
	@OneToMany(mappedBy = "alternativa")
	private List<Resposta> respostas = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name= "id_questao")
	private Questao questao;
	
	
	//construtores
	public Alternativa() {
		super();
	}
	public Alternativa(Integer id, String desc, Boolean correta) {
		super();
		this.id = id;
		this.desc = desc;
		this.correta = correta;
	}
	//-

	
	//getters and setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getCorreta() {
		return correta;
	}
	public void setCorreta(Boolean correta) {
		this.correta = correta;
	}
	//-
		

}
	