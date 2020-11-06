package org.masteukodeu.robocoop.db;

import org.masteukodeu.robocoop.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDAO {

    private final JdbcTemplate jdbc;

    public CategoryDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Category> all() {
        return jdbc.query("SELECT * FROM spoldzielnia_kategorie ORDER BY id",
                CategoryDAO::mapRow);
    }

    public Category byId(String id) {
        return jdbc.queryForObject("SELECT * FROM spoldzielnia_kategorie WHERE id = ?",
                CategoryDAO::mapRow, id);
    }

    private static Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Category(rs.getString("id"), rs.getString("nazwa"), rs.getBoolean("ukryta"), rs.getBigDecimal("okres_blokowania_w_godzinach"));
    }
}
