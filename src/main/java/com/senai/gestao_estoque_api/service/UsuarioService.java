package com.senai.gestao_estoque_api.service;

import com.senai.gestao_estoque_api.dto.UsuarioRequestDto;
import com.senai.gestao_estoque_api.dto.UsuarioResponseDto;
import com.senai.gestao_estoque_api.model.Usuario;
import com.senai.gestao_estoque_api.repository.UsuarioRepository;
import com.senai.gestao_estoque_api.service.exception.BusinessException;
import com.senai.gestao_estoque_api.service.exception.ResourceNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioResponseDto(usuario))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        return new UsuarioResponseDto(usuario);
    }

    @Transactional
    public UsuarioResponseDto criarUsuario(UsuarioRequestDto dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException("E-mail já cadastrado"); //
        }

        if (dto.getSenha() == null || !isSenhaValida(dto.getSenha())) {
            throw new BusinessException("Senha inválida. Deve ter no mínimo 8 caracteres, 1 letra maiúscula e 1 número.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNomeCompleto(dto.getNomeCompleto());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(dto.getSenha())); //
        novoUsuario.setPerfil(dto.getPerfil());
        novoUsuario.setAtivo(dto.getAtivo());

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return new UsuarioResponseDto(usuarioSalvo);
    }

    @Transactional
    public UsuarioResponseDto atualizarUsuario(Long id, UsuarioRequestDto dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        if (!usuario.getEmail().equals(dto.getEmail())) {
            if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new BusinessException("E-mail já está em uso por outro usuário");
            }
            usuario.setEmail(dto.getEmail());
        }

        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setPerfil(dto.getPerfil());
        usuario.setAtivo(dto.getAtivo());

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            if (!isSenhaValida(dto.getSenha())) {
                throw new BusinessException("Senha inválida. Deve ter no mínimo 8 caracteres, 1 letra maiúscula e 1 número.");
            }
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return new UsuarioResponseDto(usuarioAtualizado);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        usuarioRepository.delete(usuario);
    }


    private boolean isSenhaValida(String senha) {
        if (senha == null || senha.length() < 8) {
            return false;
        }
        boolean temMaiuscula = false;
        boolean temNumero = false;
        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                temMaiuscula = true;
            } else if (Character.isDigit(c)) {
                temNumero = true;
            }
        }
        return temMaiuscula && temNumero;
    }
}