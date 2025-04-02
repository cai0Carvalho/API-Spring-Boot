package com.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entity.User;
import com.projeto.repository.UserRepositoy;

@Service
public class UserService {
    @Autowired
    UserRepositoy userRespository;

    public User findByEmail(String email){
        return userRespository.findByUserEmai(email).get();
    }
    public User save(User user){
        return userRespository.save(user);
    }

}