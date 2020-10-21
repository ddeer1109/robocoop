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
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CartController {

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final RoundDAO roundDAO;

    public CartController(ProductDAO productDAO, OrderDAO orderDAO, UserDAO userDAO, RoundDAO roundDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.roundDAO = roundDAO;
    }

    @GetMapping("/cart")
    public String cart(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        Round currentRound = roundDAO.current();
        String roundId = currentRound.getId();
        Cart cart = getCartForUserAndRound(username, roundId);
        model.addAttribute("cart", cart);
        return "cart";
    }

    private Cart getCartForUserAndRound(String username, String roundId) {
        User user = userDAO.byUsername(username);
        List<Order> orders = orderDAO.byUserAndRound(user.getId(), roundId);
        List<Cart.Item> items = orders.stream().map(o -> new Cart.Item(productDAO.byId(o.getProductId()), o.getQuantity())).collect(Collectors.toList());
        Cart cart = new Cart(items);
        return cart;
    }

    @PostMapping("/order")
    public String order(HttpServletRequest request,  @RequestParam("product_id") String productId, @RequestParam(value = "quantity", required = true) BigDecimal quantity) {
        if (quantity == null) {
            throw new RuntimeException("Quantity is mandatory");
        }
        User user = userDAO.byUsername(request.getRemoteUser());
        Round currentRound = roundDAO.current();
        orderDAO.add(new Order(productId, currentRound.getId(), user.getId(), quantity ));
        // TODO make this context independent
        return "redirect:/cart";
    }
}
