package com.hav.imobiliaria.service;

import com.hav.imobiliaria.model.entity.CodigoAuthDoisFatores;
import com.hav.imobiliaria.model.entity.EmailRequest;
import com.hav.imobiliaria.model.enums.TipoEmailEnum;
import com.hav.imobiliaria.repository.CodigoAuthDoisFatoresRepository;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AuthDoisFatoresService {

    private final CodigoAuthDoisFatoresRepository repository;
    private final EmailService emailService;

    public void gerarESalvarCodigoDoisFatores(String email){
        String code = String.valueOf(new Random().nextInt(900000) + 100000);

        CodigoAuthDoisFatores
                .builder()
                .codigo(code)
                .email(email)
                .expiracao(LocalDateTime.now().plusMinutes(10))
                .build();

        repository.save(CodigoAuthDoisFatores.builder().codigo(code).build());


        Map<String,Object> variaveis = new HashMap<>();
        variaveis.put("nomeCliente", "");
        variaveis.put("titulo", "Recuperacao de senha");
        variaveis.put("mensagem", "Clique no botão abaixo para redefinir a sua senha");


        emailService.enviarEmail(
                EmailRequest.
                        builder()
                        .destinatario(email)
                        .tipoEmail(TipoEmailEnum.NOTIFICACAO_IMOBILIARIA)
                        .variaveis(variaveis)
                        .build()
        );



    }

    public Boolean validarCodigo(String email, String codigo){

        return repository.findByCodigoAndEmail(codigo, email).stream().anyMatch(codigoAuthDoisFatores -> codigoAuthDoisFatores.getExpiracao().isAfter(LocalDateTime.now()));

    }


}
