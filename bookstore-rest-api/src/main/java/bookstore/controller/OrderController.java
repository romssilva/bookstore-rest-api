package bookstore.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bookstore.model.Order;
import bookstore.repository.OrderRepository;

@RestController
public class OrderController {
	
	@Autowired
	OrderRepository orderRepository;
	
	private final String API_VERSION = "/v1";

	@GetMapping(API_VERSION + "/orders")
    public Page<Order> getAllOrders(
    		@RequestParam(value="pageNumber", required=false, defaultValue="0") int pageNumber,
    		@RequestParam(value="pageSize", required=false, defaultValue="10") int pageSize
		) {
		return orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
	
	@GetMapping(API_VERSION + "/orders/{id}")
    public Order getOrder(@PathVariable Long id, HttpServletResponse response) {
		Optional<Order> order = orderRepository.findById(id);
		
		if (order.isPresent()) {
			return order.get();
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
    }
	
	@PostMapping(API_VERSION + "/orders")
    public Order createOrder(@RequestBody Order newAuditing, HttpServletResponse response) {
		Order order = orderRepository.save(newAuditing);
		response.setStatus(HttpServletResponse.SC_CREATED);
		return order;
    }
	
	@PutMapping(API_VERSION + "/orders/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order newAuditing, HttpServletResponse response) {
		if (!orderRepository.existsById(id)) {
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		return orderRepository.save(newAuditing);
    }
}
