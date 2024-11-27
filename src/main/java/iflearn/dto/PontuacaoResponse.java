package iflearn.dto;

import iflearn.entities.Pontuacao;

public record PontuacaoResponse(Integer id, 
		Integer qtdPontos, 
		QuizResponse quiz) {

	public PontuacaoResponse(Pontuacao p) {
		this(p.getId(), 
				p.getQtdPontos(), 
				new QuizResponse(p.getQuiz()));
	}
}
