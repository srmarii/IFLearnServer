//package iflearn.dto;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.http.ResponseEntity;
//
//import iflearn.entities.Questao;
//import iflearn.entities.Quiz;
//
//public record QuizResponse(Integer id, 
//		String desc, 
//		String nome, 
//		LocalDateTime dataCriacao,
//		UsuarioResponse usuario,
//		List<QuestaoResponseID> questoes) {
//
//	public QuizResponse(Quiz qi) {
//		this(qi.getId(), 
//				qi.getId(),
//	            qi.getDesc(),
//	            qi.getNome(),
//	            qi.getDataCriacao(),
//	            new UsuarioResponse(qi.getUsuario()),
//	            qi.getQuestoes().stream()
//	              .map(QuestaoResponseID::new)
//	              .collect(Collectors.toList()) 
//						);
//	}
//	
//}

package iflearn.dto;

import java.time.LocalDateTime;

import iflearn.entities.Quiz;

public record QuizResponse(Integer id, 
		String desc, 
		String nome, 
		LocalDateTime dataCriacao,
		UsuarioResponse usuario) {

	public QuizResponse(Quiz qi) {
		this(qi.getId(), 
				qi.getDesc(), 
				qi.getNome(), 
				qi.getDataCriacao(),
				new UsuarioResponse(qi.getUsuario()));
	}

}