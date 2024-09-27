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
import jakarta.validation.Valid;

//primeiro crud
@Controller
@RequestMapping("/usuario")
public class CrudUsuario {

	@Autowired
	private UsuarioRepository ur;

	@PostMapping("/create")
	@ResponseBody
	// valid pra testar a anotação @Email que coloquei no atributo email do Usuario, mas
	// não funcionou
	public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario u) {
		if (u.getNome() == null || u.getEmail() == null || u.getSenha() == null || u.getCategoria() == null
				|| u.getTurma() == null || ur.existsByEmail(u.getEmail()) == true) {
			return ResponseEntity.badRequest().build();
		}

		u.setUsuarioNovo(true);

		Usuario uNovo = ur.save(u);

		return ResponseEntity.ok(uNovo);
	}

	
	@GetMapping("/read/{id_usuario}")
	@ResponseBody
	public ResponseEntity<Usuario> read(@PathVariable(name = "id_usuario") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Usuario> uExistente = ur.findById(id);
		if (uExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		else {
			Usuario u = uExistente.get();
			
			//não deveria setar os atributos de cada objeto/lista, 
			//não o objeto/lista inteira?? pq dai se eu quiser ver 
			//quais quizzes/materiais/pontos o usuario possui eu não conseguiria
			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);

			return ResponseEntity.ok(u);
		}
		// return ResponseEntity.ok(uExistente.get());
	}

	@PutMapping("/update")
	@ResponseBody
	public ResponseEntity<Usuario> update(@RequestBody Usuario u) {
		if (u.getId() == null || u.getNome() == null || u.getEmail() == null || u.getSenha() == null
				|| u.getTurma() == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Usuario> uExistente = ur.findById(u.getId());
		if (uExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			try {
				//deixar atualizar pro mesmo email
				if (u.getEmail().equals(uExistente.get().getEmail()) 
						|| ur.existsByEmail(u.getEmail()) == false ) {

					u.setQuizzes(null);
					u.setMateriais(null);
					u.setPontos(null);

					Usuario uAtualizado = ur.save(u);

					return ResponseEntity.ok(uAtualizado);
				}
					return ResponseEntity.badRequest().build();
				
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
	}

	@DeleteMapping("/delete/{id_usuario}")
	@ResponseBody
	public ResponseEntity<Usuario> delete(@PathVariable(name = "id_usuario") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Usuario> uExistente = ur.findById(id);
		if (uExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			ur.deleteById(id);
			return ResponseEntity.noContent().build();
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
	public ResponseEntity<Usuario> login(@RequestBody Usuario u) {
		// não precisa da categoria pois assim que der certo o login,
		// o back envia o usuario (*) pro front e ali contém a categoria
		if (u.getEmail() == null || u.getSenha() == null) {
			return ResponseEntity.badRequest().build();
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
			return ResponseEntity.badRequest().build();
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
			return ResponseEntity.badRequest().body("Informe uma nova senha");
		}

		// atualiza o objeto que veio do banco com a senha nova e o novo status
		uExistente.get().setSenha(u.getSenha());
		uExistente.get().setUsuarioNovo(false);

//		u.setQuizzes(null);
//		u.setMateriais(null);
//		u.setPontos(null);

		// salva e retorna atualizado
		Usuario uAtualizado = ur.save(uExistente.get());

		return ResponseEntity.ok(uAtualizado);
	}

}
