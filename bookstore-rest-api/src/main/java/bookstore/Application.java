package bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bookstore.model.Book;
import bookstore.repository.BooksRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	BooksRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		bookRepository.save(new Book(null, "Harry Potter and the Philosopher's Stone", "J. K. Rowling", 9.9));
	}
}
