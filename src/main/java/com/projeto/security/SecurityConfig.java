package com.projeto.security;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JWTFilter filter;

    @Autowired
    private UserDetailsServiceImpl uds;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
      http
        .httpBasic(httpBasic -> httpBasic.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))//habilitando o cors

        // Autorizando requisições de entrada
        .authorizeHttpRequests( auth -> auth
            .requestMatchers("/auth/**").permitAll()// Autorizando requisições autenticadas
            .requestMatchers("/user/**").hasRole("USER")// Autorizando apenas usuários com o perfil "USER" a utilizar este endpoint
            .anyRequest().authenticated()
        )

        .UserDetailsService(uds)
        .exceptionHandling( ex -> ex
            .authenticationEntryPoint ((request, response, authException) -> 
                response.sendError (HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
            )
        )
        
        // Configurando a Sessão para que cada requisção seja independente (stateless)
        .sessionManagement( session -> session
            .SessionCreationPolicy(SessionCreationPolicy.STATELESS) 
        );

        // Adicionando o filtro JWT
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:9090")); // Domínio do frontend
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    configuration.setAllowCredentials(true); // Permitir envio de cookies

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

}
