package iflearn.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iflearn.entities.Alternativa;
import iflearn.entities.Questao;
import iflearn.repositories.AlternativaRepository;
import iflearn.repositories.QuestaoRepository;

//quinto crud
@Controller
@RequestMapping("/alternativa")
public class CrudAlternativa {

	@Autowired
	private AlternativaRepository ar;

	@Autowired
	QuestaoRepository qur;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Alternativa> create(@RequestBody Alternativa a) {
		if (a.getDesc() == null || a.getCorreta() == null || a.getQuestao() == null) {

			return ResponseEntity.badRequest().build();
		}

		Alternativa aNova = ar.save(a);
		return ResponseEntity.ok(aNova);
	}

	@GetMapping("/read/{id_quiz}")
	@ResponseBody
	public ResponseEntity<Alternativa> read(@PathVariable(name = "id_quiz") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Alternativa> aExistente = ar.findById(id);
		if (aExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			Alternativa a = aExistente.get();
			Questao qu = a.getQuestao();
			qu.setAlternativas(null);
			qu.setQuiz(null);

			a.setQuestao(qu);
			a.setRespostas(null);

			return ResponseEntity.ok(a);
		}
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<Alternativa> update(@RequestBody Alternativa a) {
		if (a.getId() == null || a.getDesc() == null || a.getCorreta() == null || a.getQuestao() == null) {

			return ResponseEntity.badRequest().build();
		}

		Optional<Alternativa> aExistente = ar.findById(a.getId());
		if (aExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			try {
				Questao qu = qur.findById(a.getQuestao().getId()).get();

				Alternativa aAtualizada = ar.save(a);

				qu.setAlternativas(null);
				qu.setQuiz(null);

				aAtualizada.setQuestao(qu);
				aAtualizada.setRespostas(null);

				aAtualizada.setQuestao(qu);

				return ResponseEntity.ok(aAtualizada);
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}

	@DeleteMapping("/delete/{id_quiz}")
	@ResponseBody
	public ResponseEntity<Alternativa> delete(@PathVariable(name = "id_quiz") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Alternativa> aExistente = ar.findById(id);
		if (aExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			ar.deleteById(id);
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Alternativa>> listarTodos() {
		return ResponseEntity.ok(ar.findAll());
	}

}
