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
				|| u.getCategoria() == null
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

}
