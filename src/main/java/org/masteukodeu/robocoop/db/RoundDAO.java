package org.masteukodeu.robocoop.db;

import org.masteukodeu.robocoop.model.Round;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RoundDAO {
    int daysDelta = 0;

    private final JdbcTemplate jdbc;

    public RoundDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    public RoundDAO(){ this.jdbc = null; }
    public RoundDAO(int daysDelta){
        this.jdbc = null;
        this.daysDelta = daysDelta;
    }

    public Round current() {
        if(jdbc != null) {
            String currentRoundId = jdbc.queryForObject("SELECT aktualna_tura FROM spoldzielnia_config", String.class);
            return byId(currentRoundId);
        }
        else {
            return new Round("1", "test", LocalDate.now().plusDays(daysDelta));

        }
    }

    public Round byId(String id) {
        return jdbc.queryForObject("SELECT * FROM spoldzielnia_tury_zakupow WHERE id = ?",
                getRoundRowMapper(), id);
    }

    public List<Round> all() {
        return jdbc.query("SELECT * FROM spoldzielnia_tury_zakupow ORDER BY id DESC", getRoundRowMapper());
    }

    private RowMapper<Round> getRoundRowMapper() {
        return (rs, rowNum) -> {
            Date finalDateSQL = rs.getDate("data_zakonczenia");
            LocalDate finalDate;
            if (finalDateSQL != null) {
                finalDate = finalDateSQL.toLocalDate();
            } else {
                finalDate = null;
            }
            return new Round(rs.getString("id"), rs.getString("nazwa"), finalDate);
        };
    }

    public String add(Round round) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                connection -> {
                    PreparedStatement ps = connection
                            .prepareStatement("INSERT INTO spoldzielnia_tury_zakupow (nazwa, data_zakonczenia) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, round.getName());
                    ps.setDate(2, Date.valueOf(round.getFinalDate()));
                    return ps;
                }, keyHolder);
        return keyHolder.getKey().toString();
    }
}
