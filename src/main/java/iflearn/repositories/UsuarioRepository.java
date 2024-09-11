package iflearn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	//List<Usuario> findBySenha(String senha);
	//List<Usuario> findByNomeContaining(String substring);
	Usuario findById(Long id);
	//Usuario findOne(Long id);
	Usuario deleteById(Long id);
	List<Usuario> findAll();
	
	Boolean existsByNome(String nome);	
	Boolean existsByEmail(String email);
	Boolean existsBySenha(String senha); 
	Boolean existsByCategoria(String categoria);
	Boolean existsByTurma(String turma);
	
	
}
