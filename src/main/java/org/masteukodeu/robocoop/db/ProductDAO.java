package org.masteukodeu.robocoop.db;


import org.masteukodeu.robocoop.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {

    private final JdbcTemplate jdbcTemplate;

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM spoldzielnia_produkty", mapProduct());
    }

    private RowMapper<Product> mapProduct() {
        return (rs, rowNum) -> new Product(
                rs.getString("id"),
                rs.getString("nazwa"),
                rs.getBigDecimal("cena_za_jednostke"),
                rs.getString("jednostka"),
                rs.getString("kategoria"),
                rs.getInt("ilosc_rozliczeniowa"));
    }

    public Product byId(String productId) {
        return jdbcTemplate.queryForObject("SELECT * FROM spoldzielnia_produkty WHERE id = ?", mapProduct(), productId);
    }

}