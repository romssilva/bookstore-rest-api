package bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import bookstore.model.Book;

public interface BooksRepository extends CrudRepository<Book, Long> {

}
