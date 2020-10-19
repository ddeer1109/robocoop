package coop.web;

import coop.db.OrderDAO;
import coop.db.ProductDAO;
import coop.db.RoundDAO;
import coop.db.UserDAO;
import coop.model.Cart;
import coop.model.Order;
import coop.model.Round;
import coop.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
public class CartController {

    private final Cart cart;
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final RoundDAO roundDAO;

    public CartController(Cart cart, ProductDAO productDAO, OrderDAO orderDAO, UserDAO userDAO, RoundDAO roundDAO) {
        this.cart = cart;
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.roundDAO = roundDAO;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/order")
    public String order(HttpServletRequest request,  @RequestParam("product_id") String productId, @RequestParam(value = "quantity", required = true) BigDecimal quantity) {
        if (quantity == null) {
            throw new RuntimeException("Quantity is mandatory");
        }
        cart.getItems().add(new Cart.Item(productDAO.byId(productId), quantity));
        User user = userDAO.byUsername(request.getRemoteUser());
        Round currentRound = roundDAO.current();
        orderDAO.add(new Order(productId, currentRound.getId(), user.getId(), quantity ));
        // TODO make this context independent
        return "redirect:/cart";
    }
}
