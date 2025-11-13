package com.senai.gestao_estoque_api.controller;

import com.senai.gestao_estoque_api.dto.LoginRequestDto;
import com.senai.gestao_estoque_api.dto.LoginResponseDto;
import com.senai.gestao_estoque_api.model.Usuario;
import com.senai.gestao_estoque_api.service.AuthService;
import com.senai.gestao_estoque_api.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {

        Optional<Usuario> usuarioOpt = authService.validarLogin(
                loginRequest.getEmail(),
                loginRequest.getSenha()
        );

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            String token = tokenService.gerarToken(usuario);

            LoginResponseDto responseDto = new LoginResponseDto(
                    token,
                    usuario.getNomeCompleto(),
                    usuario.getPerfil().name()
            );

            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}