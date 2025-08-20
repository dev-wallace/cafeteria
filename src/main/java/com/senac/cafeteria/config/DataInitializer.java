package com.senac.cafeteria.config;

import jakarta.annotation.PostConstruct; // <-- corrigido

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.senac.cafeteria.models.Usuario;
import com.senac.cafeteria.models.enums.Role;
import com.senac.cafeteria.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Criar usuário funcionário se não existir
        if (usuarioRepository.findByEmail("funcionario@cafe.com").isEmpty()) {
            Usuario funcionario = new Usuario();
            funcionario.setNome("Funcionário Admin");
            funcionario.setEmail("funcionario@cafe.com");
            funcionario.setSenha(passwordEncoder.encode("123456"));
            funcionario.setRole(Role.FUNCIONARIO);
            usuarioRepository.save(funcionario);
            System.out.println("Usuário funcionário criado: funcionario@cafe.com / 123456");
        }

        // Criar usuário cliente de teste
        if (usuarioRepository.findByEmail("cliente@teste.com").isEmpty()) {
            Usuario cliente = new Usuario();
            cliente.setNome("Cliente Teste");
            cliente.setEmail("cliente@teste.com");
            cliente.setSenha(passwordEncoder.encode("123456"));
            cliente.setRole(Role.CLIENTE);
            usuarioRepository.save(cliente);
            System.out.println("Usuário cliente criado: cliente@teste.com / 123456");
        }
    }
}
