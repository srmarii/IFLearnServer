package iflearn.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iflearn.entities.Alternativa;
import iflearn.entities.Questao;
import iflearn.entities.Quiz;
import iflearn.repositories.AlternativaRepository;
import iflearn.repositories.PontuacaoRepository;
import iflearn.repositories.QuestaoRepository;
import iflearn.repositories.QuizRepository;

@Controller
@RequestMapping("/jogo")
public class QuizController {

	@Autowired
	QuizRepository qir;

	@Autowired
	QuestaoRepository qur;

	@Autowired
	AlternativaRepository ar;

	@Autowired
	PontuacaoRepository pr;


	// adicionar pra quiz um atributo boolean realizado para verificar se já foi
	// feito ou não

	
	@PostMapping("/jogoTeste")
	@ResponseBody
	public ResponseEntity<?> jogoteste(@RequestBody Quiz qi) {
		if (qi.getId() == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		int contador = 0;
		
		for(Questao qu : qi.getQuestoes()) {
			
			Alternativa selecionadaU = qu.getAlternativas().get(0);

			Alternativa aBanco = ar.findById(selecionadaU.getId()).get();
			
			if(aBanco.getCorreta())
				contador++;
		}
		
		
//		Pontuacao p = null;
//		p.setQtdPontos(contador);
//		Pontuacao pNova = pr.save(p);
		
		
		
		return ResponseEntity.ok(contador);
		
	}

	
	
//	@GetMapping("/jogo/{id_quiz}")
//	@ResponseBody
//	public ResponseEntity<?> jogo(@PathVariable(name = "id_quiz") Integer id) {
//		if (id == null) {
//			return ResponseEntity.badRequest().body("o id está nulo");
//		}
//
//		Optional<Quiz> qiExistente = qir.findById(id);
//		if (qiExistente.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}
//
//		else {
//
//			List<Questao> listaQu = qur.findAll();
//			for (Questao qu : listaQu) {
//
//				List<Alternativa> listaA = ar.findAll();
//				for (Alternativa a : listaA) {
//
//					int contador = 0;
//					if (a.getCorreta() == true) {
//						for (int c = 0; c < listaA.size(); c++) {
//							contador++;
//						}
//
//						Optional<Pontuacao> pExistente = pr.findById(id);
//						if (pExistente.isEmpty()) {
//							return ResponseEntity.notFound().build();
//						} else {
//							Pontuacao p = pExistente.get();
//							p.setQtdPontos(contador);
//
//							// ?
//							// qi.setPontos(contador);
//							return ResponseEntity.ok(p.getQtdPontos());
//						}
//
//					}
//
//				}
//
//			}
//
//			return ResponseEntity.badRequest().body("nada funfou");
//		}
//	}

	// pro ranking primeiramente pegar todos os termos da lista pontos de um usuario
	// achado por id e somar
	// depois armazenar num vetor essa soma dos pontos de cada usuario
	// depois pegar todos termos desse vetor e organizar em asc (crescente)
	// retornar em ordem tanto o id do usuario quanto a soma de pontos

}
