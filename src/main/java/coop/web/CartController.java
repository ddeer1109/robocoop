package coop.web;

import coop.db.OrderDAO;
import coop.db.RoundDAO;
import coop.db.UserDAO;
import coop.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
public class CartController {

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final RoundDAO roundDAO;
    private final CoopService coopService;

    public CartController(OrderDAO orderDAO, UserDAO userDAO, RoundDAO roundDAO, CoopService coopService) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.roundDAO = roundDAO;
        this.coopService = coopService;
    }

    @GetMapping("/cart")
    public String cart(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        Round currentRound = roundDAO.current();
        Cart cart = coopService.getCartForUserAndRound(username, currentRound.getId());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/order")
    public String order(HttpServletRequest request, @RequestParam("product_id") String productId, @RequestParam(value = "quantity", required = true) BigDecimal quantity) {
        if (quantity == null) {
            throw new RuntimeException("Quantity is mandatory");
        }
        User user = userDAO.byUsername(request.getRemoteUser());
        Round currentRound = roundDAO.current();
        orderDAO.add(new Order(productId, currentRound.getId(), user.getId(), quantity));
        // TODO make this context independent
        return "redirect:/cart";
    }
}
