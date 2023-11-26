package com.aryan.javaminiproject.backend.concerthive.services;


import com.aryan.javaminiproject.backend.concerthive.models.Entities.User;
import com.aryan.javaminiproject.backend.concerthive.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository rep;
    public int saveUser(String username,String email,String password){
        return rep.insertUser(username, email, password);
    }

    public int checkUserByEmail(String email){
        return rep.userExists(email);
    }
    public int checkUsernameByUsername(String username){
        return rep.userExistsByUsername(username);
    }

    public String fetchPassword(String username){
        return rep.getPasswordByUsername(username);
    }

    public User fetchUserData(String username){
        return rep.getAllData(username);
    }

    public int updateUserDetails(int id ,String username,String email,String password){
        return rep.updateUserById(id , username , email , password);
    }
}
