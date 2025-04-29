package com.hav.imobiliaria;

import com.hav.imobiliaria.model.entity.EmailRequest;
import com.hav.imobiliaria.model.enums.TipoEmailEnum;
import com.hav.imobiliaria.service.EmailService;
import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
@OpenAPIDefinition(
		info = @Info(
				title = "Projeto final hav",
				description = "Documentacao da api hav",
				version = "1.0"
		)
)
public class Application {
//	EmailService emailService;

	public static void main(String[] args) {


		SpringApplication.run(Application.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void sendEmail() throws MessagingException { // Modelo de envio de email
//		EmailRequest emailRequest = new EmailRequest();
//		emailRequest.setTipoEmail(TipoEmailEnum.RESPOSTA_PERGUNTA);
//		emailRequest.setDestinatario("jpgomesr.dev@gmail.com");
//		Map<String, Object> variaveis = new HashMap<>();
//		variaveis.put("nomeCliente", "Alex Zastrow");
//		variaveis.put("pergunta", "Como está indo a criação de envio de email?");
//		variaveis.put("resposta", "Finalizamos a criação de envio de email.");
//		emailRequest.setVariaveis(variaveis);
//		emailService.enviarEmail(emailRequest);
//	}
}
