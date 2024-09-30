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
import iflearn.entities.Quiz;
import iflearn.repositories.QuestaoRepository;
import iflearn.repositories.QuizRepository;

//quarto crud
@Controller
@RequestMapping("/questao")
public class CrudQuestao {
	
	@Autowired
	private QuestaoRepository qur;
	
	@Autowired
	private QuizRepository qir;
	
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Questao> create(@RequestBody Questao qu) {
		if(qu.getDesc() == null || qu.getQuiz() == null
				
				//como criar uma lista em json?
				//perguntar: o mysql não cria coluna pra um array??
				|| qu.getAlternativas() == null) {
	
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

		Optional<Questao> quExistente = qur.findById(id);
		if (quExistente.isEmpty())
			return ResponseEntity.badRequest().build();
		
		else {
			Questao qu = quExistente.get();
			Quiz qi = qu.getQuiz();
			qi.setUsuario(null);
			qi.setQuestoes(null);
			qi.setPontos(null);
			
			qu.setQuiz(qi);
			qu.setAlternativas(null);
			
			return ResponseEntity.ok(qu);
		}
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

        Optional<Questao> quExistente = qur.findById(qu.getId());
        if (quExistente.isEmpty())
            return ResponseEntity.badRequest().build();
      
        else {
        	try {
    			Quiz qi = qir.findById(qu.getQuiz().getId()).get();
    		
    			Questao quAtualizado = qur.save(qu);
    		    			
    			qi.setUsuario(null);
    			qi.setQuestoes(null);
    			qi.setPontos(null);
    			
    			quAtualizado.setQuiz(qi);
    			quAtualizado.setAlternativas(null);
    			    			    			
    			quAtualizado.setQuiz(qi); 
    			
    			return ResponseEntity.ok(quAtualizado);
    		} catch (Exception e) {
    			return ResponseEntity.badRequest().build(); 
    		}
    }
	}

	
	@DeleteMapping("/delete/{id_quiz}")
	@ResponseBody
	public ResponseEntity<Questao> delete(@PathVariable(name = "id_quiz") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
      
        Optional<Questao> quExistente = qur.findById(id);
        if (quExistente.isEmpty())
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
