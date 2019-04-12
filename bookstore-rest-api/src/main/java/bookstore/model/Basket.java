package bookstore.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "baskets")
public class Basket {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private ArrayList<BookItem> booksInBasket;

    public Basket() {}

	public Long getId() {
		return id;
	}

	public ArrayList<BookItem> getBooksInBasket() {
		return booksInBasket;
	}

	public void setBooksInBasket(ArrayList<BookItem> booksInBasket) {
		this.booksInBasket = booksInBasket;
	}

	public void addBooksToBasket(BookItem bookInBasket) {
		this.booksInBasket.add(bookInBasket);
	}
}
