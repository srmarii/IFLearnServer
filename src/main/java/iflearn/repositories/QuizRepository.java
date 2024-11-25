package iflearn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

	
//	Quiz findById(Long id); // isso é novo? sim, tava tentando fazer o metodo de retirnar se o usuario realizou ou não o quiz , mas ess emtodo seria no registro, lembra... 
	// List<Quiz> findAll(); // findall ja existe
}
