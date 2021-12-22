package ru.isakaev.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.isakaev.model.Author;
import ru.isakaev.model.Book;
import ru.isakaev.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
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
        return jdbcOperations.query("SELECT b.id AS book_id, b.name AS book_name , " +
                                            "a.id AS author_id, a.name AS author_name, " +
                                            "g.id AS genre_id, g.name AS genre_name " +
                                            "FROM book b " +
                                            "JOIN  author a ON b.author = a.id " +
                                            "JOIN genre g ON b.genre = g.id", new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("book_id");
                String name = rs.getString("book_name");
                Author author = new Author(rs.getInt("author_id"), rs.getString("author_name"));
                Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
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
    public Book save(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValues(Map.of("name", book.getTitle(), "author", book.getAuthor().getId(), "genre", book.getGenre().getId()));

        KeyHolder kh = new GeneratedKeyHolder();
        jdbcOperations.update("INSERT INTO book(name, author, genre) VALUES(:name, :author, :genre)", params, kh);
        book.setId(kh.getKey().intValue());
        return book;
    }

    @Override
    public void deleteById(int id) {
        jdbcOperations.update("DELETE FROM book WHERE id = :id", Map.of("id", id));
    }
}
