package bookstore.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/books")
    public List<Book> getAllBooks(@RequestParam(value="title", required=false) String title, @RequestParam(value="author", required=false) String author, @RequestParam(value="minPrice", required=false) Double minPrice, @RequestParam(value="maxPrice", required=false) Double maxPrice) {

		if (title == null) title = "";
		if (author == null) author = "";
		if (minPrice == null) minPrice = (double) 0;
		if (maxPrice == null) maxPrice = (double) 1000;
		
		return (List<Book>) bookRepository.filterAll(title, author, minPrice, maxPrice);
    }
	
    @GetMapping("/books/{id}")
    public Book getBook(HttpServletResponse response, @PathVariable Long id) {
    	Optional<Book> bookOptional = bookRepository.findById(id);
    	if (bookOptional.isPresent()) {
    		return bookOptional.get();
    	} else {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return null;
    	}
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book newBook) {
    	return bookRepository.save(newBook);
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
    
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
    	Book book = bookRepository.findById(id).get();
    	bookRepository.delete(book);
    }
  
}
