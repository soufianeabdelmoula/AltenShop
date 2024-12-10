package alten.shop.AltenShop.service;

import alten.shop.AltenShop.model.Cart;
import alten.shop.AltenShop.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    private final Map<String, Cart> carts = new HashMap<>();

    public Cart getCart(String userEmail) {
        return carts.computeIfAbsent(userEmail, Cart::new);
    }

    public void addToCart(String userEmail, CartItem cartItem) {
        Cart cart = getCart(userEmail);
        cart.addItem(cartItem);
    }

    public void removeFromCart(String userEmail, long productId) {
        Cart cart = getCart(userEmail);
        cart.removeItem(productId);
    }

    public void clearCart(String userEmail) {
        carts.remove(userEmail);
    }
}
