package coop.web;

import coop.model.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartControlller {

    private final Cart cart;

    public CartControlller(Cart cart) {
        this.cart = cart;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/order")
    public String order(@RequestParam("product_id") String productId) {
        cart.getItems().add(new Cart.Item(productId));
        // TODO make this context independent
        return "redirect:/cart";
    }
}
