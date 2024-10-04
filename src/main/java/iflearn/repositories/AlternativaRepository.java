package iflearn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.*;

public interface AlternativaRepository extends JpaRepository<Alternativa, Integer>{

	
	Alternativa findById(Long id);
	List<Alternativa> findAll();
}
