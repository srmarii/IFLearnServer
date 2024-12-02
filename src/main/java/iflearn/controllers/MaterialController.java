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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import iflearn.dto.MaterialResponse;
import iflearn.entities.Material;
import iflearn.services.MaterialService;

@Controller
@RequestMapping("/material")
public class MaterialController {

	@Autowired
	private MaterialService ms;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<?> create(@RequestParam String nome, @RequestParam Integer idUsuario) {
		return ms.create(nome, idUsuario);
	}
	
//	@PostMapping("/create")
//	@ResponseBody
//	public ResponseEntity<?> create(@RequestParam String nome, @RequestParam Integer idUsuario,
//			@RequestParam("arquivo") MultipartFile file) {
//		return ms.create(nome, idUsuario, file);
//	}

	@GetMapping("/read/{id_material}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_material") Integer id) {
		return ms.read(id);
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Material m) {
		return ms.update(m);
	}

	@DeleteMapping("/delete/{id_material}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(name = "id_material") Integer id) {
		return ms.delete(id);
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<MaterialResponse>> listarTodos() {
		return ms.listarTodos();
	}

}
