package coop.model;

import coop.db.OrderDAO;
import coop.db.ProductDAO;
import coop.db.UserDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoopService {

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public CoopService(ProductDAO productDAO, OrderDAO orderDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
    }

    public Cart getCartForUserAndRound(String username, String roundId) {
        User user = userDAO.byUsername(username);
        List<Order> orders = orderDAO.byUserAndRound(user.getId(), roundId);
        List<Cart.Item> items = orders.stream().map(o -> new Cart.Item(o.getId(), productDAO.byId(o.getProductId()), o.getQuantity())).collect(Collectors.toList());
        return new Cart(items);
    }
}
