package coop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoopController {

    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @GetMapping("/history")
    public String history() {
        return "history";
    }

    @GetMapping("/total")
    public String total() {
        return "total";
    }

}
