package model;

import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private List<Book> books;
    private String orderDate;

    public Order(int id, int customerId, List<Book> books, String orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.books = books;
        this.orderDate = orderDate;
    }

    public Order(int customerId, List<Book> books) {
        this.customerId = customerId;
        this.books = books;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
}
