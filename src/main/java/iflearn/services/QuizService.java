package iflearn.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import iflearn.dto.RankingDTO;
import iflearn.entities.Alternativa;
import iflearn.entities.Pontuacao;
import iflearn.entities.Questao;
import iflearn.entities.Quiz;
import iflearn.entities.Usuario;
import iflearn.repositories.AlternativaRepository;
import iflearn.repositories.PontuacaoRepository;
import iflearn.repositories.QuizRepository;
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
	

	public ResponseEntity<?> create(Quiz qi) {
		if (qi.getNome() == null || qi.getDesc() == null
				|| qi.getUsuario() == null) {
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
		else {
			Quiz qi = qiExistente.get();
			Usuario u = qi.getUsuario();
			u.setQuizzes(null);
			u.setMateriais(null);
			u.setPontos(null);
	//		qi.setUsuario(u);
	//		qi.setPontos(null);
	//		qi.setQuestoes(null);
			qi.setUsuario(u);
		
			return ResponseEntity.ok(qi);
		}
	}
	
	
	public ResponseEntity<?> update(Quiz qi) {
		if (qi.getId() == null || qi.getNome() == null || qi.getDesc() == null
				|| qi.getUsuario() == null) {
			return ResponseEntity.badRequest().body("um dos parâmetros está nulo");
		}
		Optional<Quiz> qiExistente = qir.findById(qi.getId());
		if (qiExistente.isEmpty())
			return ResponseEntity.notFound().build();
		else {
			try {
				Usuario u = ur.findById(qi.getUsuario().getId()).get();
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

	
	public ResponseEntity<?> delete(Integer id) {
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
	}
	
	
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

	//////////////////////////////////////////////////////

	//calcula pontos de um usuario para o quiz selecionado
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
		Pontuacao pNova = pr.save(p);

		return ResponseEntity.ok(pNova);
	}

	
	//calcula a pontuação total de um usuario (soma todos pontos de todos quizzes realizados)
	public ResponseEntity<?> calculaPontosTotalPorU(Integer id) {
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
