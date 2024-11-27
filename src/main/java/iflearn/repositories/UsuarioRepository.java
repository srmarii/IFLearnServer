package iflearn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByEmailAndSenha(String email, String senha);
	Boolean existsByEmail(String email);	
}
