package iflearn.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RegistroHora {
	
	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDateTime dataExecucaoQuiz = LocalDateTime.now();
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
	//-
	
}
