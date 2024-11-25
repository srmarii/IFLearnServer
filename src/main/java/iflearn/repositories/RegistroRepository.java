package iflearn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.Quiz;
import iflearn.entities.Registro;
import iflearn.entities.Usuario;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {

	// consulta para buscar os registros de um determinardo quiz
	Optional<Registro> findByQuizAndUsuario(Quiz qi, Usuario u); // esse aqui
	
	// Registro findById(Long idQi, Long idU);

}
