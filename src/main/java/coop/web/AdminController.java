package coop.web;

import coop.db.RoundDAO;
import coop.model.Round;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final RoundDAO roundDAO;

    public AdminController(RoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    @GetMapping("/admin/new_round")
    public String newRoundForm() {
        return "new_round";
    }

    @PostMapping("/admin/new_round")
    public String createNewRound(@RequestParam("round_name") String roundName) {
        roundDAO.add(new Round(null, roundName));
        return "redirect:/new_round_create";
    }
}
