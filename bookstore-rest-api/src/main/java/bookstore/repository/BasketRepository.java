package bookstore.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import bookstore.model.Basket;

public interface BasketRepository extends PagingAndSortingRepository<Basket, Long> { }
