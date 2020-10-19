package coop.db;


import coop.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO {

    private JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM spoldzielnia_userzy", this::mapUser);
    }

    private User mapUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setUsername(rs.getString("email"));
        user.setPassword(rs.getString("haslo"));
        return user;
    }

    public User byUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM spoldzielnia_userzy WHERE email = ?", this::mapUser, username);
    }
}