package com.projeto.security;

import java.io.IOError;
import java.io.IOException;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

    @Autowired private userDetailServiceImpl userDetailService;
    @Autowired private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        
        // Extrai, a partir do header da requisicao, o "Authorization header"
        String authHeader = request.getHeader("Authorization");

        // Checa se o "Auth header" contem um Bearer token
        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer")){
            // Extrai informacoes do JWT para valida-lo
            String jwt = authHeader.substring(7);
            if(jwt == null || jwt.isBlank()){
                // Se o JWT for invalido, aborta a requisicao
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "foi passado um Token JWT inválido no Bearer Header");
            } else {
                try{
                    // Verifica o token recebido e extrai o e-mail
                    String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);

                    // Coleta os dados do usuario a partir do email
                    UserDetails userDetails = userDetailService.loadUserByUsername(email);

                    //Cria o Authentication Tokem
                    UsernamePasswoedAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                    //Configura o contexto de Segurança com o token acima
                    if (SecurityContextHolder.getContext().getAuthentication() == null){
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }catch(JWTVerificationException e){
                    response.sendErro(HttpServletResponse.SC_BAD_REQUEST, "Token JWT inválido");
                }catch(Exception e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Não foi possível obter os dados a partir do token - " + e);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
