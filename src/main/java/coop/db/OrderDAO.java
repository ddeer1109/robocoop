package coop.db;

import coop.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Order> byUserAndRound(String userId, String roundId) {
        return jdbcTemplate.query("SELECT * FROM spoldzielnia_zamowienia WHERE id_usera = ? AND id_tury = ?",
                getOrderRowMapper(), userId, roundId);
    }

    public List<Order> byRound(String roundId) {
        return jdbcTemplate.query("SELECT * FROM spoldzielnia_zamowienia WHERE id_tury = ?",
                getOrderRowMapper(), roundId);
    }

    private RowMapper<Order> getOrderRowMapper() {
        return (rs, rowNum) -> new Order(
                rs.getString("id_produktu"),
                rs.getString("id_tury"),
                rs.getString("id_usera"),
                rs.getBigDecimal("ilosc")
        );
    }
}
