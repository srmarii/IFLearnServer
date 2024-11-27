package iflearn.dto;

import iflearn.entities.Alternativa;

public record AlternativaResponseID(Integer id) {
	
	public AlternativaResponseID(Alternativa a){
		this(a.getId());
	}
}
