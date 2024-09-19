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

import iflearn.entities.Questao;
import iflearn.repositories.QuestaoRepository;


//quarto crud
@Controller
@RequestMapping("/questao")
public class CrudQuestao {
	
	@Autowired
	private QuestaoRepository qur;
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Questao> create(@RequestBody Questao qu) {
		if(qu.getDesc() == null) {// || qu.getQuiz() == null
			//	|| qu.getAlternativas() == null) {
	
			return ResponseEntity.badRequest().build();
		}
		
		Questao quNova = qur.save(qu);
		return ResponseEntity.ok(quNova);
	}
	
	@GetMapping("/read/{id_quiz}")
	@ResponseBody
	public ResponseEntity<Questao> read(@PathVariable(name = "id_quiz") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Questao> hasUser = qur.findById(id);
		if (hasUser.isEmpty())
			return ResponseEntity.badRequest().build();
		
		else
			return ResponseEntity.ok(hasUser.get());
	}

	//como colocar que não pode trocar o usuario??
	@PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Questao> update(@RequestBody Questao qu) {        
        if (qu.getId() == null || qu.getDesc() == null 
        		|| qu.getQuiz() == null
				|| qu.getAlternativas() == null) {
			return ResponseEntity.badRequest().build();
		}

        Optional<Questao> hasUser = qur.findById(qu.getId());
        if (hasUser.isEmpty())
            return ResponseEntity.badRequest().build();
      
        else {
        	Questao quAtualizada = qur.save(qu);
	        return ResponseEntity.ok(quAtualizada);
    }
	}

	
	@DeleteMapping("/delete/{id_quiz}")
	@ResponseBody
	public ResponseEntity<Questao> delete(@PathVariable(name = "id_quiz") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
      
        Optional<Questao> hasUser = qur.findById(id);
        if (hasUser.isEmpty())
            return ResponseEntity.badRequest().build();
        
        else {
        	qur.deleteById(id);
    		return ResponseEntity.noContent().build();
    }
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Questao>> listarTodos() {
		return ResponseEntity.ok(qur.findAll());
	}

}
