package coop.model;

import coop.db.OrderDAO;
import coop.db.ProductDAO;
import coop.db.RoundDAO;
import coop.db.UserDAO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoopService {

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final RoundDAO roundDAO;

    public CoopService(ProductDAO productDAO, OrderDAO orderDAO, UserDAO userDAO, RoundDAO roundDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.roundDAO = roundDAO;
    }

    public Cart getCartForUserAndRound(String username, String roundId) {
        User user = userDAO.byUsername(username);
        List<Order> orders = orderDAO.byUserAndRound(user.getId(), roundId);
        List<Cart.Item> items = orders.stream().map(o -> new Cart.Item(o.getId(), productDAO.byId(o.getProductId()), o.getQuantity())).collect(Collectors.toList());
        return new Cart(items);
    }

    public Map<Category, List<ProductDetails>> getProductsByCategory() {
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
        return productsByCategory;
    }

    private static BigDecimal getTotalQuantity(List<Order> orders) {
        if (orders == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (Order order : orders) {
            sum = sum.add(order.getQuantity());
        }
        return sum;
    }
}