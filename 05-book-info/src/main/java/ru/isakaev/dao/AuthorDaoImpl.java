package ru.isakaev.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.isakaev.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("SELECT id, name FROM author", new AuthorMapper());
    }

    @Override
    public Author getById(int id) {
        return jdbcOperations.queryForObject("SELECT id, name FROM author WHERE id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public Author save(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());

        KeyHolder kh = new GeneratedKeyHolder();
        jdbcOperations.update("INSERT INTO author(name) VALUES(:name)", params, kh);
        int id = kh.getKey().intValue();
        return new Author(id, author.getName());
    }

    @Override
    public void deleteById(int id) {
        jdbcOperations.update("DELETE FROM author WHERE id = :id", Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            return new Author(id, name);
        }
    }
}
