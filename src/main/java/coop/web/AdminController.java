package coop.web;

import coop.db.ConfigDAO;
import coop.db.RoundDAO;
import coop.model.Round;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class AdminController {

    private final RoundDAO roundDAO;
    private final ConfigDAO configDAO;

    public AdminController(RoundDAO roundDAO, ConfigDAO configDAO) {
        this.roundDAO = roundDAO;
        this.configDAO = configDAO;
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
}

