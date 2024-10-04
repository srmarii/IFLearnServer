package iflearn.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iflearn.entities.*;

public interface PontuacaoRepository extends JpaRepository<Pontuacao, Integer> {

	Pontuacao findById(Long id);
	List<Pontuacao> findAll();
}
