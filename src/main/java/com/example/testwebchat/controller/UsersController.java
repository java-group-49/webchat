package com.example.testwebchat.controller;

import com.example.testwebchat.storage.UserStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin
public class UsersController {

    @GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName){
        System.out.println("register: " + userName + " in  system");
        try {
            UserStorage.getInstance().setUser(userName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetchAll")
    public Set<String> getAll(){
        System.out.println("fetching");
        return UserStorage.getInstance().getUsers();
    }
}
