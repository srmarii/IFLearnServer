package iflearn.dto;

import iflearn.entities.Questao;

public record QuestaoResponseID(Integer id) {
	
	public QuestaoResponseID(Questao qu) {
		this(qu.getId());
	}

}
