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

import iflearn.dto.QuestaoResponse;
import iflearn.entities.Questao;
import iflearn.services.QuestaoService;

@Controller
@RequestMapping("/questao")
public class QuestaoController {

	@Autowired
	private QuestaoService qus;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody Questao qu) {
		return qus.create(qu);
	}

	@GetMapping("/read/{id_questao}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_questao") Integer id) {
		return qus.read(id);
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Questao qu) {
		return qus.update(qu);
	}

	@DeleteMapping("/delete/{id_questao}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(name = "id_questao") Integer id) {
		return qus.delete(id);
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<QuestaoResponse>> listarTodos() {
		return qus.listarTodos();
	}

}
