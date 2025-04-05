package com.projeto.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.dto.LoginCredentials;
import com.projeto.entity.User;
import com.projeto.security.JWTUtil;
import com.projeto.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;

    //Registo de usuário
    @PostMapping("/registro")
    public Map<String, Object> registerHandler(@RequestBody User user){
        // Salva o usuário com a senha criptografada
        userService.salvarUsuario(user);
        // Gerando o token JWT a partir do e-mail do Usuario
        //String token = jwtUtil.generateToken(user.getEmail());

        User usuarioResumido = new User();
        usuarioResumido.setUserName(user.getUserName());
        usuarioResumido.setEmail(user.getEmail());
        usuarioResumido.setUserId(user.getUserId());

        // Gerando o token JWT a partir dos dados do Usuario
        String token = jwtUtil.generateTokenWithUserData(usuarioResumido);

        // Retornando a resposta com o JWT
        return Collections.singletonMap("jwt-token", token);
    }

    // Login de usuario
   @PostMapping("/login")
   public ResponseEntity<?> loginHandler(@RequestBody LoginCredentials body) {
       try {
           // Verificando as credenciais com o método de validação
           boolean autenticado = userService.validarCredenciais(body.getEmail(), body.getPassword());
   
           // Se as credenciais não forem válidas, retornamos um erro 401
           if (!autenticado) {
               return ResponseEntity.status(401).body(Collections.singletonMap("error", "Credenciais inválidas"));
           }
   
           // Se a autenticação foi bem-sucedida, buscamos os dados do usuário
           User user = userService.findByEmail(body.getEmail());
           User usuarioResumido = new User();
           usuarioResumido.setUserName(user.getUserName());
           usuarioResumido.setUserId(user.getUserId());
   
           // Gerando o token JWT com os dados do usuário
           String token = jwtUtil.generateTokenWithUserData(usuarioResumido);
   
           // Retornando o token JWT na resposta
           return ResponseEntity.ok(Collections.singletonMap("jwt-token", token));
   
       } catch (Exception e) {
           // Caso ocorra alguma exceção, retornamos erro 401
           return ResponseEntity.status(401).body(Collections.singletonMap("error", "Credenciais inválidas"));
       }
   }
   

}
