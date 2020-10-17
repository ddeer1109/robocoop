package coop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartControlller {

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
