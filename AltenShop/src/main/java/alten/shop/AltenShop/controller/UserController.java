package alten.shop.AltenShop.controller;

import alten.shop.AltenShop.model.User;

import alten.shop.AltenShop.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class UserController {

    @Autowired
    private UserService userService;


    // Route pour créer un compte
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok("Account created successfully");
    }


    
}