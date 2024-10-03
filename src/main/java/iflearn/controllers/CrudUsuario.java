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
<<<<<<< HEAD
	// pra que serve o "?"
	public ResponseEntity<?> create(@RequestBody Usuario u) {
		if (u.getNome() == null || u.getEmail() == null || u.getSenha() == null || u.getCategoria() == null
				|| u.getTurma() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		if(ur.existsByEmail(u.getEmail()) == true) {
			return ResponseEntity.badRequest().body("email já existe no banco");
=======
	public ResponseEntity<Usuario> create(@RequestBody Usuario u) {
		if (u.getNome() == null || u.getEmail() == null 
				|| u.getSenha() == null 
				|| u.getCategoria() == null
				|| u.getTurma() == null
				|| u.getUsuarioNovo()
				|| ur.existsByEmail(u.getEmail())== true) {
			return ResponseEntity.badRequest().build();
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
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
		if (uExistente.isEmpty())
			return ResponseEntity.badRequest().build();
		
		else {
			Usuario u = uExistente.get();
			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);
			
			return ResponseEntity.ok(u);
		}
	//		return ResponseEntity.ok(uExistente.get());
	}

	//como deixar atualizar pro mesmo email
	@PutMapping("/update")
<<<<<<< HEAD
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Usuario u) {
		if (u.getId() == null || u.getNome() == null || u.getEmail() == null || u.getSenha() == null
				|| u.getTurma() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
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
					return ResponseEntity.badRequest().body("o email já existe no banco");
				
			} catch (Exception e) {
				return ResponseEntity.badRequest().build();
			}
		}
=======
    @ResponseBody
    public ResponseEntity<Usuario> update(@RequestBody Usuario u) {        
        if (u.getId() == null || u.getNome() == null 
        		|| u.getEmail() == null 
				|| u.getSenha() == null 
				|| u.getTurma() == null
				|| ur.existsByEmail(u.getEmail())== true) {
			return ResponseEntity.badRequest().build();
		}

        Optional<Usuario> uExistente = ur.findById(u.getId());
        if (uExistente.isEmpty())
            return ResponseEntity.badRequest().build();
      
        else {
        	try {
    			u.setQuizzes(null);
    			u.setMateriais(null);
    			u.setPontos(null);
    			
    			Usuario uAtualizado = ur.save(u);
    			
    			return ResponseEntity.ok(uAtualizado);
    		} catch (Exception e) {
    			return ResponseEntity.badRequest().build(); 
    		}
    }
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	}

	
	@DeleteMapping("/delete/{id_usuario}")
	@ResponseBody
<<<<<<< HEAD
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
=======
	public ResponseEntity<Usuario> delete(@PathVariable(name = "id_usuario") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
      
        Optional<Usuario> uExistente = ur.findById(id);
        if (uExistente.isEmpty())
            return ResponseEntity.badRequest().build();
        
        else {
        	ur.deleteById(id);
    		return ResponseEntity.noContent().build();
    }
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listarTodos() {
		return ResponseEntity.ok(ur.findAll());
	}
	
	@PostMapping("/login")
	@ResponseBody
<<<<<<< HEAD
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
=======
	public ResponseEntity<Usuario> login(@RequestBody Usuario u){
		//não precisa da categoria pois assim que der certo o login, 
		//o back envia o usuario (*) pro front e ali contém a categoria
		 if (u.getEmail() == null || u.getSenha() == null) {
				return ResponseEntity.badRequest().build();
			}
		 
		 Usuario uExistente = ur.findByEmailAndSenha(u.getEmail(), u.getSenha());
		 if(uExistente == null) {
		 	return ResponseEntity.badRequest().build();
		 }		 
		 
		 // "limpa" o JSON de retorno - isto é, retorna apenas as informacoes necessarias neste momento...
		 uExistente.setQuizzes(null);
		 uExistente.setMateriais(null);
		 uExistente.setPontos(null);
		 
		 //*
		 return ResponseEntity.ok(uExistente);
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	}

	//não funfou ainda
	String senhaNova;
	@PostMapping("/trocaDeSenha")
	@ResponseBody
	public ResponseEntity<Usuario> trocaDeSenha(@RequestBody Usuario u) {
		// metodo de troca de senha, salvar + u.setUsuarioNovo(false)

<<<<<<< HEAD
		// alterei para nOT found, se o ID for nulo
		if (u.getId() == null) {
			return ResponseEntity.notFound().build();
		}

		// se a senha for nula ou em branco
		if (u.getSenha() == null || u.getSenha().trim().length() == 0) {
			return ResponseEntity.badRequest().body("senha está nula ou em branco");
=======
		// nesse caso, diferentemente do update, o front pede pro 
		// usuario preencher apenas a senha velha e a senha nova, o 
		// resto dos atributos preenchidos o front que envia sem pedir novamente pro usuario
		if (u.getId() == null || u.getSenha() == null 
				|| senhaNova == null 
			//ver se a senha velha e a senha nova são iguais
				|| u.getSenha().equals(senhaNova)) {
			return ResponseEntity.badRequest().build();
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
		}

		Optional<Usuario> uExistente = ur.findById(u.getId());
		if (uExistente.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
<<<<<<< HEAD

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
=======
		
			u.setSenha(senhaNova);			
			u.setUsuarioNovo(false);
			Usuario uAtualizado = ur.save(u);
			return ResponseEntity.ok(uAtualizado);
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	}

}
