package stubs.base.rest_jdbc_stub.data;

import lombok.Data;

import java.util.Date;

@Data
public class Book {

    private Long id;
    private Date createdAt;
    private String name;
    private String author;

    public Book () {
    }

    public Book (String name, String author) {
        this.name = name;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", author=" + author + "]";
    }

}
