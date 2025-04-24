package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.auth.CodigoDoisFatoresRequestDTO;
import com.hav.imobiliaria.controller.dto.auth.LoginGoogleRequestDTO;
import com.hav.imobiliaria.controller.dto.auth.LoginRequestDTO;
import com.hav.imobiliaria.controller.dto.auth.LoginResponseDTO;
import com.hav.imobiliaria.controller.dto.exception.ErroResposta;
import com.hav.imobiliaria.exceptions.requisicao_padrao.Codigo2FAInvalidoException;
import com.hav.imobiliaria.exceptions.requisicao_padrao.UsuarioNaoEncontradoException;
import com.hav.imobiliaria.model.entity.TentativaLoginUsuario;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.model.entity.UsuarioComum;
import com.hav.imobiliaria.security.GoogleAuthenticationToken;
import com.hav.imobiliaria.security.service.TokenService;
import com.hav.imobiliaria.service.AuthDoisFatoresService;
import com.hav.imobiliaria.service.TentativaLoginUsuarioService;
import com.hav.imobiliaria.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    private final AuthenticationManager authenticationManager;
    private final AuthDoisFatoresService authDoisFatoresService;
    private final TentativaLoginUsuarioService tentativaLoginUsuarioService;
    private final TokenService tokenService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        try{
            var auth = this.authenticationManager.authenticate(usernamePassword);

            Usuario usuario = (Usuario) auth.getPrincipal();

            var token = tokenService.generateToken(usuario);

            return ResponseEntity.ok(new LoginResponseDTO(token ));
        }catch (BadCredentialsException e ){
           tentativaLoginUsuarioService.verificarTentativaUsuario(data.email());
        }
        ErroResposta erroResposta =
                new ErroResposta(HttpStatus.UNAUTHORIZED.value(), "Usuário ou senha incorreto", List.of());

        return new ResponseEntity<>(erroResposta, HttpStatus.UNAUTHORIZED );
    }
    @PreAuthorize("permitAll()")
    @PostMapping("/google")
    public ResponseEntity<?> loginGoogle(@RequestBody @Valid LoginGoogleRequestDTO data){

       try{
           Usuario usuario = usuarioService.buscarPorEmail(data.email());

           GoogleAuthenticationToken autentication = new GoogleAuthenticationToken(new ArrayList<>(),
                   usuario);

           authenticationManager.authenticate(autentication);

           var token  = tokenService.generateToken(usuario);

           return ResponseEntity.ok(new LoginResponseDTO(token));

       }catch (UsuarioNaoEncontradoException e){
            Usuario usuario = new UsuarioComum();
            usuario.setNome(data.nome());
            usuario.setEmail(data.email());
            usuario.setFoto(data.foto());
            usuario = usuarioService.salvarUsuarioRequisicaoGoogle(usuario);

           GoogleAuthenticationToken autentication = new GoogleAuthenticationToken(new ArrayList<>(),
                   usuario);

            authenticationManager.authenticate(autentication);

           var token  = tokenService.generateToken(usuario);

           return ResponseEntity.ok(new LoginResponseDTO(token));


       }



    }


    @PreAuthorize("permitAll()")
    @GetMapping("verificar-2fa-habilitado/{email}")
    public ResponseEntity<Map> verificar2FAHabilitadoPor(@PathVariable String email){
        Boolean habilitado = this.usuarioService.buscarPorEmail(email).getAutenticacaoDoisFatoresHabilitado();
        if(habilitado){
            authDoisFatoresService.gerarESalvarCodigoDoisFatores(email);
        }
        return ResponseEntity.ok(
                Map.of("habilitado",  habilitado)

       );
    }


    @PreAuthorize("permitAll()")
    @PostMapping("2fa/verify")
    public ResponseEntity<?> verificarCodigo(@RequestBody @Valid CodigoDoisFatoresRequestDTO codigoDoisFatoresRequestDTO) {
        Boolean valido = authDoisFatoresService.validarCodigo(codigoDoisFatoresRequestDTO.email(), codigoDoisFatoresRequestDTO.codigo());

        Usuario usuario = usuarioService.buscarPorEmail(codigoDoisFatoresRequestDTO.email());

        if(valido){
            var auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), codigoDoisFatoresRequestDTO.senha()));
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        }
        throw new Codigo2FAInvalidoException();
    }



}
