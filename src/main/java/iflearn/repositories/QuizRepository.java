package iflearn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.*;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

	
	Quiz findById(Long id);
	List<Quiz> findAll();
}
