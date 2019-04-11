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

import bookstore.model.Basket;
import bookstore.model.Book;
import bookstore.model.BookInBasket;
import bookstore.repository.BasketRepository;

public class BasketController {
	
	@Autowired
	BasketRepository basketRepository;

	@GetMapping("/baskets")
    public List<Basket> getAllBaskets() {
		return (List<Basket>) basketRepository.findAll();
    }
	
    @GetMapping("/baskets/{id}")
    public Optional<Basket> getBasket(@PathVariable Long id) {
    	return basketRepository.findById(id);
    }

    @PostMapping("/baskets")
    public void addBasket(HttpServletResponse response) {
    	Basket basket = basketRepository.save(new Basket());
    	response.setStatus(HttpServletResponse.SC_CREATED);
    	response.setHeader("Content-Location", "/baskets/{" + basket.getId() + "}");
    }

    @PostMapping("/baskets/{id}/books")
    public void addBookToBasket(@PathVariable Long id, HttpServletResponse response, @RequestBody BookInBasket bookToAddToBasket) {
    	Optional<Basket> basketResource = basketRepository.findById(id);
    	
    	if (!basketResource.isPresent()) {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return;
    	}
    	
    	Basket basket = basketResource.get();
    	BookInBasket bookInBasket = new BookInBasket();
    	bookInBasket.setBook(bookToAddToBasket.getBook());
    	bookInBasket.setQuantity(bookToAddToBasket.getQuantity());
    	basket.addBooksToBasket(bookInBasket);
    	
    	basketRepository.save(basket);
    }
}
