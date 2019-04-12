package bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bookstore.model.Book;
import bookstore.repository.BookRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		Book book1 = new Book(null, "Harry Potter and the Philosopher's Stone", "J. K. Rowling", 9.9);
		book1.addComment("This book is great, love it!");
		bookRepository.save(book1);
		bookRepository.save(new Book(null, "Harry Potter and the Chamber of Secrets", "J. K. Rowling", 14.9));
	}
}
