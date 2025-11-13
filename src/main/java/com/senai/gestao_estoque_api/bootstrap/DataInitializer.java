package com.senai.gestao_estoque_api.bootstrap;

import com.senai.gestao_estoque_api.model.Perfil;
import com.senai.gestao_estoque_api.model.Usuario;
import com.senai.gestao_estoque_api.repository.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (usuarioRepository.findByEmail("admin@gestao.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNomeCompleto("Administrador Padrão");
            admin.setEmail("admin@gestao.com");
            admin.setSenha(passwordEncoder.encode("Admin@123")); // Senha
            admin.setPerfil(Perfil.ADMIN);
            admin.setAtivo(true);
            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByEmail("operador@gestao.com").isEmpty()) {
            Usuario operador = new Usuario();
            operador.setNomeCompleto("Operador de Caixa");
            operador.setEmail("operador@gestao.com");
            operador.setSenha(passwordEncoder.encode("Operador@123")); // Senha
            operador.setPerfil(Perfil.OPERADOR);
            operador.setAtivo(true);
            usuarioRepository.save(operador);
        }
    }
}