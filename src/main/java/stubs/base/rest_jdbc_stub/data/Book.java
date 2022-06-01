package stubs.base.rest_jdbc_stub.data;

import lombok.Data;

import java.util.Date;

@Data // Представляет из себя отображение таблицы "Book" в БД
public class Book {

    private Long id; // будет проставляться автоматически в БД при добавлении новой записи
    private Date createdAt; // проставляется в момент сохранения
    private String name;
    private String author;
    private Boolean free;

    // необходим
    public Book () {
    }

    public Book (String name, String author, Boolean free) {
        this.name = name;
        this.author = author;
        this.free = free;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", author=" + author + "]";
    }

}
