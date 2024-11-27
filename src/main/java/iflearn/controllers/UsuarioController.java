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

import iflearn.dto.UsuarioRequest;
import iflearn.dto.UsuarioResponse;
import iflearn.entities.Usuario;
import iflearn.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService us;

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody Usuario u) {
		return us.create(u);
	}

	@GetMapping("/read/{id_usuario}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_usuario") Integer id) {
		return us.read(id);
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody UsuarioRequest udto) {
		return us.update(udto);
	}

	@DeleteMapping("/delete/{id_usuario}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(name = "id_usuario") Integer id) {
		return us.delete(id);
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<UsuarioResponse>> listarTodos() {
		return us.listarTodos();
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody Usuario u) {
		return us.login(u);
	}

	@PostMapping("/trocaDeSenhaProfessor")
	@ResponseBody
	public ResponseEntity<?> trocaDeSenhaProfessor(@RequestBody Usuario u) {
		return us.trocaDeSenhaProfessor(u);
	}

}
