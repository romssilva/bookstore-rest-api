package bookstore.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookstore.model.Book;
import bookstore.repository.BookRepository;

@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	private final String API_VERSION = "/v1";

	@GetMapping(API_VERSION + "/books")
    public Page<Book> getAllBooks(
    		@RequestParam(value="title", required=false, defaultValue="") String title,
    		@RequestParam(value="author", required=false, defaultValue="") String author,
    		@RequestParam(value="pageNumber", required=false, defaultValue="0") int pageNumber,
    		@RequestParam(value="pageSize", required=false, defaultValue="10") int pageSize
		) {
		
		return bookRepository.findByTitleContainingAndAuthorContainingAllIgnoreCase(title, author, PageRequest.of(pageNumber, pageSize));
    }
	
    @GetMapping(API_VERSION + "/books/{id}")
    public Book getBook(HttpServletResponse response, @PathVariable Long id) {
    	Optional<Book> bookOptional = bookRepository.findById(id);
    	if (bookOptional.isPresent()) {
    		return bookOptional.get();
    	} else {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return null;
    	}
    }

    @PostMapping(API_VERSION + "/books")
    public Book addBook(@RequestBody Book newBook, HttpServletResponse response) {
		Book book = bookRepository.save(newBook);
		response.setStatus(HttpServletResponse.SC_CREATED);
    	return book;
    }
    
    @PutMapping("books/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody Book updatedBook, HttpServletResponse response) {
    	Book newBook = new Book();
    	BeanUtils.copyProperties(updatedBook, newBook);
    	
    	if (!bookRepository.existsById(id)) {
    		response.setStatus(HttpServletResponse.SC_CREATED);
    	}

    	bookRepository.save(newBook);
	}
    
    @PostMapping("books/{id}/comments")
    public void addBookComment(@PathVariable Long id, @RequestBody String comment, HttpServletResponse response) {
    	Optional<Book> bookResource = bookRepository.findById(id);
    	
    	if (!bookResource.isPresent()) {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return;
    	}
    	
    	Book book = bookResource.get();
    	book.addComment(comment);
    	bookRepository.save(book);
	}
    
    @DeleteMapping(API_VERSION + "/books/{id}")
    public void deleteBook(@PathVariable Long id) {
    	Book book = bookRepository.findById(id).get();
    	bookRepository.delete(book);
    }
  
}
