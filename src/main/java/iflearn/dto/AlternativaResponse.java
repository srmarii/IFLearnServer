package iflearn.dto;

import iflearn.entities.Alternativa;

public record AlternativaResponse(Integer id, 
		String desc, 
		Boolean correta,
		QuestaoResponse questao) {
	
	public AlternativaResponse(Alternativa a) {
		this(a.getId(),
				a.getDesc(), 
				a.getCorreta(),
				new QuestaoResponse(a.getQuestao()));
	}

}
