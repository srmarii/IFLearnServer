package iflearn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.*;

public interface MaterialRepository extends JpaRepository<Material, Integer>{
	Material findById(Long id);
	Material deleteById(Long id);
	List<Material> findAll();
	
	Boolean existsByNome(String nome);	
	Boolean existsByTurma(String turma);

}
