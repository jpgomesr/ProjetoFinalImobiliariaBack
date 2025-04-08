package com.hav.imobiliaria;

import com.hav.imobiliaria.model.entity.EmailRequest;
import com.hav.imobiliaria.service.EmailService;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.Map;

@EnableJpaAuditing
@SpringBootApplication
@AllArgsConstructor
@EnableAsync
public class Application {
	public static void main(String[] args) {
		// Carregar variáveis do .env antes de iniciar a aplicação
		Dotenv dotenv = Dotenv.load();
		
		// Configurar variáveis de ambiente do sistema
		System.setProperty("S3_ACCESS_KEY", dotenv.get("S3_ACCESS_KEY"));
		System.setProperty("S3_SECRET_KEY", dotenv.get("S3_SECRET_KEY"));
		System.setProperty("S3_REGION", dotenv.get("S3_REGION"));
		System.setProperty("S3_BUCKET_NAME", dotenv.get("S3_BUCKET_NAME"));
		System.setProperty("EMAIL_APP_PASSWORD", dotenv.get("EMAIL_APP_PASSWORD"));
		System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
		
		SpringApplication.run(Application.class, args);
	}

//	public void sendEmail() throws MessagingException { // Modelo de envio de email
//		EmailRequest emailRequest = new EmailRequest();
//		emailRequest.setTipoEmail("resposta-pergunta");
//		emailRequest.setDestinatario("joao_pg_rodrigues@estudante.sesisenai.org.br");
//		Map<String, Object> variaveis = new HashMap<>();
//		variaveis.put("nomeCliente", "Alex Zastrow");
//		variaveis.put("pergunta", "Como está indo a criação de envio de email?");
//		variaveis.put("resposta", "Finalizamos a criação de envio de email.");
//		emailRequest.setVariaveis(variaveis);
//		emailService.enviarEmail(emailRequest);
//	}
}
