package iflearn.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iflearn.dto.RankingDTO;
import iflearn.entities.Alternativa;
import iflearn.entities.Pontuacao;
import iflearn.entities.Questao;
import iflearn.entities.Quiz;
import iflearn.entities.Usuario;
import iflearn.repositories.AlternativaRepository;
import iflearn.repositories.PontuacaoRepository;
import iflearn.repositories.QuestaoRepository;
import iflearn.repositories.QuizRepository;
import iflearn.repositories.UsuarioRepository;

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

	@Autowired
	UsuarioRepository ur;

	//calcula pontos de um usuario para o quiz selecionado
	@PostMapping("/calculaPontosQi")
	@ResponseBody
	public ResponseEntity<?> calculaPontosQi(@RequestBody Quiz qi) {
		if (qi.getId() == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}

		Integer contador = 0;
		for (Questao qu : qi.getQuestoes()) {
			Alternativa selecionadaU = qu.getAlternativas().get(0);
			Alternativa aBanco = ar.findById(selecionadaU.getId()).get();

			if (aBanco.getCorreta())
				contador++;
		}

		Pontuacao p = new Pontuacao();
		p.setQtdPontos(contador);
		p.setQuiz(qi);
		Pontuacao pNova = pr.save(p);

		return ResponseEntity.ok(pNova);
	}

	//calcula a pontuação total de um usuario (soma todos pontos de todos quizzes realizados)
	@PostMapping("/calculaPontosU/{id_usuario}")
	@ResponseBody
	public ResponseEntity<?> calculaPontosTotalPorU(@PathVariable(name = "id_usuario") Integer id) {
		if (id == null)
			return ResponseEntity.badRequest().body("o id está nulo");

		Optional<Usuario> uExistente = ur.findById(id);
		if (uExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		int soma = 0;
		for (Pontuacao n : uExistente.get().getPontos()) {
			soma += n.getQtdPontos();
		}

		RankingDTO retorno = new RankingDTO(uExistente.get().getId(), uExistente.get().getNome(), soma);

		return ResponseEntity.ok(retorno);
	}

	//ordena a soma total de pontos de cada usuario em ordem descrecente 
	//e retorna id, nome e a soma total de cada usuario
	@GetMapping("/ranking")
	@ResponseBody
	public ResponseEntity<?> ranking() {

		List<RankingDTO> somasDeCadaU = new ArrayList<>();

		List<Usuario> lista = ur.findAll();
		for (Usuario u : lista) {
			u.setMateriais(null);
			u.setQuizzes(null);

			int soma = 0;
			for (Pontuacao n : u.getPontos()) {
				soma += n.getQtdPontos();
			}

			somasDeCadaU.add(new RankingDTO(u.getId(), u.getNome(), soma));
		}

		//ordena os valores (em ordem crescente)
		Collections.sort(somasDeCadaU, new Comparator<RankingDTO>() {
			public int compare(RankingDTO r1, RankingDTO r2) {
				return r1.soma().compareTo(r2.soma());
			}
		});

		//reversiona os valores (coloca em ordem decrescente)
		Collections.reverse(somasDeCadaU);
		return ResponseEntity.ok(somasDeCadaU);
	}

}
