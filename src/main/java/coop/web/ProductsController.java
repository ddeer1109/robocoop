package coop.web;

import coop.db.ProductDAO;
import coop.model.Category;
import coop.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
        Map<Category, List<Product>> productsByCategory = new LinkedHashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getId(), category);
            productsByCategory.put(category, new ArrayList<>());
        }
        for (Product product : all) {
            Category category = categoryMap.get(product.getCategoryId());
            List<Product> products = productsByCategory.get(category);
            products.add(product);
        }

        request.setAttribute("productsByCategory", productsByCategory);
        return "product/list";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
