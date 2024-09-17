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

import iflearn.entities.Quiz;
import iflearn.repositories.QuizRepository;

//terceiro crud
@Controller
@RequestMapping("/quiz")
public class CrudQuiz {
	
	@Autowired
	private QuizRepository qir;
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Quiz> create(@RequestBody Quiz qi) {
		if(qi.getNome() == null || qi.getDesc() == null 
				|| qi.getFeedback() == null 
				|| qi.getDataCriacao() == null) {
			//	|| qi.getUsuario() == null) {
			return ResponseEntity.badRequest().build();
		}
		
		Quiz qiNovo = qir.save(qi);
		return ResponseEntity.ok(qiNovo);
	}
	
	@GetMapping("/read/{id_quiz}")
	@ResponseBody
	public ResponseEntity<Quiz> read(@PathVariable(name = "id_quiz") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Quiz> hasUser = qir.findById(id);
		if (hasUser.isEmpty())
			return ResponseEntity.badRequest().build();
		
		else
			return ResponseEntity.ok(hasUser.get());
	}

	//como colocar que não pode trocar o usuario??
	@PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Quiz> update(@RequestBody Quiz qi) {        
        if (qi.getId() == null || qi.getNome() == null 
        		|| qi.getDesc() == null 
				|| qi.getFeedback() == null 
				|| qi.getDataCriacao() == null) {
			//	|| m.getUsuario() == null) {
			return ResponseEntity.badRequest().build();
		}

        Optional<Quiz> hasUser = qir.findById(qi.getId());
        if (hasUser.isEmpty())
            return ResponseEntity.badRequest().build();
      
        else {
	        Quiz qiAtualizado = qir.save(qi);
	        return ResponseEntity.ok(qiAtualizado);
    }
	}

	
	@DeleteMapping("/delete/{id_quiz}")
	@ResponseBody
	public ResponseEntity<Quiz> delete(@PathVariable(name = "id_quiz") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
      
        Optional<Quiz> hasUser = qir.findById(id);
        if (hasUser.isEmpty())
            return ResponseEntity.badRequest().build();
        
        else {
        	qir.deleteById(id);
    		return ResponseEntity.noContent().build();
    }
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Quiz>> listarTodos() {
		return ResponseEntity.ok(qir.findAll());
	}
	

}
