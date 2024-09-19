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
	public ResponseEntity<Usuario> create(@RequestBody Usuario u) {
		if (u.getNome() == null || u.getEmail() == null 
				|| u.getSenha() == null 
				|| u.getCategoria() == null
				|| u.getTurma() == null
				|| ur.existsByEmail(u.getEmail())== true) {
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

		Optional<Usuario> hasUser = ur.findById(id);
		if (hasUser.isEmpty())
			return ResponseEntity.badRequest().build();
		
		else
			return ResponseEntity.ok(hasUser.get());
	}

	@PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Usuario> update(@RequestBody Usuario u) {        
        if (u.getId() == null || u.getNome() == null 
        		|| u.getEmail() == null 
				|| u.getSenha() == null 
				|| u.getTurma() == null
				|| ur.existsByEmail(u.getEmail())== true) {
			return ResponseEntity.badRequest().build();
		}

        Optional<Usuario> hasUser = ur.findById(u.getId());
        if (hasUser.isEmpty())
            return ResponseEntity.badRequest().build();
      
        else {
	        Usuario uAtualizado = ur.save(u);
	        return ResponseEntity.ok(uAtualizado);
    }
	}

	
	@DeleteMapping("/delete/{id_usuario}")
	@ResponseBody
	public ResponseEntity<Usuario> delete(@PathVariable(name = "id_usuario") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
      
        Optional<Usuario> hasUser = ur.findById(id);
        if (hasUser.isEmpty())
            return ResponseEntity.badRequest().build();
        
        else {
        	ur.deleteById(id);
    		return ResponseEntity.noContent().build();
    }
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listarTodos() {
		return ResponseEntity.ok(ur.findAll());
	}
	
	//não funfou ainda
//	@PostMapping("/login")
//	@ResponseBody
//	public ResponseEntity<Usuario> login(@RequestBody Usuario u){
//		 if (u.getNome() == null || u.getEmail() == null 
//					|| u.getSenha() == null 
//					|| u.getCategoria() == null
//					|| u.getTurma() == null) {
//				return ResponseEntity.badRequest().build();
//			}
//		 //como ver se existe cada atributo pro mesmo id?
//		 if(ur.existsByNome(u.getNome())== false 
//				 || ur.existsByEmail(u.getEmail())== false
//				 || ur.existsBySenha(u.getSenha())== false
//				 || ur.existsByCategoria(u.getCategoria())== false
//				 || ur.existsByTurma(u.getTurma())== false) {
//			 	return ResponseEntity.badRequest().build();
//		 }
//		 
//		 
//		 //front
//		if(u.getCategoria().equalsIgnoreCase("professor") && u.getUsuarioNovo() == true) {
//			trocaDeSenha(u);
//		}
//		
////		return ResponseEntity.ok(ur.findById(u.getId()));
//		return ResponseEntity.ok(u);
//	}
	
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<Usuario> login(@RequestBody Usuario u){
		
		//não precisa da categoria pois assim que der certo o login, 
		//o back envia o usuario (*) pro front e ali contém a categoria
		 if (u.getEmail() == null || u.getSenha() == null
					) {
				return ResponseEntity.badRequest().build();
			}
		 
		 Usuario uExistente = ur.findByEmailAndSenha(u.getEmail(), u.getSenha());
		 
		 if( uExistente == null  ) {
		 	return ResponseEntity.badRequest().build();
		 }		 
		 
		 // "limpa" o JSON de retorno - isto é, retorna apenas as informacoes necessarias neste momento...
		 uExistente.setPontos(null);
		 uExistente.setQuizzes(null);
		 uExistente.setMateriais(null);
		 
		 //*
		 return ResponseEntity.ok(uExistente);
	}
	
	@PostMapping("/trocaDeSenha")
	@ResponseBody
	public ResponseEntity<Usuario> trocaDeSenha(@RequestBody Usuario u) {
		// metodo de troca de senha, salvar + u.setUsuarioNovo(false)
		String senhaNova = "";

		// nesse caso, diferentemente do update, o front pede pro 
		// usuario preencher apenas a senha velha e a senha nova, o 
		// resto dos atributos preenchidos o front que envia sem pedir novamente pro usuario
		if (u.getId() == null || u.getNome() == null 
				|| u.getEmail() == null 
				|| u.getSenha() == null
				|| u.getTurma() == null 
				|| senhaNova == null 
						//ver se a senha velha e a senha nova são iguais
				|| u.getSenha().equals(senhaNova)) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Usuario> hasUser = ur.findById(u.getId());
		if (hasUser.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		
			u.setSenha(senhaNova);
			u.setUsuarioNovo(false);
			Usuario uAtualizado = ur.save(u);
			return ResponseEntity.ok(uAtualizado);
	}

}
