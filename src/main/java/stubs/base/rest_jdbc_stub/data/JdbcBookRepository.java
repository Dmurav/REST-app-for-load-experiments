package stubs.base.rest_jdbc_stub.data;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository // Репозиторий, класс для работы с БД
public class JdbcBookRepository implements BookRepository {
    // внедряем зависимость
    private JdbcTemplate jdbc;

    public JdbcBookRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int save(Book book) {
        book.setCreatedAt(new Date()); // устанавливаем дату в момент сохранения
        return jdbc.update("INSERT INTO book (createdAt, name, author, free) VALUES(?,?,?,?)",
                new Object[] { book.getCreatedAt(), book.getName(), book.getAuthor(), book.getFree()});
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT * FROM book", BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public Book findById(Long id) {
        try {
            Book book = jdbc.queryForObject(("SELECT * FROM book WHERE id=?"),
            BeanPropertyRowMapper.newInstance(Book.class), id);
            return book;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int update(Book book) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return jdbc.update("DELETE FROM book WHERE id=?", id);
    }

    @Override
    public List<Book> findByUse(boolean free) {
        return jdbc.query("SELECT * from book WHERE free=?",
                BeanPropertyRowMapper.newInstance(Book.class), free);
    }

    @Override
    public List<Book> findByNameContaining(String name) {
        String q = "SELECT * from book WHERE name LIKE '%" + name + "%'";
        return jdbc.query(q, BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public int deleteAll() {
        return jdbc.update("DELETE from book");
    }
}
