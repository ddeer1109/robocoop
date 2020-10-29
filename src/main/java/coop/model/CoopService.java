package coop.model;

import coop.db.OrderDAO;
import coop.db.ProductDAO;
import coop.db.RoundDAO;
import coop.db.UserDAO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoopService {

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final RoundDAO roundDAO;
    private final Clock clock;

    public CoopService(ProductDAO productDAO, OrderDAO orderDAO, UserDAO userDAO, RoundDAO roundDAO, Clock clock) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.roundDAO = roundDAO;
        this.clock = clock;
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
            List<Order> ordersForProduct = ordersByProduct.computeIfAbsent(order.getProductId(), k -> new ArrayList<>());
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

    public boolean isOrderingAllowed() {
        Round currentRound = roundDAO.current();
        LocalDate finalDate = currentRound.getFinalDate();
        LocalDateTime lastOrderTime = getLastOrderTime(finalDate);
        return clock.now().isBefore(lastOrderTime);
    }

    private LocalDateTime getLastOrderTime(LocalDate finalDate) {
        // Two days back. 20.00 o'clock
        return finalDate.atStartOfDay().minusDays(1).minusHours(4);
    }
}
