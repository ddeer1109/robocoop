package coop.web;

import coop.db.RoundDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoopController {

    private final RoundDAO roundDAO;

    public CoopController(RoundDAO roundDAO) {
        this.roundDAO = roundDAO;
    }

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/history")
    public String history(Model model)
    {
        model.addAttribute("rounds", roundDAO.all());
        return "history";
    }

    @GetMapping("/total")
    public String total() {
        return "total";
    }

}
