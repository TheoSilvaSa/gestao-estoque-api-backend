package com.senai.gestao_estoque_api.controller;

import com.senai.gestao_estoque_api.dto.UsuarioRequestDto;
import com.senai.gestao_estoque_api.dto.UsuarioResponseDto;
import com.senai.gestao_estoque_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarTodos() {
        List<UsuarioResponseDto> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDto usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criarUsuario(@Valid @RequestBody UsuarioRequestDto dto) {
        UsuarioResponseDto novoUsuario = usuarioService.criarUsuario(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoUsuario.getId()).toUri();

        return ResponseEntity.created(uri).body(novoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto dto) {
        UsuarioResponseDto usuarioAtualizado = usuarioService.atualizarUsuario(id, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}