package iflearn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import iflearn.repositories.AlternativaRepository;
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

	
	
	
}
