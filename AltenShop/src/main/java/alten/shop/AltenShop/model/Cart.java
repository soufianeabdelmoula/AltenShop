package alten.shop.AltenShop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String userEmail;
    private List<CartItem> items = new ArrayList<>();

    public Cart() {}

    public Cart(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem item) {
        for (CartItem existingItem : items) {
            if (existingItem.getProductId() == item.getProductId()) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(long productId) {
        items.removeIf(item -> item.getProductId() == productId);
    }
}
