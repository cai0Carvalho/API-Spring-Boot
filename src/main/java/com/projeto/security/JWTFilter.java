package com.projeto.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

    @Autowired private UserDetailsServiceImpl userDetailService;
    @Autowired private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException{
        
        // Obtendo o cabeçalho Authorization
        String authHeader = request.getHeader("Authorization");

        // Verifica se há um token válido no cabeçalho
        if(authHeader != null && authHeader.startsWith("Bearer")){
            // Extrai informacoes do JWT para valida-lo, Remove "Bearer "
            String jwt = authHeader.substring(7);
            
            try{
                // Valida o token JWT e obtém o email do usuário
                String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);

                // Coleta os dados do usuario a partir do email
                UserDetails userDetails = userDetailService.loadUserByUsername(email);

                // Cria o token de autenticação para o Spring Security
                UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //Configura o contexto de Segurança com o token acima
                if (SecurityContextHolder.getContext().getAuthentication() == null){
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }catch(JWTVerificationException e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido");
                return;
            }catch(Exception e){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não foi possível obter os dados a partir do token - " + e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
