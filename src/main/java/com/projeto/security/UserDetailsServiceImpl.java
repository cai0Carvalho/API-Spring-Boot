package com.projeto.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.projeto.entity.User;
import com.projeto.repository.UserRepository;

// O UserDetailService é usado para recuperar os detalhes do usuario que estão tentando se autenticar
// na aplicacao. Isso e feito atraves do metodo loadUserByUsername.
// Se o usuario nao for encontrado e disparada uma excecao do tipo UsernameNotFoundException

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired 
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Não foi possível encontrar o usuário com o email: " + email));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        System.out.println("Usuário encontrado: " + user.getEmail());
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getUserPassword(), // 🔹 Certifique-se de que está criptografada com BCrypt
            authorities
        );
    }
}