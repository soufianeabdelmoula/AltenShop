package alten.shop.AltenShop.service;

import alten.shop.AltenShop.model.Product;
import alten.shop.AltenShop.model.Wishlist;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WishlistService {

    private final Map<String, Wishlist> userWishlists = new HashMap<>();

    public Wishlist getWishlist(String userEmail) {
        return userWishlists.computeIfAbsent(userEmail, Wishlist::new);
    }

    public void addProductToWishlist(String userEmail, Product product) {
        Wishlist wishlist = getWishlist(userEmail);
        wishlist.addProduct(product);
    }

    public void removeProductFromWishlist(String userEmail, Product product) {
        Wishlist wishlist = getWishlist(userEmail);
        wishlist.removeProduct(product);
    }

    public List<Product> getAllProducts(String userEmail) {
        return getWishlist(userEmail).getProducts();
    }
}
