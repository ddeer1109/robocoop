package coop.web;

import coop.db.OrderDAO;
import coop.db.ProductDAO;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class CartController {

    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final RoundDAO roundDAO;
    private final CoopService coopService;
    private final ProductDAO productDAO;

    public CartController(OrderDAO orderDAO, UserDAO userDAO, RoundDAO roundDAO, CoopService coopService, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.roundDAO = roundDAO;
        this.coopService = coopService;
        this.productDAO = productDAO;
    }

    @GetMapping("/cart")
    public String cart(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        Round currentRound = roundDAO.current();
        Cart cart = coopService.getCartForUserAndRound(username, currentRound.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("orderingBlocked", coopService.isOrderingBlocked());
        return "cart";
    }

    @PostMapping("/order")
    public String order(HttpServletRequest request, @RequestParam("product_id") String productId, @RequestParam(value = "quantity") BigDecimal quantity) {
        // TODO Check why it can be null if request param is required by default
        if (quantity == null) {
            throw new RuntimeException("Quantity is mandatory");
        }

        if (coopService.isOrderingBlocked()) {
            return "redirect:/products/list?error=" + URLEncoder.encode("Nie można już zamawiać produktów!", StandardCharsets.UTF_8);
        }

        Product product = productDAO.byId(productId);
        if (!product.getAllowDecimalQuantity()) {
            if (!isIntegerValue(quantity)) {
                return "redirect:/products/list?error=" + URLEncoder.encode("Niedozwolona wartosć - tylko wartosci całkowite!", StandardCharsets.UTF_8);
            }
        }

        User user = userDAO.byUsername(request.getRemoteUser());
        Round currentRound = roundDAO.current();
        orderDAO.add(new Order(null, productId, currentRound.getId(), user.getId(), quantity));
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeItem(HttpServletRequest request, @RequestParam("order_id") String orderId) {
        User user = userDAO.byUsername(request.getRemoteUser());
        Order order = orderDAO.byId(orderId);
        if (!order.getUserId().equals(user.getId())) {
            throw new RuntimeException("User " + user.getId() + " trying to remove order (" + orderId + ") of other user");
        }

        if (coopService.isOrderingBlocked()) {
            return "redirect:/cart?error=" + URLEncoder.encode("Nie można już usuwać zamówień!", StandardCharsets.UTF_8);
        }

        orderDAO.delete(orderId);
        return "redirect:/cart";
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        try {
            bd.toBigIntegerExact();
            return true;
        } catch (ArithmeticException ex) {
            return false;
        }
    }
}
