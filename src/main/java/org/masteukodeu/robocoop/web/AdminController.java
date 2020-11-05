package org.masteukodeu.robocoop.web;

import org.masteukodeu.robocoop.db.ConfigDAO;
import org.masteukodeu.robocoop.db.ProductDAO;
import org.masteukodeu.robocoop.db.RoundDAO;
import org.masteukodeu.robocoop.model.Round;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class AdminController {

    private final RoundDAO roundDAO;
    private final ConfigDAO configDAO;
    private final ProductDAO productDAO;

    public AdminController(RoundDAO roundDAO, ConfigDAO configDAO, ProductDAO productDAO) {
        this.roundDAO = roundDAO;
        this.configDAO = configDAO;
        this.productDAO = productDAO;
    }

    @GetMapping("/admin/new_round")
    public String newRoundForm() {
        return "admin/new_round";
    }

    @PostMapping("/admin/new_round")
    public String createNewRound(@RequestParam("round_name") String roundName, @RequestParam("final_date") String finalDate) {
        String roundId = roundDAO.add(new Round(null, roundName, LocalDate.parse(finalDate)));
        configDAO.setCurrentRound(roundId);
        return "redirect:/admin/new_round_created";
    }

    @GetMapping("/admin/new_round_created")
    public String newRoundCreated() {
        return "admin/new_round_created";
    }


    @GetMapping("/admin")
    public String admin() {
        return "admin/admin";
    }

    @GetMapping("/admin/categories")
    public String categories(Model model) {
        model.addAttribute("categories", productDAO.categories());
        return "admin/categories";
    }
}

