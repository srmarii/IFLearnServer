package iflearn.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import iflearn.entities.Usuario;
import iflearn.repositories.UsuarioRepository;

@Service
public class EmailService {
	private final JavaMailSender mailSender;
	
	@Autowired
	UsuarioRepository ur;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

//	public void envia(EmailRequest emaildto) {
//		var message = new SimpleMailMessage();
//		message.setFrom("noreply@email.com");
//		message.setTo(emaildto.email());
//		message.setSubject("Email de recuperação de senha");
//		message.setText(emaildto.texto());
//		mailSender.send(message);
//	}

	public void envia(EmailRequest emaildto) {
//		Usuario u = new Usuario();
		Optional<Usuario> uExistente = ur.findById(emaildto.idu());
		if (uExistente.isEmpty()) {
			System.out.println("id n existe no banco");
		} else {
//			u = uExistente.get();
			
			
			var message = new SimpleMailMessage();
			message.setFrom("noreply@email.com");
			message.setTo(uExistente.get().getEmail());
			message.setSubject("Email de recuperação de senha");
			message.setText("Este é o email de recuperação de sua senha no IFLearn, clique no link abaixo para fazer a recuperação:\n\nlink");
		//	message.setText("link");
			mailSender.send(message);
		}
	}

}
