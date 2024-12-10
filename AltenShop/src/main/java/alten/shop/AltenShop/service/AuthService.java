package alten.shop.AltenShop.service;

import alten.shop.AltenShop.model.User;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private static final Map<String, User> users = new HashMap<>();
    private static final String ADMIN_EMAIL = "admin@admin.com";
    
    // Enregistrement d'un nouvel utilisateur
    public boolean register(User user) {
        if (!users.containsKey(user.getEmail())) {
            users.put(user.getEmail(), user);
            return true;
        }
        return false; 
    }

    // Connexion d'un utilisateur
    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;  
        }
        return null;  
    }
}

