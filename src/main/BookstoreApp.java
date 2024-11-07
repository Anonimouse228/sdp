// src/main/BookstoreApp.java
package main;

import controller.BookstoreController;
import model.Book;
import service.BookstoreService;
import view.BookstoreView;

import java.sql.SQLException;

public class BookstoreApp {
    public static void main(String[] args) throws SQLException {
        BookstoreView view = new BookstoreView();
        BookstoreController controller = new BookstoreController(view);

        controller.displayBooks();
        controller.addBookToCart(1);
        controller.addBookToCart(3);
        controller.checkout();
        BookstoreService bookstoreService = new BookstoreService();

        Book book1 = new Book(1, "Java Programming", "Author X", 29.99, 50);
        bookstoreService.addBook(book1);

        bookstoreService.updateBookStock(book1, 45);

        System.out.println("Attempting to create another DatabaseConnection instance...");
        controller = new BookstoreController(view);
    }
}
