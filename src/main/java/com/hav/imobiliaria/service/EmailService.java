package com.hav.imobiliaria.service;

import com.hav.imobiliaria.model.entity.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    public void enviarEmail(EmailRequest request) throws MessagingException {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);

        helper.setTo(request.getDestinatario());
        helper.setSubject(definirAssunto(request.getTipoEmail()));
        helper.setText(processarTemplate(request.getTipoEmail(), request.getVariaveis()), true);

        mailSender.send(mensagem);
    }

    private String processarTemplate(String tipoEmail, Map<String, Object> variaveis) {
        Context context = new Context();
        context.setVariables(variaveis);

        String nomeTemplate = "email/" + tipoEmail;  // Exemplo: "email/resposta-pergunta"
        return templateEngine.process(nomeTemplate, context);
    }

    private String definirAssunto(String tipoEmail) {
        return switch (tipoEmail) {
            case "resposta-pergunta" -> "Resposta à sua pergunta!";
            case "atualizacao-imovel" -> "Seu imóvel favorito foi atualizado!";
            default -> "Notificação da Imobiliária";
        };
    }
}
