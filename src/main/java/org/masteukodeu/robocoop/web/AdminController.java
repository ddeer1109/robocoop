package org.masteukodeu.robocoop.web;

import org.masteukodeu.robocoop.db.*;
import org.masteukodeu.robocoop.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoundDAO roundDAO;
    private final ConfigDAO configDAO;
    private final CategoryDAO categoryDAO;
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;

    public AdminController(RoundDAO roundDAO, ConfigDAO configDAO, CategoryDAO categoryDAO, OrderDAO orderDAO, ProductDAO productDAO, UserDAO userDAO) {
        this.roundDAO = roundDAO;
        this.configDAO = configDAO;
        this.categoryDAO = categoryDAO;
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    @GetMapping("/new_round")
    public String newRoundForm() {
        return "admin/new_round";
    }

    @PostMapping("/new_round")
    public String createNewRound(@RequestParam("round_name") String roundName, @RequestParam("final_date") String finalDate) {
        String roundId = roundDAO.add(new Round(null, roundName, LocalDate.parse(finalDate)));
        configDAO.setCurrentRound(roundId);
        return "redirect:/admin/new_round_created";
    }

    @GetMapping("/new_round_created")
    public String newRoundCreated() {
        return "admin/new_round_created";
    }


    @GetMapping("/")
    public String admin() {
        return "admin/admin";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryDAO.all());
        return "admin/categories";
    }

    @GetMapping("/category/edit")
    public String categoryEditForm(Model model, String id) {
        model.addAttribute("category", categoryDAO.byId(id));
        return "admin/category_edit";
    }

    @PostMapping("/category/edit")
    public String categoryEdit(String id, String name, boolean hidden, @RequestParam("blocked_period") BigDecimal blockedPeriod) {
        Category category = new Category(id, name, hidden, blockedPeriod);
        categoryDAO.update(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("history", roundDAO.all());
        return "admin/history";
    }

    @GetMapping("/round")
    public String roundDetails(Model model, @RequestParam("id") String roundId) {
        model.addAttribute("round", roundDAO.byId(roundId));
        List<Order> orders = orderDAO.byRound(roundId);

        Map<User, Cart> cartsByUser = new HashMap<>();

        for (Order order : orders) {
            User user = userDAO.byId(order.getUserId());
            Cart cart = cartsByUser.getOrDefault(user, new Cart(new ArrayList<>()));
            cart.getItems().add(new Cart.Item(order.getId(), productDAO.byId(order.getProductId()), order.getQuantity()));
            cartsByUser.put(user, cart);
        }

        model.addAttribute("cartsByUser", cartsByUser);
        return "admin/round_details";
    }
}

