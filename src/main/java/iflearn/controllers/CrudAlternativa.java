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

import iflearn.entities.Alternativa;
import iflearn.entities.Questao;
import iflearn.entities.Quiz;
import iflearn.repositories.AlternativaRepository;
import iflearn.repositories.QuestaoRepository;

//quinto crud
@Controller
@RequestMapping("/alternativa")
public class CrudAlternativa {
	
	@Autowired
	private AlternativaRepository ar;
	
	@Autowired QuestaoRepository qur;
	
	@PostMapping("/create")
	@ResponseBody
<<<<<<< HEAD
	public ResponseEntity<?> create(@RequestBody Alternativa a) {
		if (a.getDesc() == null || a.getCorreta() == null || a.getQuestao() == null) {

			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
=======
	public ResponseEntity<Alternativa> create(@RequestBody Alternativa a) {
		if(a.getDesc() == null || a.getCorreta() == null
				|| a.getQuestao() == null) {
	
			return ResponseEntity.badRequest().build();
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
		}
		
		Alternativa aNova = ar.save(a);
		return ResponseEntity.ok(aNova);
	}
<<<<<<< HEAD

	@GetMapping("/read/{id_alternativa}")
=======
	
	@GetMapping("/read/{id_quiz}")
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_alternativa") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Alternativa> aExistente = ar.findById(id);
		if (aExistente.isEmpty())
			return ResponseEntity.badRequest().build();
		
		else {
			Alternativa a = aExistente.get();
			Questao qu = a.getQuestao();
			qu.setAlternativas(null);
			qu.setQuiz(null);
			
			a.setQuestao(qu);
			a.setRespostas(null);
			
			return ResponseEntity.ok(a);
		}
	}

	//como colocar que não pode trocar o usuario??
	@PutMapping("/update")
<<<<<<< HEAD
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Alternativa a) {
		if (a.getId() == null || a.getDesc() == null || a.getCorreta() == null || a.getQuestao() == null) {

			return ResponseEntity.badRequest().body("um dos parametros está nulo");
=======
    @ResponseBody
    public ResponseEntity<Alternativa> update(@RequestBody Alternativa a) {        
		if(a.getId() == null || a.getDesc() == null 
				|| a.getCorreta() == null
				|| a.getQuestao() == null) {
	
			return ResponseEntity.badRequest().build();
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
		}

        Optional<Alternativa> aExistente = ar.findById(a.getId());
        if (aExistente.isEmpty())
            return ResponseEntity.badRequest().build();
      
        else {
        	try {
    			Questao qu = qur.findById(a.getQuestao().getId()).get();
    			
    			//save antes pra não salvar no banco o null, só setar pra retornar pro usuario
    			Alternativa aAtualizada = ar.save(a);
    			
    			qu.setAlternativas(null);
    			qu.setQuiz(null);
    			
    			aAtualizada.setQuestao(qu);
    			aAtualizada.setRespostas(null);
    			
    			aAtualizada.setQuestao(qu); 
    			
    			return ResponseEntity.ok(aAtualizada);
    		} catch (Exception e) {
    			return ResponseEntity.badRequest().build(); 
    		}
    }
	}

<<<<<<< HEAD
	@DeleteMapping("/delete/{id_alternativa}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable(name = "id_alternativa") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Alternativa> aExistente = ar.findById(id);
		if (aExistente.isEmpty())
			return ResponseEntity.notFound().build();

		else {
			ar.deleteById(id);
			return ResponseEntity.ok().body("alternativa deletada");
		}
=======
	
	@DeleteMapping("/delete/{id_quiz}")
	@ResponseBody
	public ResponseEntity<Alternativa> delete(@PathVariable(name = "id_quiz") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
      
        Optional<Alternativa> aExistente = ar.findById(id);
        if (aExistente.isEmpty())
            return ResponseEntity.badRequest().build();
        
        else {
        	ar.deleteById(id);
    		return ResponseEntity.noContent().build();
    }
>>>>>>> 225dc8ff38eacfa8b0b0a1df883809e35116e23b
	}

	@GetMapping("/listarTodos")
	@ResponseBody
	public ResponseEntity<List<Alternativa>> listarTodos() {
		List<Alternativa> lista = ar.findAll();

		for (Alternativa a : lista) {

			if (a.getQuestao() != null) {
				Questao qu = a.getQuestao();
				qu.setAlternativas(null);
				qu.setQuiz(null);
			}

			a.setQuestao(null);
			a.setRespostas(null);

		}

		return ResponseEntity.ok(lista);
	}

}
