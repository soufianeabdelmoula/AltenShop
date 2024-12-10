package alten.shop.AltenShop.controller;

import alten.shop.AltenShop.model.User;
import alten.shop.AltenShop.service.AuthService;
import alten.shop.AltenShop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/account")
    public ResponseEntity<String> createAccount(@RequestBody User user) {
        if (authService.register(user)) {
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.status(400).body("Email already in use");
    }

    @PostMapping("/token")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        User user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
   
            String token = jwtUtil.generateToken(user.getEmail());  
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

}

