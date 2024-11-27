package iflearn.dto;

//para update utilizando senha nova e senha antiga para a verificação
public record UsuarioRequest(Integer id, 
		String nome, 
		String sobrenome, 
		String email, 
		Integer categoria,
		String senhaVelha, 
		String senhaAtualizada, 
		Integer somaPontos, 
		Boolean usuarioNovo) {

}
