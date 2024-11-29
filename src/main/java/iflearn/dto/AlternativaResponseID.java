package iflearn.dto;

import iflearn.entities.Alternativa;

public record AlternativaResponseID(Integer id, String desc, Boolean correta) {
	
	public AlternativaResponseID(Alternativa a){
		this(a.getId(), 
				a.getDesc(), 
				a.getCorreta());
	}
}
