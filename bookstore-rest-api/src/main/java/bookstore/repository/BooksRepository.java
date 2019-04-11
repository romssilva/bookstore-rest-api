package bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import bookstore.model.Book;

public interface BooksRepository extends CrudRepository<Book, Long> {

	@Query("FROM Book b WHERE UPPER(b.title) LIKE CONCAT('%',UPPER(:title),'%') and UPPER(b.author) LIKE CONCAT('%',UPPER(:author),'%') and b.price > :minPrice and b.price < :maxPrice")
	public List<Book> filterAll(String title, String author, Double minPrice, Double maxPrice);
	
	@Query(nativeQuery = true, value = "SELECT Top 1 * FROM Book ORDER BY price DESC")
	public Book findMostExpensive();
	
	
}
