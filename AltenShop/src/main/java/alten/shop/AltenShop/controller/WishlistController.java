package alten.shop.AltenShop.controller;

import alten.shop.AltenShop.model.Product;
import alten.shop.AltenShop.service.WishlistService;
import alten.shop.AltenShop.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private JwtUtil jwtUtil;

    private String getUserEmailFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); 
            return jwtUtil.extractEmail(token); // Extraire l'email depuis le token
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getWishlist(HttpServletRequest request) {
        String userEmail = getUserEmailFromToken(request);
        if (userEmail != null) {
            List<Product> wishlist = wishlistService.getAllProducts(userEmail);
            return ResponseEntity.ok(wishlist);
        }
        return ResponseEntity.status(401).build(); 
    }

    @PostMapping
    public ResponseEntity<String> addProductToWishlist(@RequestBody Product product, HttpServletRequest request) {
        String userEmail = getUserEmailFromToken(request);
        if (userEmail != null) {
            wishlistService.addProductToWishlist(userEmail, product);
            return ResponseEntity.ok("Product added to wishlist");
        }
        return ResponseEntity.status(401).build(); 
    }

    @DeleteMapping
    public ResponseEntity<String> removeProductFromWishlist(@RequestBody Product product, HttpServletRequest request) {
        String userEmail = getUserEmailFromToken(request);
        if (userEmail != null) {
            wishlistService.removeProductFromWishlist(userEmail, product);
            return ResponseEntity.ok("Product removed from wishlist");
        }
        return ResponseEntity.status(401).build(); 
    }
}

