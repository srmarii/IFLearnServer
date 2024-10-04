package iflearn.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iflearn.entities.Alternativa;
import iflearn.entities.Pontuacao;
import iflearn.entities.Questao;
import iflearn.entities.Quiz;
import iflearn.entities.Usuario;
import iflearn.repositories.AlternativaRepository;
import iflearn.repositories.PontuacaoRepository;
import iflearn.repositories.QuestaoRepository;
import iflearn.repositories.QuizRepository;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	QuizRepository qir;

	@Autowired
	QuestaoRepository qur;

	@Autowired
	AlternativaRepository ar;

	@Autowired
	PontuacaoRepository pr;

	@GetMapping("/jogo/{id_quiz}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable(name = "id_quiz") Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Optional<Quiz> qiExistente = qir.findById(id);
		if (qiExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		else {

			List<Quiz> listaQi = qir.findAll();
			for (Quiz qi : listaQi) {

				List<Questao> listaQu = qur.findAll();
				for (Questao qu : listaQu) {

					List<Alternativa> listaA = ar.findAll();
					for (Alternativa a : listaA) {

						int contador = 0;
						if (a.getCorreta() == true) {
							for (int c = 0; c < listaA.size(); c++) {
								contador = c;
							}

							Optional<Pontuacao> pExistente = pr.findById(id);
							if (pExistente.isEmpty()) {
								return ResponseEntity.notFound().build();
							} else {
								Pontuacao p = pExistente.get();
								p.setQtdPontos(contador);
							}
						
						}

					}

				}

			}

			return ResponseEntity.ok(null);
		}
	}

}
