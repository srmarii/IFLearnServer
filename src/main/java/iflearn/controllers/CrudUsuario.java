package iflearn.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iflearn.entities.Usuario;
import iflearn.repositories.UsuarioRepository;

//primeiro crud
@Controller
@RequestMapping("/usuario")
public class CrudUsuario {

	@Autowired
	private UsuarioRepository ur;

	@PostMapping("/create")
	@ResponseBody
	// pra que serve o "?"
	public ResponseEntity<?> create(@RequestBody Usuario u) {
		if (u.getNome() == null || u.getSobrenome() == null || u.getEmail() == null || u.getSenha() == null
				|| u.getCategoria() == null || u.getTurma() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		if (ur.existsByEmail(u.getEmail()) == true) {
			return ResponseEntity.badRequest().body("email já existe no banco");
		}

		u.setUsuarioNovo(true);

		Usuario uNovo = ur.save(u);

		return ResponseEntity.ok(uNovo);
	}

	@GetMapping("/read/{id_usuario}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_usuario") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Usuario> uExistente = ur.findById(id);
		if (uExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		else {
			Usuario u = uExistente.get();

			// não deveria setar os atributos de cada objeto/lista,
			// não o objeto/lista inteira?? pq dai se eu quiser ver
			// quais quizzes/materiais/pontos o usuario possui eu não conseguiria
			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);

			return ResponseEntity.ok(u);
		}
		// return ResponseEntity.ok(uExistente.get());
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Usuario u) {
		if (u.getId() == null || u.getNome() == null || u.getSobrenome() == null || u.getEmail() == null
				|| u.getSenha() == null || u.getTurma() == null)
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		

		Optional<Usuario> uExistente = ur.findById(u.getId());
		if (uExistente.isEmpty())
			return ResponseEntity.notFound().build();

		String senhaVelha = "";

		if (senhaVelha.equals(uExistente.get().getSenha())) {

			// deixar atualizar pro mesmo email
			if (u.getEmail().equals(uExistente.get().getEmail()) || ur.existsByEmail(u.getEmail()) == false) {

				u.setQuizzes(null);
				u.setMateriais(null);
				u.setPontos(null);

				Usuario uAtualizado = ur.save(u);

				return ResponseEntity.ok(uAtualizado);
			}
			return ResponseEntity.badRequest().body("o email já existe no banco");
		}
		return ResponseEntity.badRequest().body("a senha antiga não confere");
	}

	@DeleteMapping("/delete/{id_usuario}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(name = "id_usuario") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Usuario> uExistente = ur.findById(id);
		if (uExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			ur.deleteById(id);
			return ResponseEntity.ok().body("usuario deletado");
		}
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listarTodos() {

		List<Usuario> lista = ur.findAll();

		for (Usuario u : lista) {
			u.setMateriais(null);
			u.setQuizzes(null);
			u.setPontos(null);
		}

		return ResponseEntity.ok(lista);
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody Usuario u) {
		if (u.getEmail() == null || u.getSenha() == null) {
			return ResponseEntity.badRequest().body("um dos parametros está nulo");
		}

		Usuario uExistente = ur.findByEmailAndSenha(u.getEmail(), u.getSenha());
		if (uExistente == null) {
			return ResponseEntity.notFound().build();
		}

		// "limpa" o JSON de retorno - isto é, retorna apenas as informacoes necessarias
		// neste momento...
		uExistente.setQuizzes(null);
		uExistente.setMateriais(null);
		uExistente.setPontos(null);

		// *
		return ResponseEntity.ok(uExistente);
	}

	@PostMapping("/trocaDeSenha")
	@ResponseBody
	public ResponseEntity<?> trocaDeSenha(@RequestBody Usuario u) {

		// alterei para nOT found, se o ID for nulo
		if (u.getId() == null) {
			return ResponseEntity.notFound().build();
		}

		// se a senha for nula ou em branco
		if (u.getSenha() == null || u.getSenha().trim().length() == 0) {
			return ResponseEntity.badRequest().body("senha está nula ou em branco");
		}

		// busca do banco com base no ID informado pelo front
		Optional<Usuario> uExistente = ur.findById(u.getId());

		// not found, assim o front pode saber exatamente o que houve
		if (uExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		// compara a senha do objeto que está chegando por parâmetro com a senha do
		// banco
		if (u.getSenha().equals(uExistente.get().getSenha())) {
			return ResponseEntity.badRequest().body("senha é igual a anterior");
		}

		// atualiza o objeto que veio do banco com a senha nova e o novo status
		uExistente.get().setSenha(u.getSenha());
		uExistente.get().setUsuarioNovo(false);

		// salva e retorna atualizado
		Usuario uAtualizado = ur.save(uExistente.get());

		uExistente.get().setQuizzes(null);
		uExistente.get().setMateriais(null);
		uExistente.get().setPontos(null);

		return ResponseEntity.ok(uAtualizado);
	}

}
