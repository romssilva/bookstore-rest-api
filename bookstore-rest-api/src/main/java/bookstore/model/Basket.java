package bookstore.model;

import java.util.ArrayList;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Basket {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private ArrayList<BookInBasket> booksInBasket;

    public Basket() {}

	public Long getId() {
		return id;
	}

	public ArrayList<BookInBasket> getBooksInBasket() {
		return booksInBasket;
	}

	public void setBooksInBasket(ArrayList<BookInBasket> booksInBasket) {
		this.booksInBasket = booksInBasket;
	}

	public void addBooksToBasket(BookInBasket bookInBasket) {
		this.booksInBasket.add(bookInBasket);
	}
	
	public double getBasketTotal() {
		double basketTotal = 0;
		
		for (int i = 0; i < this.booksInBasket.size(); i++) {
			basketTotal += this.booksInBasket.get(i).getBook().getPrice();
		}
		
		return basketTotal;
	}
}
