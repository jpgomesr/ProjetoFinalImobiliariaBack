package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.auth.CodigoDoisFatoresRequestDTO;
import com.hav.imobiliaria.controller.dto.auth.LoginRequestDTO;
import com.hav.imobiliaria.controller.dto.auth.LoginResponseDTO;
import com.hav.imobiliaria.exceptions.requisicao_padrao.Codigo2FAInvalidoException;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.security.service.TokenService;
import com.hav.imobiliaria.service.AuthDoisFatoresService;
import com.hav.imobiliaria.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    private final AuthenticationManager authenticationManager;
    private final AuthDoisFatoresService authDoisFatoresService;
    private final TokenService tokenService;

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);


        Usuario usuario = (Usuario) auth.getPrincipal();

        if(usuario.getAutenticacaoDoisFatoresHabilitado().equals(Boolean.TRUE)){
            authDoisFatoresService.gerarESalvarCodigoDoisFatores(data.email());
            return ResponseEntity.ok(Map.of(
                    "require2fa", usuario.getAutenticacaoDoisFatoresHabilitado()
            ));
        }

        var token = tokenService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

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
