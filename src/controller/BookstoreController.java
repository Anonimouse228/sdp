// src/controller/BookstoreController.java
package controller;

import database.DatabaseConnection;
import model.Book;
import view.BookstoreView;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BookstoreController {
    private List<Book> inventory;
    private List<Book> cart;
    private BookstoreView view;
    private Connection dbConnection;

    public BookstoreController(BookstoreView view) {
        this.view = view;
        this.inventory = new ArrayList<>();
        this.cart = new ArrayList<>();

        // Initialize database connection
        this.dbConnection = DatabaseConnection.getInstance().getConnection();

        loadSampleData(); // Temporary method to add sample books
    }

    private void loadSampleData() {
        inventory.add(new Book(1, "Effective Java", "Joshua Bloch", 45.99, 10));
        inventory.add(new Book(2, "Clean Code", "Robert C. Martin", 39.99, 5));
        inventory.add(new Book(3, "Design Patterns", "Gang of Four", 50.00, 0));
    }

    public void displayBooks() {
        view.displayBookList(inventory);
    }

    public void addBookToCart(int bookId) {
        Book book = findBookById(bookId);
        if (book != null && book.getStock() > 0) {
            cart.add(book);
            book.setStock(book.getStock() - 1);
            view.showBookAddedToCart(book.getTitle());
        } else {
            view.showOutOfStockMessage(book != null ? book.getTitle() : "Book not found");
        }
    }

    private Book findBookById(int bookId) {
        for (Book book : inventory) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public void checkout() {
        if (!cart.isEmpty()) {
            view.showCheckoutMessage();
            cart.clear();
        } else {
            System.out.println("Your cart is empty.");
        }
    }
}
