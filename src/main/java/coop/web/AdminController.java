package coop.web;

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

    public AdminController(RoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    @GetMapping("/admin/new_round")
    public String newRoundForm() {
        return "new_round";
    }

    @PostMapping("/admin/new_round")
    public String createNewRound(@RequestParam("round_name") String roundName, @RequestParam("final_date") String finalDate){
        roundDAO.add(new Round(null, roundName, LocalDate.parse(finalDate)));
        return "redirect:/admin/new_round_created";
    }

    @GetMapping("/admin/new_round_created")
    public String newRoundCreated() {
        return "new_round_created";
    }
    }
