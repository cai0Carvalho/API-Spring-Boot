package com.projeto.security;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.projeto.repository.UserRepositoy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired UserRepositoy UserRepo;
    @Autowired private JWTFilter filter;
    @Autowired private UserDetailsServiceImpl uds;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      http
        .httpBasic(httpBasic -> httpBasic.disable())// Desativa autenticação básica
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))//habilitando o cors

        // Autorizando requisições de entrada
        .authorizeHttpRequests( auth -> auth
            .requestMatchers("/auth/**").permitAll()// Autorizando requisições autenticadas
            .requestMatchers("/user/**").hasRole("USER")// Autorizando apenas usuários com o perfil "USER" a utilizar este endpoint
            .anyRequest().authenticated()
        )
         .userDetailsService(uds) // Define o serviço de usuários
        .exceptionHandling( ex -> ex
            .authenticationEntryPoint ((request, response, authException) -> 
                response.sendError (HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
            )
        )
        
        // Configurando a Sessão para que cada requisção seja independente (stateless)
        .sessionManagement( session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Adicionando o filtro JWT
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean (tipo de Service) que sera responsavel por encriptar a senha
    @Bean
    public PasswordEncoder passwordEconder(){
        return new BCryptPasswordEncoder();
    }

    // Expondo o bean responsavel pelo gerenciamento do processo de autenticacao
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
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
