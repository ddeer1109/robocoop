package coop.db;

import coop.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAO {

    private JdbcTemplate jdbcTemplate;

    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Order order) {
        jdbcTemplate.update("INSERT INTO coop_old.spoldzielnia_zamowienia" +
                " (id_produktu, id_tury, id_usera, ilosc) VALUES (?, ?, ?, ?)",
                order.getProductId(), order.getRoundId(), order.getUserId(), order.getQuantity());
    }
}
