package iflearn.controllers;

import java.util.List;

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

import iflearn.dto.QuizResponse;
import iflearn.entities.Pontuacao;
import iflearn.entities.Quiz;
import iflearn.services.QuizService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService qis;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody Quiz qi) {
		return qis.create(qi);
	}

	@GetMapping("/read/{id_quiz}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_quiz") Integer id) {
		return qis.read(id);
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Quiz qi) {
		return qis.update(qi);
	}

	@DeleteMapping("/delete/{id_quiz}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(name = "id_quiz") Integer id) {
		return qis.delete(id);
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<QuizResponse>> listarTodos() {
		return qis.listarTodos();
	}

	///////////////////////////////////////////////////////////////////

	@PostMapping("/calculaPontosQi")
	@ResponseBody
	public ResponseEntity<?> calculaPontosQi(@RequestBody Quiz qi) {
		return qis.calculaPontosQi(qi);
	}

	@GetMapping("/calculaPontosU/{id_usuario}")
	@ResponseBody
	public ResponseEntity<?> calculaPontosU(@PathVariable(name = "id_usuario") Integer id) {
		return qis.calculaPontosU(id);
	}

	@GetMapping("/ranking")
	@ResponseBody
	public ResponseEntity<?> ranking() {
		return qis.ranking();
	}

	@GetMapping("/teste")
	@ResponseBody
	public List<Pontuacao> teste() {
		Quiz qi = new Quiz();
		List<Pontuacao> a = qi.getPontos();
		return a;
	}

	@GetMapping("/realizouQuiz/{id_quiz}/{id_usuario}")
	@ResponseBody
	public boolean realizouQuiz(@PathVariable(name = "id_quiz") Integer idqi,
			@PathVariable(name = "id_usuario") Integer idu) {
		return qis.realizouQuiz(idqi, idu);

	}

}
