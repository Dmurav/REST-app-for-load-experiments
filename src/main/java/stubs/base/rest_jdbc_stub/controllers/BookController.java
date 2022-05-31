package stubs.base.rest_jdbc_stub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stubs.base.rest_jdbc_stub.data.Book;
import stubs.base.rest_jdbc_stub.data.BookRepository;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/")
public class BookController {

    private BookRepository bookRepo;

    @Autowired
    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @PostMapping("/api/book/add")
    public int addBook(@RequestBody Book book){
        int id = bookRepo.save(new Book(book.getName(), book.getAuthor()));
        return id;
    }

    @GetMapping("/api/book/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") long id){
        Book book = bookRepo.findById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/book/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = new ArrayList<>();
        try {
            bookRepo.findAll().forEach(books::add);
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
