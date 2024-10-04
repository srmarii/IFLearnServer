package iflearn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.*;

public interface QuestaoRepository extends JpaRepository<Questao, Integer>{

	
	Questao findById(Long id);
	List<Questao> findAll();
}
