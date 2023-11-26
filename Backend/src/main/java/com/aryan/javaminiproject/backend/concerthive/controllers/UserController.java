package com.aryan.javaminiproject.backend.concerthive.controllers;


import com.aryan.javaminiproject.backend.concerthive.models.Entities.User;
import com.aryan.javaminiproject.backend.concerthive.models.ResponseBodies.Login;
import com.aryan.javaminiproject.backend.concerthive.models.ResponseBodies.UpdatedUser;
import com.aryan.javaminiproject.backend.concerthive.repositories.UserRepository;
import com.aryan.javaminiproject.backend.concerthive.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    UserRepository repo;

    //Sign Up API
    @PostMapping("/user/register-user")
    public ResponseEntity<?> saveUser(
      @RequestParam("username") String username,
      @RequestParam("email") String email,
      @RequestParam("password") String password
    ){
        if (service.checkUserByEmail(email)>0){
         return new ResponseEntity<>("account with that email already exists",HttpStatus.BAD_REQUEST);

        } else if (service.checkUsernameByUsername(username)>0) {
            return new ResponseEntity<>("username taken",HttpStatus.BAD_REQUEST);
        }
        if (username.isEmpty()||email.isEmpty()||password.isEmpty()){
            return new ResponseEntity<>("Invalid Details", HttpStatus.BAD_REQUEST);
        }
        else{
            int result = service.saveUser(username, email, password);
            if (result==0){
                return new ResponseEntity<>("Failed to save user",HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("registered successfully",HttpStatus.OK);
        }
    };

    //Login API
    @PostMapping("/user/login-user")
    public ResponseEntity<?> loginUser(
            @RequestBody Login login
            ){
        String checkPass = login.getPassword();
        String checkusername= login.getUsername();
        if (checkPass.isEmpty()||checkusername.isEmpty()){
            return new ResponseEntity<>("No Data Available",HttpStatus.BAD_REQUEST);
        }
        if(service.checkUsernameByUsername(checkusername)==0){
            return new ResponseEntity<>("User Doesnt Exist",HttpStatus.NOT_FOUND);
        }
        String actualPassword = service.fetchPassword(checkusername);
        if (actualPassword==null || !(actualPassword.equals(checkPass))){
            return new ResponseEntity<>("Incorrect Password",HttpStatus.NOT_FOUND);
        }
        else{
            User myData = service.fetchUserData(checkusername);
            System.out.println(myData);


            // Create a Map to hold user data including id
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", myData.getId());
            responseData.put("username", myData.getUsername());
            responseData.put("email", myData.getEmail());
            responseData.put("password", myData.getPassword());
            System.out.println(responseData);
            return new ResponseEntity<>(responseData,HttpStatus.OK);
        }
    }

    //UPDATE API
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id , @RequestBody UpdatedUser new_user){

        String new_username = new_user.getUsername();
        String new_email = new_user.getEmail();
        String new_password = new_user.getPassword();

        if(new_username.isEmpty()||new_email.isEmpty()||new_password.isEmpty()){
            return new ResponseEntity<String>("Invalid Details" , HttpStatus.BAD_REQUEST);
        }

        Optional<User> UserExists=repo.findById(id);
        if(UserExists.isEmpty()){
            return new ResponseEntity<String>("User Not Found" , HttpStatus.NOT_FOUND);
        }
        else{

            int affectedRows = service.updateUserDetails(id , new_username , new_email , new_password);
            if (affectedRows==0){
                return new ResponseEntity<String>("User Not Found" , HttpStatus.NOT_FOUND);
            }
            else {
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("id", id);
                responseData.put("username", new_username);
                responseData.put("email", new_email);
                responseData.put("password", new_password);
                System.out.println(responseData);
                return new ResponseEntity<>(responseData,HttpStatus.OK);
            }
        }
    }

    //FETCH USER BY ID
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id){
        Optional<User> UserExists=repo.findById(id);
        if(UserExists.isEmpty()){
            return new ResponseEntity<String>("User Not Found" , HttpStatus.NOT_FOUND);
        }
        else{
            Optional<User> myData = repo.findById(id);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", myData.get().getId());
            responseData.put("username", myData.get().getUsername());
            responseData.put("email", myData.get().getEmail());
            responseData.put("password", myData.get().getPassword());
            return new ResponseEntity<>(responseData,HttpStatus.OK);
        }
    }
}

