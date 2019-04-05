package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/books")
    public Book all() {
        return new Book(counter.incrementAndGet(), "Harry Potter", "JK", 9.9);
    }

    @PostMapping("/books")
    public Book greeting() {
        return new Book(counter.incrementAndGet(), "Harry Potter", "JK", 9.9);
    }
}
