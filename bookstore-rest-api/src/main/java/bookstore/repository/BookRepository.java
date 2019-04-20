package bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import bookstore.model.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
	
	public Page<Book> findByTitleContainingAndAuthorContainingAllIgnoreCase(String title, String author, Pageable pageable);
}
