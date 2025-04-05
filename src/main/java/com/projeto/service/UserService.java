package com.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.projeto.entity.User;
import com.projeto.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o encoder

    // Método para salvar um novo usuário com senha criptografada
    public void salvarUsuario(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword())); // Criptografa a senha
        userRepository.save(user);
    }

    // Método para buscar usuário por e-mail
    public User findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Método para validar as credenciais do usuário
    public boolean validarCredenciais(String email, String senha) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        return passwordEncoder.matches(senha, user.getUserPassword()); // Verifica se a senha informada bate com a senha criptografada
    }

    // Método para salvar o usuário
    public User save(User user) {
        return userRepository.save(user);
    }
}
