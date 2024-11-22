package iflearn.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import iflearn.dto.UsuarioUpdateDTO;
import iflearn.entities.Usuario;
import iflearn.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository ur;

	
	public ResponseEntity<?> create(Usuario u) {
		if (u.getNome() == null || u.getSobrenome() == null || u.getEmail() == null || u.getSenha() == null
				|| u.getCategoria() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		if (ur.existsByEmail(u.getEmail()) == true) {
			return ResponseEntity.badRequest().body("email já cadastrado");
		}
		u.setUsuarioNovo(true);
		Usuario uNovo = ur.save(u);

		return ResponseEntity.ok(uNovo);
	}

	
	public ResponseEntity<?> read(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Usuario> uExistente = ur.findById(id);
		if (uExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			Usuario u = uExistente.get();
			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);

			return ResponseEntity.ok(u);
		}
	}

	
	public ResponseEntity<?> update(UsuarioUpdateDTO udto) {
		if (udto.id() == null || udto.nome() == null || udto.sobrenome() == null || udto.email() == null
				|| udto.categoria() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Optional<Usuario> uExistente = ur.findById(udto.id());
		if (uExistente.isEmpty())
			return ResponseEntity.notFound().build();
		
		// senha velha E senha atualizada forem nulas ao mesmo tempo OU que cada uma
		// seja nula individualmente
		if (udto.senhaVelha() == null && udto.senhaAtualizada() == null || udto.senhaVelha() == null
				|| udto.senhaAtualizada() == null) {
			Usuario u = new Usuario();
			u.setId(udto.id());
			u.setNome(udto.nome());
			u.setSobrenome(udto.sobrenome());
			u.setEmail(udto.email());
			u.setCategoria(udto.categoria());
			// setar a senha com a senha já existente no banco
			u.setSenha(uExistente.get().getSenha());

			Usuario uAtualizado = ur.save(u);

			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);

			return ResponseEntity.ok(uAtualizado);
		}
		// se senha velha e senh nova NÃO forem nulas e então, a senha velha conferir
		// com a que já existe no banco
		if (udto.senhaVelha().equals(uExistente.get().getSenha()) && udto.senhaAtualizada() != null) {
			Usuario u = new Usuario();
			u.setId(udto.id());
			u.setNome(udto.nome());
			u.setSobrenome(udto.sobrenome());
			u.setEmail(udto.email());
			u.setCategoria(udto.categoria());
			u.setSenha(udto.senhaAtualizada());

			Usuario uAtualizado = ur.save(u);

			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);

			return ResponseEntity.ok(uAtualizado);
		}
		return ResponseEntity.badRequest().body("a senha antiga não confere");
	}

	
	public ResponseEntity<?> delete(Integer id) {
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

	
	public ResponseEntity<List<Usuario>> listarTodos() {
		List<Usuario> lista = ur.findAll();
		for (Usuario u : lista) {
			u.setMateriais(null);
			u.setQuizzes(null);
			u.setPontos(null);
		}
		return ResponseEntity.ok(lista);
	}

	
	public ResponseEntity<?> login(Usuario u) {
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

		return ResponseEntity.ok(uExistente);
	}
	
	
	public ResponseEntity<?> trocaDeSenha(Usuario u) {
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

		// compara a senha do objeto que está chegando por parâmetro com a senha do banco
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