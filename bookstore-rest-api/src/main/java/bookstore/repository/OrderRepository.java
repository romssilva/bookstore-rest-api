package bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import bookstore.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> { }