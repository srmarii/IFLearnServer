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

import iflearn.entities.Alternativa;
import iflearn.services.AlternativaService;

@Controller
@RequestMapping("/alternativa")
public class AlternativaController {

	@Autowired
	private AlternativaService as;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody Alternativa a) {
		return as.create(a);
	}

	@GetMapping("/read/{id_alternativa}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_alternativa") Integer id) {
		return as.read(id);
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Alternativa a) {
		return as.update(a);
	}

	@DeleteMapping("/delete/{id_alternativa}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(name = "id_alternativa") Integer id) {
		return as.delete(id);
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Alternativa>> listarTodos() {
		return as.listarTodos();
	}

}
