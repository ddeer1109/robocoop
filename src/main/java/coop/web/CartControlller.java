package coop.web;

import coop.db.ProductDAO;
import coop.model.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class CartControlller {

    private final Cart cart;
    private final ProductDAO productDAO;

    public CartControlller(Cart cart, ProductDAO productDAO) {
        this.cart = cart;
        this.productDAO = productDAO;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/order")
    public String order(@RequestParam("product_id") String productId, @RequestParam(value = "quantity", required = true) BigDecimal quantity) {
        cart.getItems().add(new Cart.Item(productDAO.byId(productId), quantity));
        // TODO make this context independent
        return "redirect:/cart";
    }
}
