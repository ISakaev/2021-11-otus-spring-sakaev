package ru.isakaev.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.isakaev.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query("SELECT id, name FROM genre", new GenreMapper());
    }

    @Override
    public Genre getById(int id) {
        return jdbcOperations.queryForObject("SELECT id, name FROM genre WHERE id = :id",
                Map.of("id", id), new GenreMapper());
    }
    @Override
    public Genre save(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());

        KeyHolder kh = new GeneratedKeyHolder();
        jdbcOperations.update("INSERT INTO genre(name) VALUES(:name)", params, kh);

        int id = kh.getKey().intValue();
        return new Genre(id, genre.getName());
    }

    @Override
    public void deleteById(int id) {
        jdbcOperations.update("DELETE FROM genre WHERE id = :id", Map.of("id", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
