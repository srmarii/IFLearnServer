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

import iflearn.entities.Material;
import iflearn.entities.Usuario;
import iflearn.repositories.MaterialRepository;
import iflearn.repositories.UsuarioRepository;

//segundo crud
@Controller
@RequestMapping("/material")
public class CrudMaterial {

	@Autowired
	private MaterialRepository mr;
	
	@Autowired
	private UsuarioRepository  ur;
	
	@PostMapping("/create")
	@ResponseBody
<<<<<<< HEAD
	public ResponseEntity<?> create(@RequestBody Material m) {
		if (m.getNome() == null || m.getTurma() == null || m.getData() == null || m.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
=======
	public ResponseEntity<Material> create(@RequestBody Material m) {
		if(m.getNome() == null || m.getTurma() == null 
				|| m.getData() == null
				|| m.getUsuario() == null) {
			return ResponseEntity.badRequest().build();
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
		}
		
		//try catch pra caso o id não exista
//		try {
			//Usuario u = ur.findById(m.getUsuario().getId()).get();
			
			Material mNovo = mr.save(m);
			
//			// nao monta as listas para devolver para o front..
//			u.setQuizzes(null);
//			u.setMateriais(null);
//			u.setPontos(null);
//			
//			// atualiza o objeto material com os dados do usuario para retornar ao front
//			mNovo.setUsuario(u); 
			
			return ResponseEntity.ok(mNovo);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().build(); 
//		}
	}
	
	@GetMapping("/read/{id_material}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_material") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Material> mExistente = mr.findById(id);
		
		if (mExistente.isEmpty()){
			return ResponseEntity.badRequest().build();
		
		}else {
			Material m = mExistente.get();
<<<<<<< HEAD

			if (m.getUsuario() != null) {
				Usuario u = m.getUsuario();
				u.setQuizzes(null);
				u.setMateriais(null);
				u.setPontos(null);

				m.setUsuario(u);
			}
=======
			Usuario u = m.getUsuario();
			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);
			
			m.setUsuario(u);
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
			return ResponseEntity.ok(m);
		}
	}
	
	//trocar o usuario não fica disponível pra edição no front!
	@PutMapping("/update")
<<<<<<< HEAD
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Material m) {
		if (m.getId() == null || m.getNome() == null || m.getTurma() == null || m.getData() == null
=======
    @ResponseBody
    public ResponseEntity<Material> update(@RequestBody Material m) {        
        if (m.getId() == null || m.getNome() == null 
				|| m.getTurma() == null
				|| m.getData() == null
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
				|| m.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}

        Optional<Material> mExistente = mr.findById(m.getId());
        if (mExistente.isEmpty())
            return ResponseEntity.badRequest().build();
      
        else {
        	try {
    			Usuario u = ur.findById(m.getUsuario().getId()).get();
    			
    			Material mAtualizado = mr.save(m);
    			
    			// nao monta as listas para devolver para o front..
    			u.setQuizzes(null);
    			u.setMateriais(null);
    			u.setPontos(null);
    			
    			// atualiza o objeto material com os dados do usuario para retornar ao front, não salva as listas como null
    			mAtualizado.setUsuario(u); 
    			
    			return ResponseEntity.ok(mAtualizado);
    		} catch (Exception e) {
    			return ResponseEntity.badRequest().build(); 
    		}
    }
	}

	
	@DeleteMapping("/delete/{id_material}")
	@ResponseBody
<<<<<<< HEAD
	public ResponseEntity<?> delete(@PathVariable(name = "id_material") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Material> mExistente = mr.findById(id);
		if (mExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			mr.deleteById(id);
			return ResponseEntity.ok().body("material deletado");
		}
=======
	public ResponseEntity<Material> delete(@PathVariable(name = "id_material") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
      
        Optional<Material> mExistente = mr.findById(id);
        if (mExistente.isEmpty())
            return ResponseEntity.badRequest().build();
        
        else {
        	mr.deleteById(id);
    		return ResponseEntity.noContent().build();
    }
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Material>> listarTodos() {
<<<<<<< HEAD

		List<Material> lista = mr.findAll();

		for (Material m : lista) {

			if (m.getUsuario() != null) {
				Usuario u = m.getUsuario();
				u.setQuizzes(null);
				u.setMateriais(null);
				u.setPontos(null);
			}
		}

		return ResponseEntity.ok(lista);
=======
		return ResponseEntity.ok(mr.findAll());
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	}
	
}
