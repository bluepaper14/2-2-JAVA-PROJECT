package project;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // 장바구니에 아이템 추가 (이름과 가격만 저장)
    public void addItem(String name, String price) {
        cartItems.add(new CartItem(name, price));
    }

    // 장바구니에 있는 아이템 목록 반환
    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
