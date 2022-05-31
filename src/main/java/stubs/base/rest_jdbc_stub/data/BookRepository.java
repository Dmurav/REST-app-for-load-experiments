package stubs.base.rest_jdbc_stub.data;

import java.util.List;

public interface BookRepository {

    int save (Book book);
    List<Book> findAll();
    Book findById(Long id);

}
