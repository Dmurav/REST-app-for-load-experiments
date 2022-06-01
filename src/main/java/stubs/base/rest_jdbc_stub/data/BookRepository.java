package stubs.base.rest_jdbc_stub.data;

import java.util.List;

// описывает основные методы репозитория
public interface BookRepository {

    // сохранить книгу в схему Book БД
    int save (Book book);
    // получить все книги из схемы Book БД
    List<Book> findAll();
    // получить одну книгу из схемы Book БД по id
    Book findById(Long id);
    // обновить инфу по книге
    int update(Book book);
    // удалить книгу по id
    int deleteById(Long id);
    // найти все свободные книги
    List<Book> findByUse(boolean free);
    // найти по слову в названии
    List<Book> findByNameContaining(String name);
    int deleteAll();



}
