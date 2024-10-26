package iflearn.dto;

public record UsuarioUpdateDTO(Integer id, String nome, String sobrenome, String email, Integer categoria,
		String senhaVelha, String senhaAtualizada) {

}
