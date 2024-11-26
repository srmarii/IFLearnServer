package iflearn.dto;

public record UpdateUsuarioRequest(Integer id, String nome, String sobrenome, String email, Integer categoria,
		String senhaVelha, String senhaAtualizada, Integer somaPontos, Boolean usuarioNovo) {

}
