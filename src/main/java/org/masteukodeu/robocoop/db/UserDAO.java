package org.masteukodeu.robocoop.db;


import org.masteukodeu.robocoop.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM spoldzielnia_userzy", this::mapUser);
    }

    private User mapUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getString("id"),
                rs.getString("email"),
                rs.getString("email"),
                rs.getString("haslo"),
                rs.getBoolean("admin"),
                rs.getString("nazwisko"),
                rs.getString("telefon"),
                rs.getBoolean("czy_prawko"));
    }

    public User byUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM spoldzielnia_userzy WHERE email = ?", this::mapUser, username);
    }

    public User byId(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM spoldzielnia_userzy WHERE id = ?", this::mapUser, id);
    }
}