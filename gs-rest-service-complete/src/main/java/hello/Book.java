package hello;

import java.util.ArrayList;

public class Book {

    private long id;
    private String title;
    private String author;
    private double price;
    private ArrayList<String> comments;

    public Book(long id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.comments = new ArrayList<String>();
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void addComments(String comment) {
		this.comments.add(comment);
	}

	public long getId() {
		return id;
	}
	
}
