package iflearn.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import iflearn.entities.Quiz;

public record QuizResponse(Integer id, 
		String desc, 
		String nome, 
		LocalDateTime dataCriacao, 
		UsuarioResponse usuario,
		List<QuestaoResponse> questoes) {

	public QuizResponse(Quiz qi) {
		this(qi.getId(), 
				qi.getDesc(), 
				qi.getNome(), 
				qi.getDataCriacao(), 
				new UsuarioResponse(qi.getUsuario()),
				qi.getQuestoes().stream().map(QuestaoResponse::new).collect(Collectors.toList()));
	}

}