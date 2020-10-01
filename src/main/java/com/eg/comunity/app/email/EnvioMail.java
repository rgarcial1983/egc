package com.eg.comunity.app.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.eg.comunity.app.models.entity.Usuario;

@Service
public class EnvioMail{
	
    @Autowired
    private JavaMailSender javaMailSender;
    
    private final TemplateEngine templateEngine;
    
    public EnvioMail(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }
    
    @Value("${footer.contactanos.email}")
    private String remitente;
   
    public void enviarEmailNuevoUsuario(Usuario user, String password) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password);

        String process = templateEngine.process("emails/email-welcome", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        
        try {
			helper.setSubject("Bienvenid@ a EGC " + user.getUsername());
			helper.setText(process, true);
			//helper.setFrom(remitente);
			helper.setFrom("rafalillo@hotmail.com");
			helper.setTo(user.getEmail());
			javaMailSender.send(mimeMessage);
		} catch (MailException e) {
			e.printStackTrace();
			System.out.println("HA OCURRIDO UN ERROR AL ENVIAR UN EMAIL A " + user.getEmail());
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("HA OCURRIDO UN ERROR AL ENVIAR UN EMAIL A " + user.getEmail());
		}
    }

}
