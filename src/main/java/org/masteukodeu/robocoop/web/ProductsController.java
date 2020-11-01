package org.masteukodeu.robocoop.web;

import org.masteukodeu.robocoop.model.Category;
import org.masteukodeu.robocoop.model.CoopService;
import org.masteukodeu.robocoop.model.ProductDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ProductsController {

    private final CoopService service;

    public ProductsController(CoopService service) {
        this.service = service;
    }

    @GetMapping("/products/list")
    public String doGet(Model model) {
        Map<Category, List<ProductDetails>> productsByCategory = service.getProductsByCategory();
        model.addAttribute("productsByCategory", productsByCategory);
        model.addAttribute("orderingBlocked", service.isOrderingBlocked());
        return "product/list";
    }

}
