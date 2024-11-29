package iflearn.dto;

import java.util.List;
import java.util.stream.Collectors;

import iflearn.entities.Questao;

public record QuestaoResponse(Integer id, 
		String desc, 
		String explicacao, 
		QuizResponseID quiz,
		List<AlternativaResponseID> alternativas) {
	
	public QuestaoResponse(Questao qu) {
		this(qu.getId(), 
				qu.getDesc(), 
				qu.getExplicacao(), 
				new QuizResponseID(qu.getQuiz()),
				qu.getAlternativas().stream().map(AlternativaResponseID::new).collect(Collectors.toList()));
	}

}
