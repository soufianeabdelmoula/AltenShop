package alten.shop.AltenShop.controller;

import alten.shop.AltenShop.model.Cart;
import alten.shop.AltenShop.model.CartItem;
import alten.shop.AltenShop.service.CartService;
import alten.shop.AltenShop.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    private String getUserEmailFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); 
            return jwtUtil.extractEmail(token);
        }
        return null; 
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(HttpServletRequest request) {
        String userEmail = getUserEmailFromToken(request);
        if (userEmail != null) {
            Cart cart = cartService.getCart(userEmail);
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem, HttpServletRequest request) {
        String userEmail = getUserEmailFromToken(request);
        if (userEmail != null) {
            cartService.addToCart(userEmail, cartItem);
            return ResponseEntity.ok("Product added to cart");
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable long productId, HttpServletRequest request) {
        String userEmail = getUserEmailFromToken(request);
        if (userEmail != null) {
            cartService.removeFromCart(userEmail, productId);
            return ResponseEntity.ok("Product removed from cart");
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(HttpServletRequest request) {
        String userEmail = getUserEmailFromToken(request);
        if (userEmail != null) {
            cartService.clearCart(userEmail);
            return ResponseEntity.ok("Cart cleared");
        }
        return ResponseEntity.status(401).body("Unauthorized");
    }
}

