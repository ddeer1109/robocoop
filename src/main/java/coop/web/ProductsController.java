package coop.web;

import coop.db.OrderDAO;
import coop.db.ProductDAO;
import coop.db.RoundDAO;
import coop.model.Category;
import coop.model.Order;
import coop.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class ProductsController {
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final RoundDAO roundDAO;

    public ProductsController(ProductDAO productDAO, OrderDAO orderDAO, RoundDAO roundDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.roundDAO = roundDAO;
    }

    @GetMapping("/products/list")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Order> orders = orderDAO.byRound(roundDAO.current().getId());
        List<Product> all = productDAO.findAll();
        List<Category> categories = productDAO.categories();
        Map<String, List<Order>> ordersByProduct = new HashMap<>();
        Map<String, Category> categoryMap = new HashMap<>();
        Map<Category, List<ProductDetails>> productsByCategory = new LinkedHashMap<>();
        for (Category category : categories) {
            categoryMap.put(category.getId(), category);
            productsByCategory.put(category, new ArrayList<>());
        }
        for (Order order : orders) {
            List<Order> ordersForProduct = ordersByProduct.get(order.getProductId());
            if (ordersForProduct == null) {
                ordersForProduct = new ArrayList<>();
                ordersByProduct.put(order.getProductId(), ordersForProduct);
            }
            ordersForProduct.add(order);
        }
        for (Product product : all) {
            Category category = categoryMap.get(product.getCategoryId());
            List<ProductDetails> products = productsByCategory.get(category);
            products.add(new ProductDetails(product, getTotalQuantity(ordersByProduct.get(product.getId()))));
        }

        request.setAttribute("productsByCategory", productsByCategory);
        return "product/list";
    }

    private BigDecimal getTotalQuantity(List<Order> orders) {
        if (orders == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Order order : orders) {
            sum = sum.add(order.getQuantity());
        }
        return sum;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
