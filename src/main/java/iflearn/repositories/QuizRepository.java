package iflearn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
