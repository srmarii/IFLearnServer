package iflearn.dto;

import iflearn.entities.Usuario;

// para calculaPontosU e rankings
public record RankingResponse(Integer id, 
		String nome,
		Integer categoria,
		Integer soma) {	
	
	public RankingResponse(Usuario u) {
		this(u.getId(), 
				u.getNome(), 
				u.getCategoria(),
				u.getSomaPontos());
	}
}
