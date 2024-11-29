package iflearn.dto;

import java.time.LocalDateTime;

import iflearn.entities.Quiz;

public record QuizResponseID(Integer id, 
		String nome, 
		String desc,
		LocalDateTime dataCriacao) {
	
	public QuizResponseID(Quiz qi) {
		this(qi.getId(), 
				qi.getNome(),
				qi.getDesc(),
				qi.getDataCriacao());
	}

}
