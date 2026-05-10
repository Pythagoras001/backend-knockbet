package com.knockbet.backend_knockbet.Services;


import com.knockbet.backend_knockbet.Events.NuevaUserApuesta;
import com.knockbet.backend_knockbet.Models.EstrucApuesta.UserApuesta;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @EventListener
    public void enviarInfoApuestaMail(NuevaUserApuesta event) throws MessagingException {
        Context context = new Context();
        context.setVariable("apuesta", event.userApuesta());

        String html = templateEngine.process("DetallesApuesta", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(event.userApuesta().getApostador().correo());
        helper.setSubject("Detalles de su Apuesta KnockBet");
        helper.setText(html, true);

        mailSender.send(message);
    }

}
