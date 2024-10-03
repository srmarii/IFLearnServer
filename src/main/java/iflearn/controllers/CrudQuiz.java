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
import iflearn.entities.Quiz;
import iflearn.entities.Usuario;
import iflearn.repositories.QuizRepository;
import iflearn.repositories.UsuarioRepository;

//terceiro crud
@Controller
@RequestMapping("/quiz")
public class CrudQuiz {
	
	@Autowired
	private QuizRepository qir;
	
	@Autowired
	private UsuarioRepository  ur;
	
	@PostMapping("/create")
	@ResponseBody
<<<<<<< HEAD
	public ResponseEntity<?> create(@RequestBody Quiz qi) {
		if (qi.getNome() == null || qi.getDesc() == null
		// || qi.getData() == null
=======
	public ResponseEntity<Quiz> create(@RequestBody Quiz qi) {
		if(qi.getNome() == null || qi.getDesc() == null
			//	|| qi.getDataCriacao() == null
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
				|| qi.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		
		Quiz qiNovo = qir.save(qi);
		return ResponseEntity.ok(qiNovo);
	}
	
	@GetMapping("/read/{id_quiz}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_quiz") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Quiz> qiExistente = qir.findById(id);
		if (qiExistente.isEmpty())
			return ResponseEntity.badRequest().build();
		
		else {
			Quiz qi = qiExistente.get();
			Usuario u = qi.getUsuario();
			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);
			
			qi.setUsuario(u);
			qi.setPontos(null);
			qi.setQuestoes(null);
			
			return ResponseEntity.ok(qi);
		}
	}

	//como colocar que não pode trocar o usuario??
	@PutMapping("/update")
<<<<<<< HEAD
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Quiz qi) {
		if (qi.getId() == null || qi.getNome() == null || qi.getDesc() == null
		// || qi.getFeedback() == null
		// || qi.getData() == null
=======
    @ResponseBody
    public ResponseEntity<Quiz> update(@RequestBody Quiz qi) {        
        if (qi.getId() == null || qi.getNome() == null 
        		|| qi.getDesc() == null 
			//	|| qi.getFeedback() == null
			//	|| qi.getDataCriacao() == null
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
				|| qi.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}

        Optional<Quiz> qiExistente = qir.findById(qi.getId());
        if (qiExistente.isEmpty())
            return ResponseEntity.badRequest().build();
      
        else {
        	try {
    			Usuario u = ur.findById(qi.getUsuario().getId()).get();
    			
    			//save antes pra não salvar no banco o null, só setar pra retornar pro usuario
    			Quiz qiAtualizado = qir.save(qi);
    			
    			u.setQuizzes(null);
    			u.setMateriais(null);
    			u.setPontos(null);
    			
    			qiAtualizado.setUsuario(u);
    			qiAtualizado.setPontos(null);
    			qiAtualizado.setQuestoes(null);
    			
    			qiAtualizado.setUsuario(u); 
    			
    			return ResponseEntity.ok(qiAtualizado);
    		} catch (Exception e) {
    			return ResponseEntity.badRequest().build(); 
    		}
    }
	}

	
	@DeleteMapping("/delete/{id_quiz}")
	@ResponseBody
<<<<<<< HEAD
	public ResponseEntity<?> delete(@PathVariable(name = "id_quiz") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Quiz> qiExistente = qir.findById(id);
		if (qiExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			qir.deleteById(id);
			return ResponseEntity.ok().body("quiz deletado");
		}
=======
	public ResponseEntity<Quiz> delete(@PathVariable(name = "id_quiz") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
      
        Optional<Quiz> qiExistente = qir.findById(id);
        if (qiExistente.isEmpty())
            return ResponseEntity.badRequest().build();
        
        else {
        	qir.deleteById(id);
    		return ResponseEntity.noContent().build();
    }
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Quiz>> listarTodos() {

		List<Quiz> lista = qir.findAll();

		for (Quiz qi : lista) {

			if (qi.getUsuario() != null) {
				Usuario u = qi.getUsuario();
				u.setQuizzes(null);
				u.setMateriais(null);
				u.setPontos(null);
			}

			qi.setPontos(null);
			qi.setQuestoes(null);
		}

		return ResponseEntity.ok(lista);
	}
	

}
