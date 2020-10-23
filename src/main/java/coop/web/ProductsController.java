package coop.web;

import coop.model.Category;
import coop.model.CoopService;
import coop.model.ProductDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class ProductsController {

    private final CoopService service;

    public ProductsController(CoopService service) {
        this.service = service;
    }

    @GetMapping("/products/list")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<Category, List<ProductDetails>> productsByCategory = service.getProductsByCategory();

        request.setAttribute("productsByCategory", productsByCategory);
        return "product/list";
    }

}
