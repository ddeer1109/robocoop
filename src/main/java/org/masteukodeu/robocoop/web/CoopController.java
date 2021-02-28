package org.masteukodeu.robocoop.web;

import org.masteukodeu.robocoop.db.OrderDAO;
import org.masteukodeu.robocoop.db.ProductDAO;
import org.masteukodeu.robocoop.db.RoundDAO;
import org.masteukodeu.robocoop.db.UserDAO;
import org.masteukodeu.robocoop.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CoopController {

    private final RoundDAO roundDAO;
    private final CoopService service;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    public CoopController(RoundDAO roundDAO, CoopService service, OrderDAO orderDAO, UserDAO userDAO, ProductDAO productDAO) {
        this.roundDAO = roundDAO;
        this.service = service;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/history")
    public String history(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        List<Round> rounds = roundDAO.all();
        List<HistoricalCart> history = rounds.stream().map(r -> new HistoricalCart(r, service.getCartForUserAndRound(username, r.getId()))).collect(Collectors.toList());
        model.addAttribute("history", history);
        return "history";
    }

    @GetMapping("/round")
    public String roundDetails(Model model, @RequestParam("id") String roundId, HttpServletRequest request) {
        String username = request.getRemoteUser();
        User user = userDAO.byUsername(username);
        model.addAttribute("round", roundDAO.byId(roundId));
        List<Order> orders = orderDAO.byUserAndRound(user.getId(), roundId);
//        orders.stream().map(o -> new Cart.Item(o.getId(), productDAO.byId(o.getProductId()), o.getQuantity()));
        model.addAttribute("orders", orders);
        Cart cart = new Cart(new ArrayList<>());

        for (Order order : orders) {
            cart.getItems().add(new Cart.Item(order.getId(), productDAO.byId(order.getProductId()), order.getQuantity()));
        }
        model.addAttribute("cart", cart);
        return "round_details";
    }

    @GetMapping("/total")
    public String total(Model model) {
        model.addAttribute("round", roundDAO.current());
        model.addAttribute("orders", service.getProductsByCategory());
        return "total";
    }

    public static class HistoricalCart {
        public final Round round;
        public final Cart cart;

        public HistoricalCart(Round round, Cart cart) {
            this.round = round;
            this.cart = cart;
        }
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
