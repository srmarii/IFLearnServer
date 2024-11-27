package iflearn.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import iflearn.dto.AlternativaResponse;
import iflearn.entities.Alternativa;
import iflearn.repositories.AlternativaRepository;

@Service
public class AlternativaService {

	@Autowired
	private AlternativaRepository ar;

	public ResponseEntity<?> create(Alternativa a) {
		if (a.getDesc() == null || a.getCorreta() == null || a.getQuestao() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Alternativa aNova = ar.save(a);
		return ResponseEntity.ok(aNova);
	}

	public ResponseEntity<?> read(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Alternativa> aExistente = ar.findById(id);
		if (aExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			Alternativa a = aExistente.get();
			return ResponseEntity.ok(new AlternativaResponse(a));
		}
	}

	public ResponseEntity<?> update(Alternativa a) {
		if (a.getId() == null || a.getDesc() == null || a.getCorreta() == null || a.getQuestao() == null) {
			return ResponseEntity.badRequest().body("um dos parametros está nulo");
		}
		Optional<Alternativa> aExistente = ar.findById(a.getId());
		if (aExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			try {
//				Questao qu = qur.findById(a.getQuestao().getId()).get();
				Alternativa aAtualizada = ar.save(a);

				return ResponseEntity.ok(new AlternativaResponse(aAtualizada));

			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}

	public ResponseEntity<?> delete(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Alternativa> aExistente = ar.findById(id);
		if (aExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			ar.deleteById(id);
			return ResponseEntity.ok().body("alternativa deletada");
		}
	}

	public ResponseEntity<List<AlternativaResponse>> listarTodos() {
		List<Alternativa> lista = ar.findAll();
		return ResponseEntity.ok(lista.stream()
				.map(AlternativaResponse::new)
				.collect(Collectors.toList()));
	}

}
