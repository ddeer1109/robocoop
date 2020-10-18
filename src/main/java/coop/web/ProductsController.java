package coop.web;

import coop.db.ProductDAO;
import coop.model.Category;
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
        List<Category> categories = productDAO.categories();
        Map<String, Category> categoryMap = new HashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getId(), category);
        }
        Map<Category, List<Product>> allByCategory = new HashMap<>();
        for (Product product : all) {
            Category category = categoryMap.get(product.getCategoryId());
            List<Product> products = allByCategory.get(category);
            if (products == null) {
                products = new ArrayList<>();
                allByCategory.put(category, products);
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
