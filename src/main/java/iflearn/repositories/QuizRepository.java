package iflearn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.*;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

}
