package model;

import java.util.List;

public class OrderImpl implements Order {
    private List<Book> books;

    public OrderImpl(List<Book> books) {
        this.books = books;
    }

    @Override
    public double calculateTotalPrice() {
        return books.stream().mapToDouble(Book::getPrice).sum();
    }

    @Override
    public String getDescription() {
        return "Standard Order with " + books.size() + " books.";
    }
}
