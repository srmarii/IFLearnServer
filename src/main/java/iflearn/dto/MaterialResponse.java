package iflearn.dto;

import java.time.LocalDateTime;

import iflearn.entities.Material;

public record MaterialResponse(Integer id, 
		String nome, 
		LocalDateTime data, 
		UsuarioResponse usuario) {

	public MaterialResponse(Material m) {
		this(m.getId(), 
				m.getNome(), 
				m.getDataCriacao(),
				new UsuarioResponse(m.getUsuario()));
	}
}
