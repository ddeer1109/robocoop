package org.masteukodeu.robocoop.model;

import org.masteukodeu.robocoop.db.*;
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
    private final CategoryDAO categoryDAO;
    private final DeliveryDAO deliveryDAO;

    public CoopService(ProductDAO productDAO, OrderDAO orderDAO, UserDAO userDAO, RoundDAO roundDAO, Clock clock, CategoryDAO categoryDAO, DeliveryDAO deliveryDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
        this.roundDAO = roundDAO;
        this.clock = clock;
        this.categoryDAO = categoryDAO;
        this.deliveryDAO = deliveryDAO;
    }

    public Cart getCartForUserAndRound(String username, String roundId) {
        User user = userDAO.byUsername(username);
        List<Order> orders = orderDAO.byUserAndRound(user.getId(), roundId);
        List<Cart.Item> items = orders.stream().map(o -> new Cart.Item(o.getId(), productDAO.byId(o.getProductId()), o.getQuantity())).collect(Collectors.toList());
        return new Cart(items);
    }

    public Map<Category, List<ProductDetails>> getProductsByCategory() {
        String roundId = roundDAO.current().getId();
        return getOrderedProductsByCategoryForRound(roundId);
    }

    public Map<Category, List<ProductDetails>> getOrderedProductsByCategoryForRound(String roundId) {
        List<Order> orders = orderDAO.byRound(roundId);
        List<Product> all = productDAO.findAll();
        List<Category> categories = categoryDAO.all();
        Map<String, Delivery> deliveries = deliveryDAO.byRound(roundId).stream().collect(Collectors.toMap(d -> d.getProductId(), d -> d));
        Map<String, List<Order>> ordersByProduct = new HashMap<>();
        Map<String, Category> categoryMap = new HashMap<>();
        Map<Category, List<ProductDetails>> productsByCategory = new LinkedHashMap<>();
        for (Category category : categories) {
            if (!category.isHidden()) {
                categoryMap.put(category.getId(), category);
                productsByCategory.put(category, new ArrayList<>());
            }
        }
        for (Order order : orders) {
            List<Order> ordersForProduct = ordersByProduct.computeIfAbsent(order.getProductId(), k -> new ArrayList<>());
            ordersForProduct.add(order);
        }
        for (Product product : all) {
            Category category = categoryMap.get(product.getCategoryId());
            List<ProductDetails> products = productsByCategory.get(category);
            if (products != null) {
                products.add(new ProductDetails(product, getTotalQuantity(ordersByProduct.get(product.getId())), deliveries.get(product.getId())));
            }
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

    public boolean isOrderingBlocked() {
        Round currentRound = roundDAO.current();
        LocalDate finalDate = currentRound.getFinalDate();
        LocalDateTime lastOrderTime = getLastOrderTime(finalDate);
        return !clock.now().isBefore(lastOrderTime);
    }

    private LocalDateTime getLastOrderTime(LocalDate finalDate) {
        // Two days back. 20.00 o'clock
        return finalDate.atStartOfDay().minusDays(1).minusHours(4);
    }

    public boolean isCategoryBlocked(Category category) {
        if (category.getBlockedPeriod() == null) {
            return isOrderingBlocked();
        } else {
            Round currentRound = roundDAO.current();
            LocalDate finalDate = currentRound.getFinalDate();
            LocalDateTime lastOrderTime = finalDate.atStartOfDay().minusHours(category.getBlockedPeriod().longValue());
            return clock.now().isAfter(lastOrderTime);
        }
    }
}
