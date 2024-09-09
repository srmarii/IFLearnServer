package iflearn.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import iflearn.entities.Alternativa;
import iflearn.entities.Material;
import iflearn.entities.Pontuacao;
import iflearn.entities.Questao;
import iflearn.entities.Quiz;
import iflearn.entities.Usuario;
import iflearn.repositories.AlternativaRepository;
import iflearn.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;

@Component
public class Testes {
	
	@Autowired
	private AlternativaRepository repo;

//	@PostConstruct
//	public void run() {
//	
//		Alternativa a = new Alternativa();
//		Material m = new Material();
//		Pontuacao p = new Pontuacao();
//		Questao q = new Questao();
//		Quiz qu = new Quiz();
//		Usuario u = new Usuario();
//		
//		repo.save(a);
//
//	}

}
