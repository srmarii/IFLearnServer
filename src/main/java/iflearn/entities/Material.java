package iflearn.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Material {

	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 2)
	private String turma;
	private Integer data;
	//-
	
	@ManyToOne
	@JoinColumn(name= "id_usuario")
	private Usuario usuario;
	
	
	//construtores
	public Material() {
		super();
	}
	public Material(Integer id, String turma, Integer data) {
		super();
		this.id = id;
		this.turma = turma;
		this.data = data;
	}
	//-
	
	
	//getters and setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTurma() {
		return turma;
	}
	public void setTurma(String turma) {
		this.turma = turma;
	}
	
	public Integer getData() {
		return data;
	}
	public void setData(Integer data) {
		this.data = data;
	}
	//-
	
}
