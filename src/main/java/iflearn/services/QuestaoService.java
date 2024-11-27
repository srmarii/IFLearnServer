package iflearn.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import iflearn.dto.QuestaoResponse;
import iflearn.entities.Questao;
import iflearn.repositories.QuestaoRepository;

@Service
public class QuestaoService {

	@Autowired
	private QuestaoRepository qur;

	public ResponseEntity<?> create(Questao qu) {
		if (qu.getDesc() == null || qu.getQuiz() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Questao quNova = qur.save(qu);
		return ResponseEntity.ok(quNova);
	}

	public ResponseEntity<?> read(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Questao> quExistente = qur.findById(id);
		if (quExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			Questao qu = quExistente.get();
			return ResponseEntity.ok(new QuestaoResponse(qu));
		}
	}

	public ResponseEntity<?> update(Questao qu) {
		if (qu.getId() == null || qu.getDesc() == null || qu.getQuiz() == null || qu.getAlternativas() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Optional<Questao> quExistente = qur.findById(qu.getId());
		if (quExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			try {
//				Quiz qi = qir.findById(qu.getQuiz().getId()).get();
				Questao quAtualizado = qur.save(qu);
				return ResponseEntity.ok(new QuestaoResponse(quAtualizado));
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}

	public ResponseEntity<?> delete(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Questao> quExistente = qur.findById(id);
		if (quExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			qur.deleteById(id);
			return ResponseEntity.ok().body("questão deletada");
		}
	}

	public ResponseEntity<List<QuestaoResponse>> listarTodos() {
		List<Questao> lista = qur.findAll();
		return ResponseEntity.ok(lista.stream()
				.map(QuestaoResponse::new)
				.collect(Collectors.toList()));
	}

}
