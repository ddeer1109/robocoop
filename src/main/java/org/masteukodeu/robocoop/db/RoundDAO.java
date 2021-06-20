package org.masteukodeu.robocoop.db;

import org.masteukodeu.robocoop.model.Round;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public interface RoundDAO {
    public Round current();

    public Round byId(String id);

    public List<Round> all();

    public String add(Round round);
}
