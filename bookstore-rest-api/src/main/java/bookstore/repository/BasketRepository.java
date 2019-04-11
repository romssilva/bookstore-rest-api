package bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import bookstore.model.Basket;

public interface BasketRepository extends CrudRepository<Basket, Long> { }
