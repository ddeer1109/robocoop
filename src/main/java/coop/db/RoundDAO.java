package coop.db;

import coop.model.Round;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoundDAO {

    private final JdbcTemplate jdbc;

    public RoundDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Round current() {
        String currentRoundId = jdbc.queryForObject("SELECT aktualna_tura FROM spoldzielnia_config", String.class);
        return byId(currentRoundId);
    }

    private Round byId(String id) {
        return jdbc.queryForObject("SELECT * FROM spoldzielnia_tury_zakupow WHERE id = ?",
                (rs, rowNum) -> new Round(rs.getString("id"), rs.getString("nazwa")), id);
    }
}
