package stubs.base.rest_jdbc_stub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stubs.base.rest_jdbc_stub.data.Book;
import stubs.base.rest_jdbc_stub.data.BookRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081") // Совместное использование ресурсов между источниками
@RestController // Позволяет приложению обрабатывать REST-запросы (JSON)
@RequestMapping("/")
public class BookController {
    // внедряем зависимость - репозиторий
    private BookRepository bookRepo;

    @Autowired
    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    // Сохраняем книгу в БД
    @PostMapping("/api/v1/book/add")
    public int addBook(@RequestBody Book book){ // Получаем JSON объект из body запроса
        int id = bookRepo.save(new Book(book.getName(), book.getAuthor(), book.getFree()));
        return id;
    }

    // Получаем книгу по id
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") long id){ // Получаем id из контекста
        Book book = bookRepo.findById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Получить все книги или книги по названию /api/v1/book?name={name}
    @GetMapping("/api/v1/book")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String name){
        List<Book> books = new ArrayList<>();
        try {
            if(name == null) {
                bookRepo.findAll().forEach(books::add);
            } else {
                bookRepo.findByNameContaining(name).forEach(books::add);
            }

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Обновить книгу по id
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        Book _book = bookRepo.findById(id);
        if (_book != null) {
            _book.setId(id);
            _book.setName(book.getName());
            _book.setAuthor(book.getAuthor());
            _book.setCreatedAt(book.getCreatedAt());
            _book.setFree(book.getFree());
            bookRepo.update(_book);
            return new ResponseEntity<>("Book was updated successfully.", HttpStatus.OK);
        } else {
            return  new ResponseEntity<>("Cannot find book with id=", HttpStatus.NOT_FOUND);
        }
    }

    // Удалить книгу по id
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        try {
            int result = bookRepo.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find book with id=" + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Book was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete book.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Получить все книги по наличию
    @GetMapping("/api/v1/book/free")
    public ResponseEntity<List<Book>> findByFree() {
        try {
            List<Book> books = bookRepo.findByUse(true);
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Удалить все книги
    @DeleteMapping("/api/v1/book")
    public ResponseEntity<String> deleteAll() {
        try {
            int numRows = bookRepo.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Book(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete books.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
