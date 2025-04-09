package com.hav.imobiliaria.controller;

import com.hav.imobiliaria.controller.dto.auth.CodigoDoisFatoresRequestDTO;
import com.hav.imobiliaria.controller.dto.auth.LoginResponseDTO;
import com.hav.imobiliaria.model.entity.Usuario;
import com.hav.imobiliaria.security.service.TokenService;
import com.hav.imobiliaria.service.AuthDoisFatoresService;
import com.hav.imobiliaria.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/2fa")
@AllArgsConstructor
public class AuthDoisFatoresController {


    private final AuthDoisFatoresService service;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/verify")
    public ResponseEntity<?> verificarCodigo(@RequestBody @Valid CodigoDoisFatoresRequestDTO codigoDoisFatoresRequestDTO) {
        Boolean valido = service.validarCodigo(codigoDoisFatoresRequestDTO.email(), codigoDoisFatoresRequestDTO.codigo());

        Usuario usuario = usuarioService.buscarPorEmail(codigoDoisFatoresRequestDTO.email());

        if(valido){
            var auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), passwordEncoder.usuario.getSenha()));
            var token = tokenService.generateToken(usuario);

            return ResponseEntity.ok(new LoginResponseDTO(token));
        }

    }


}
