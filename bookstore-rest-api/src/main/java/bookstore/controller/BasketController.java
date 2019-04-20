package bookstore.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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

import bookstore.model.Basket;
import bookstore.model.BookItem;
import bookstore.repository.BasketRepository;

@RestController
public class BasketController {
	
	@Autowired
	BasketRepository basketRepository;
	
	private final String API_VERSION = "/v1";

	@GetMapping(API_VERSION + "/baskets")
    public Page<Basket> getAllBaskets(
    		@RequestParam(value="pageNumber", required=false, defaultValue="0") int pageNumber,
    		@RequestParam(value="pageSize", required=false, defaultValue="10") int pageSize
		) {
		return basketRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
	
    @GetMapping(API_VERSION + "/baskets/{id}")
    public Basket getBasket(HttpServletResponse response, @PathVariable Long id) {
    	Optional<Basket> basketOptional = basketRepository.findById(id);
    	if (basketOptional.isPresent()) {
    		return basketOptional.get();
    	} else {
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		return null;
    	}
    }

    @PostMapping(API_VERSION + "/baskets")
    public Basket createBasket(HttpServletResponse response) {
    	Basket basket = basketRepository.save(new Basket());
    	response.setStatus(HttpServletResponse.SC_CREATED);
    	return basket;
    }

    @PutMapping(API_VERSION + "/baskets/{id}/items")
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

    @DeleteMapping(API_VERSION + "/baskets/{id}")
    public void deleteBasket(HttpServletResponse response, @PathVariable Long id) {
    	basketRepository.deleteById(id);
    }
}
