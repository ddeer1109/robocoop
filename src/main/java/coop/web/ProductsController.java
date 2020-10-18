package coop.web;

import coop.db.ProductDAO;
import coop.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductsController {
    private final ProductDAO productDAO;

    public ProductsController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/products/list")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Product> all = productDAO.findAll();
        Map<String, List<Product>> allByCategory = new HashMap<>();
        for (Product product : all) {
            List<Product> products = allByCategory.get(product.getCategoryId());
            if (products == null) {
                products = new ArrayList<>();
                allByCategory.put(product.getCategoryId(), products);
            }
            products.add(product);
        }

        request.setAttribute("productsByCategory", allByCategory);
        return "product/list";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
