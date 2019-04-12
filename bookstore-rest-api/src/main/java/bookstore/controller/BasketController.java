package bookstore.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookstore.model.Basket;
import bookstore.model.BookItem;
import bookstore.repository.BasketRepository;

@RestController
public class BasketController {
	
	@Autowired
	BasketRepository basketRepository;

	@GetMapping("/baskets")
    public List<Basket> getAllBaskets() {
		return (List<Basket>) basketRepository.findAll();
    }
	
    @GetMapping("/baskets/{id}")
    public Basket getBasket(HttpServletResponse response, @PathVariable Long id) {
    	Optional<Basket> basketOptional = basketRepository.findById(id);
    	if (basketOptional.isPresent()) {
    		return basketOptional.get();
    	} else {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return null;
    	}
    }

    @PostMapping("/baskets")
    public void createBasket(HttpServletResponse response) {
    	Basket basket = basketRepository.save(new Basket());
    	response.setStatus(HttpServletResponse.SC_CREATED);
    	response.setHeader("Content-Location", "/baskets/{" + basket.getId() + "}");
    }

    @PutMapping("/baskets/{id}/items")
    public void addBookItemToBasket(HttpServletResponse response, @PathVariable Long id, @RequestBody BookItem bookItem) {
    	Optional<Basket> basketResource = basketRepository.findById(id);
    	
    	if (!basketResource.isPresent()) {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return;
    	}
    	
    	Basket basket = basketResource.get();
    	BookItem bookInBasket = new BookItem();
    	bookInBasket.setBookId(bookItem.getBookId());
    	bookInBasket.setQuantity(bookItem.getQuantity());
    	basket.addBooksToBasket(bookInBasket);
    	
    	basketRepository.save(basket);
    }

    @DeleteMapping("/baskets/{id}")
    public void deleteBasket(HttpServletResponse response, @PathVariable Long id) {
    	// TODO
    }
}
