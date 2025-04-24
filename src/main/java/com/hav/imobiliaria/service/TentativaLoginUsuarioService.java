package com.hav.imobiliaria.service;

import com.hav.imobiliaria.model.entity.EmailRequest;
import com.hav.imobiliaria.model.entity.TentativaLoginUsuario;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.enums.TipoEmailEnum;
import com.hav.imobiliaria.repository.TentativaLoginUsuarioRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class TentativaLoginUsuarioService {

    private final TentativaLoginUsuarioRepository repository;
    private final UsuarioService usuarioService;
    private final EmailService emailService;

    @Transactional
    public void verificarTentativaUsuario(String email){
            TentativaLoginUsuario tentativaLoginUsuario;

        try{
             tentativaLoginUsuario = repository.findById(email).orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException e ){
             tentativaLoginUsuario = new TentativaLoginUsuario(email, 0, LocalDateTime.now().minusMonths(1));
             tentativaLoginUsuario = repository.save(tentativaLoginUsuario);

        }

        if(tentativaLoginUsuario.getUltimaTentativa().isAfter(LocalDateTime.now().minusMinutes(5))){
            tentativaLoginUsuario.setTentativas(tentativaLoginUsuario.getTentativas() + 1);
            tentativaLoginUsuario.setUltimaTentativa(LocalDateTime.now());
        }
        else {
            tentativaLoginUsuario.setTentativas(1);
            tentativaLoginUsuario.setUltimaTentativa(LocalDateTime.now());
        }
        bloquearUsuarioCasoTentativasMaiorIgualCinco(tentativaLoginUsuario);
    }
    @Transactional
    protected void bloquearUsuarioCasoTentativasMaiorIgualCinco(TentativaLoginUsuario tentativaLoginUsuario){
        try{
            if(tentativaLoginUsuario.getTentativas() >= 5){
                tentativaLoginUsuario.setTentativas(0);
                Usuario usuario = usuarioService.buscarPorEmail(tentativaLoginUsuario.getEmail());
                usuario.setAtivo(false);

                EmailRequest emailRequest = new EmailRequest();

                Dotenv dotenv = Dotenv.load();

                Map<String, Object> variables = new HashMap<>();

                variables.put("nomeCliente", usuario.getNome());
                variables.put("titulo", "Bloqueio de conta");
                variables.put("mensagem", "Sua conta foi bloqueada por excesso de tentativas inválidas, entre em contato com o suporte");
                variables.put("linkAcao", dotenv.get("FRONTEND_URL") + "/suporte");
                variables.put("textoBotao", "Ir para o suporte");

                emailRequest.setTipoEmail(TipoEmailEnum.NOTIFICACAO_IMOBILIARIA);
                emailRequest.setVariaveis(variables);
                emailRequest.setDestinatario(usuario.getEmail());
                emailService.enviarEmail(emailRequest);
            }

        }catch (NoSuchElementException _){}

    }


}
