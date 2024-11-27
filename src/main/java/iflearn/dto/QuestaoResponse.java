package iflearn.dto;

import iflearn.entities.Questao;

public record QuestaoResponse(Integer id, 
		String desc, 
		String explicacao, 
		QuizResponse quiz) {
	
	public QuestaoResponse(Questao qu) {
		this(qu.getId(), 
				qu.getDesc(), 
				qu.getExplicacao(), 
				new QuizResponse(qu.getQuiz()));
	}

}
