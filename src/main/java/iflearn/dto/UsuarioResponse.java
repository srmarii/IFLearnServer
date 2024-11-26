package iflearn.dto;

import iflearn.entities.Usuario;

public record UsuarioResponse(Integer id, String nome, String sobrenome, String email, String senha, Integer categoria,
		boolean usuarioNovo, int somaPontos) {

	public UsuarioResponse(Usuario u) {
		this(u.getId(), u.getNome(), u.getSobrenome(), u.getEmail(), u.getSenha(), u.getCategoria(), u.getUsuarioNovo(),
				u.getSomaPontos());
	}
}
