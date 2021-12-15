package ru.isakaev.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookDaoImpl(NamedParameterJdbcOperations jdbcOperations, AuthorDao authorDao, GenreDao genreDao) {
        this.jdbcOperations = jdbcOperations;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query("SELECT id, name, author, genre FROM book", new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Author author = authorDao.getById(rs.getInt("author"));
                Genre genre = genreDao.getById(rs.getInt("genre"));
                return new Book(id, name, author, genre);
            }
        });
    }

    @Override
    public Book getById(int id) {
        return jdbcOperations.queryForObject("SELECT id, name, author, genre FROM book WHERE id = :id",
                Map.of("id", id),
                new RowMapper<Book>() {
                    @Override
                    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        Author author = authorDao.getById(rs.getInt("author"));
                        Genre genre = genreDao.getById(rs.getInt("genre"));
                        return new Book(id, name, author, genre);
                    }
                });
    }

    @Override
    public void save(Book book) {
        jdbcOperations.update("INSERT INTO book(id, name, author, genre) VALUES(:id, :name, :author, :gener)",
                Map.of("id", book.getId(), "name", book.getName(), "author",
                        authorDao.save(book.getAuthor()), "gener", genreDao.save(book.getGenre())));

    }

    @Override
    public void deleteById(int id) {

    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            return new Book(id, name);
        }
    }
}
