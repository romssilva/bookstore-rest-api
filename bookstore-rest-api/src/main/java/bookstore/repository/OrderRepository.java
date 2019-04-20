package bookstore.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import bookstore.model.Order;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> { }