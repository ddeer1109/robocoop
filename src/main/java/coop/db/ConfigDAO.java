package coop.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigDAO {

    private final JdbcTemplate jdbc;

    public ConfigDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void setCurrentRound(String roundId) {
        jdbc.update("UPDATE spoldzielnia_config SET aktualna_tura = ?", roundId);
    }
}