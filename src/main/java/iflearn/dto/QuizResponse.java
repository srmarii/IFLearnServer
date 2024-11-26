package iflearn.dto;

import java.time.LocalDateTime;

import iflearn.entities.Quiz;

public record QuizResponse(Integer id, String desc, String nome, 
		UsuarioResponse usuario, LocalDateTime dataCriacao) {
	
	public QuizResponse(Quiz qi) {
		this(qi.getId(), qi.getDesc(), qi.getNome(), new UsuarioResponse(qi.getUsuario()), qi.getDataCriacao());
	}

}
