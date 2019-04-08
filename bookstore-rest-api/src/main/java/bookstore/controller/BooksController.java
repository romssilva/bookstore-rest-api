package bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookstore.model.Book;
import bookstore.repository.BooksRepository;

@RestController
public class BooksController {
	
	@Autowired
	BooksRepository booksRepository;


    @GetMapping("/books")
    public List<Book> all() {
    	return (List<Book>) booksRepository.findAll();
    }

    @PostMapping("/books")
    public Book add(@RequestBody Book newBook) {
    	return booksRepository.save(newBook);
    }
}
