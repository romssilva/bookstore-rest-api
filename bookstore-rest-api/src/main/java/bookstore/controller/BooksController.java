package bookstore.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookstore.model.Book;
import bookstore.repository.BooksRepository;

@RestController
public class BooksController {
	
	@Autowired
	BooksRepository booksRepository;

	@GetMapping("/books")
    public List<Book> getAllBooks(@RequestParam(value="title", required=false) String title, @RequestParam(value="author", required=false) String author, @RequestParam(value="minPrice", required=false) Double minPrice, @RequestParam(value="maxPrice", required=false) Double maxPrice) {

		if (title == null) title = "";
		if (author == null) author = "";
		if (minPrice == null) minPrice = (double) 0;
		if (maxPrice == null) maxPrice = (double) 1000;
		
		return (List<Book>) booksRepository.filterAll(title, author, minPrice, maxPrice);
    }
	
    @GetMapping("/books/{id}")
    public Optional<Book> getBook(@PathVariable Long id) {
    	return booksRepository.findById(id);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book newBook) {
    	return booksRepository.save(newBook);
    }
    
    @PutMapping("books/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody Book updatedBook, HttpServletResponse response) {
    	Book newBook = new Book();
    	BeanUtils.copyProperties(updatedBook, newBook);
    	
    	if (!booksRepository.existsById(id)) {
    		response.setStatus(HttpServletResponse.SC_CREATED);
    	}

    	booksRepository.save(newBook);
	}
    
    @PostMapping("books/{id}/comments")
    public void addBookComment(@PathVariable Long id, @RequestBody String comment, HttpServletResponse response) {
    	Optional<Book> bookResource = booksRepository.findById(id);
    	
    	if (!bookResource.isPresent()) {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return;
    	}
    	
    	Book book = bookResource.get();
    	book.addComment(comment);
    	booksRepository.save(book);
	}
    
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
    	Book book = booksRepository.findById(id).get();
    	booksRepository.delete(book);
    }
  
}
