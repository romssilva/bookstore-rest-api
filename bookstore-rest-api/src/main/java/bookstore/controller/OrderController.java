package bookstore.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bookstore.model.Book;
import bookstore.model.Order;
import bookstore.repository.OrderRepository;

@RestController
public class OrderController {
	
	@Autowired
	OrderRepository orderRepository;

	@GetMapping("/orders")
    public List<Order> getAllOrders() {
		return (List<Order>) orderRepository.findAll();
    }
	
	@GetMapping("/orders/{id}")
    public Order getOrder(HttpServletResponse response, @PathVariable Long id) {
		// TODO
		return null;
    }
	
	@PostMapping("/orders")
    public Order createOrder() {
		// TODO
		return null;
    }
	
	@PutMapping("/orders/{id}")
    public Order updateOrder(HttpServletResponse response, @PathVariable Long id, @RequestBody Order order) {
		// TODO
		return null;
    }
}
