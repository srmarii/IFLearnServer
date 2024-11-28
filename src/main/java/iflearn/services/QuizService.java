package iflearn.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import iflearn.dto.RankingResponse;
import iflearn.dto.PontuacaoResponse;
import iflearn.dto.QuizResponse;
import iflearn.entities.Alternativa;
import iflearn.entities.Pontuacao;
import iflearn.entities.Questao;
import iflearn.entities.Quiz;
import iflearn.entities.Registro;
import iflearn.entities.Usuario;
import iflearn.repositories.AlternativaRepository;
import iflearn.repositories.PontuacaoRepository;
import iflearn.repositories.QuizRepository;
import iflearn.repositories.RegistroRepository;
import iflearn.repositories.UsuarioRepository;

@Service
public class QuizService {

	@Autowired
	private QuizRepository qir;
	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private AlternativaRepository ar;
	@Autowired
	private PontuacaoRepository pr;
	@Autowired
	private RegistroRepository rr;

	public ResponseEntity<?> create(Quiz qi) {
		if (qi.getNome() == null || qi.getDesc() == null || qi.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Quiz qiNovo = qir.save(qi);
		return ResponseEntity.ok(qiNovo);
	}

	public ResponseEntity<?> read(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Quiz> qiExistente = qir.findById(id);
		if (qiExistente.isEmpty())
			return ResponseEntity.notFound().build();

		Quiz qi = qiExistente.get();
		return ResponseEntity.ok(new QuizResponse(qi));
	}

	public ResponseEntity<?> update(Quiz qi) {
		if (qi.getId() == null || qi.getNome() == null || qi.getDesc() == null || qi.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Optional<Quiz> qiExistente = qir.findById(qi.getId());
		if (qiExistente.isEmpty())
			return ResponseEntity.notFound().build();

		try {
//				Usuario u = ur.findById(qi.getUsuario().getId()).get();
			Quiz qiAtualizado = qir.save(qi);
			return ResponseEntity.ok(new QuizResponse(qiAtualizado));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	public ResponseEntity<?> delete(Integer id) {
		if (id == null) {
			return ResponseEntity.badRequest().body("o id está nulo");
		}
		Optional<Quiz> qiExistente = qir.findById(id);
		if (qiExistente.isEmpty())
			return ResponseEntity.notFound().build();

		qir.deleteById(id);
		return ResponseEntity.ok().body("quiz deletado");

	}

	public ResponseEntity<List<QuizResponse>> listarTodos() {
		List<Quiz> lista = qir.findAll();
		// . strem() divide a lista para cada objeto
		return ResponseEntity.ok(lista.stream()
				// .map() entra em cada objeto que foi separado e r criando um QuizResponse para
				// cada um (pq é nessa estrutura jgui não sabe
				.map(QuizResponse::new)
				// .collect() transforma de volta em uma lista
				.collect(Collectors.toList()));
	}

	//////////////////////////////////////////////////////

	// calcula pontos de um usuario para o quiz selecionado
	public ResponseEntity<?> calculaPontosQi(Quiz qi) {
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
		p.setUsuario(qi.getUsuario());
		System.out.println(p.getUsuario());
		Pontuacao pNova = pr.save(p);

		Registro r = new Registro(qi, qi.getUsuario());
		rr.save(r);

		return ResponseEntity.ok(new PontuacaoResponse(pNova));
	}

	// calcula a pontuação total de um usuario (soma todos pontos de todos quizzes
	// realizados)
	public ResponseEntity<?> calculaPontosU(Integer id) {
		if (id == null)
			return ResponseEntity.badRequest().body("o id está nulo");
		Optional<Usuario> uExistente = ur.findById(id);
		if (uExistente.isEmpty())
			return ResponseEntity.notFound().build();

		int soma = 0;
		for (Pontuacao n : uExistente.get().getPontos()) {
			soma += n.getQtdPontos();
		}

		Usuario u = uExistente.get();
		u.setSomaPontos(soma);
		ur.save(u);

		return ResponseEntity.ok(new RankingResponse(u));
	}

//	public ResponseEntity<?> ranking() {
//		List<RankingResponse> somasDeCadaU = new ArrayList<>();
//		List<Usuario> lista = ur.findAll();
////		for (Usuario u : lista) {
////			int soma = 0;
////			for (Pontuacao p : u.getPontos()) {
////				soma += p.getQtdPontos();
////			}
//			Usuario u = new Usuario();
////			somasDeCadaU.add(new RankingResponse(u.getId(), u.getNome(), u.getCategoria(), soma));
//			somasDeCadaU.add(new RankingResponse(u.getId(), u.getNome(), u.getCategoria(), u.getSomaPontos()));
////		}
//		Collections.sort(somasDeCadaU, new Comparator<RankingResponse>() {
//			public int compare(RankingResponse r1, RankingResponse r2) {
//				return r1.soma().compareTo(r2.soma());
//			}
//		});
//		Collections.reverse(somasDeCadaU);
//
//		return ResponseEntity.ok(somasDeCadaU);
//	}

	// ordena a soma total de pontos de cada usuario em ordem descrecente
	// e retorna id, nome e a soma total de cada usuario
	public ResponseEntity<?> ranking() {
		List<RankingResponse> somasDeCadaU = new ArrayList<>();
		List<Usuario> lista = ur.findAll();
		for (Usuario u : lista) {
			somasDeCadaU.add(new RankingResponse(u.getId(), u.getNome(), u.getCategoria(), u.getSomaPontos()));
		}

		// ordena os valores (em ordem crescente)
		Collections.sort(somasDeCadaU, new Comparator<RankingResponse>() {
			public int compare(RankingResponse r1, RankingResponse r2) {
				return r1.soma().compareTo(r2.soma());
			}
		});

		// reversiona os valores (coloca em ordem decrescente)
		Collections.reverse(somasDeCadaU);

		return ResponseEntity.ok(somasDeCadaU);
	}

	public boolean realizouQuiz(Integer idqi, Integer idu) {
		if (idqi == null || idu == null)
			return false;

		Quiz q = new Quiz();
		q.setId(idqi);
		Usuario u = new Usuario();
		u.setId(idu);

		Optional<Registro> rExistente = rr.findByQuizAndUsuario(q, u);
		if (rExistente.isEmpty())
			return false;

		return rExistente.isPresent();
	}

}
