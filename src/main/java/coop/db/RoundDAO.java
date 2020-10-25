package coop.db;

import coop.model.Order;
import coop.model.Round;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public Round byId(String id) {
        return jdbc.queryForObject("SELECT * FROM spoldzielnia_tury_zakupow WHERE id = ?",
                getRoundRowMapper(), id);
    }

    public List<Round> all() {
        return jdbc.query("SELECT * FROM spoldzielnia_tury_zakupow ORDER BY id DESC", getRoundRowMapper());
    }

    private RowMapper<Round> getRoundRowMapper() {
        return (rs, rowNum) -> new Round(rs.getString("id"), rs.getString("nazwa"));
    }

    public void add(Round round) {
        jdbc.update("INSERT INTO spoldzielnia_tury_zakupow (nazwa) VALUES (?)",
               round.getName());
    }
}
