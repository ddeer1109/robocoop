package org.masteukodeu.robocoop.db;

import org.masteukodeu.robocoop.model.Delivery;
import org.masteukodeu.robocoop.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class DeliveryDAO {

    private final JdbcTemplate jdbcTemplate;

    public DeliveryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(String roundId, String productId, BigDecimal price, BigDecimal amount) {
        jdbcTemplate.update("INSERT INTO dostawy" +
                        " (id_tury, id_produktu, cena, ilosc) VALUES (?, ?, ?, ?)",
                roundId, productId, price, amount);
    }

    public void update(String roundId, String productId, BigDecimal price, BigDecimal amount) {
        jdbcTemplate.update("UPDATE dostawy SET cena = ?, ilosc = ? WHERE id_tury = ? AND id_produktu = ?",
                price, amount, roundId, productId);
    }

    public boolean exists(String roundId, String productId) {
        return !jdbcTemplate.queryForList("SELECT * FROM dostawy WHERE id_tury = ? AND id_produktu = ?", roundId, productId).isEmpty();
    }

    public List<Delivery> byRound(String roundId) {
        return jdbcTemplate.query("SELECT * FROM dostawy WHERE id_tury = ?",
                getRowMapper(), roundId);
    }

    private static RowMapper<Delivery> getRowMapper() {
        return (rs, rowNum) -> new Delivery(
                rs.getString("id_tury"),
                rs.getString("id_produktu"),
                rs.getBigDecimal("cena"),
                rs.getBigDecimal("ilosc")
        );
    }
}
