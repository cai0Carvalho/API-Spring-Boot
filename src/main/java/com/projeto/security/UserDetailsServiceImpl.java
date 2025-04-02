package com.projeto.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.projeto.entity.User;
import com.projeto.repository.UserRepositoy;

// O UserDetailService é usado para recuperar os detalhes do usuario que estão tentando se autenticar
// na aplicacao. Isso e feito atraves do metodo loadUserByUsername.
// Se o usuario nao for encontrado e disparada uma excecao do tipo UsernameNotFoundException

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired private UserRepositoy userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional<User> userRes = userRepo.findUserByEmail(email);
    if(userRes.isEmpty()){
        throw new UsernameNotFoundException("Não foi possível encontrar o usuário com o email: " +email);
    }
    User user = userRes.get();
    return new org.springframework.security.core.userdetails.User(
        email,
        user.getUserPassword(),
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}