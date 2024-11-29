package iflearn.dto;

import iflearn.entities.Pontuacao;

public record PontuacaoResponse(Integer id, 
		Integer qtdPontos, 
		Integer contadorQu,
		String feedback,
		QuizResponseID quiz) {

	public PontuacaoResponse(Pontuacao p, Integer contador, String feedback) {
		this(p.getId(), 
				p.getQtdPontos(),
				contador,
				feedback,
				new QuizResponseID(p.getQuiz()));
	}
}
