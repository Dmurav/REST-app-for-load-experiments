package stubs.base.rest_jdbc_stub.data;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class JdbcBookRepository implements BookRepository {

    private JdbcTemplate jdbc;

    public JdbcBookRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int save(Book book) {
        book.setCreatedAt(new Date());
        return jdbc.update("INSERT INTO Book (createdAt, name, author) VALUES(?,?,?)",
                new Object[] { book.getCreatedAt(), book.getName(), book.getAuthor()});
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT * FROM Book", BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public Book findById(Long id) {
        try {
            Book book = jdbc.queryForObject(("SELECT * FROM Book WHERE id=?"),
            BeanPropertyRowMapper.newInstance(Book.class), id);
            return book;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
