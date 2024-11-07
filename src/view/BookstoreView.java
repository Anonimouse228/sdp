package view;

import model.Book;
import java.util.List;

public class BookstoreView {

    public void displayBookList(List<Book> books) {
        System.out.println("Book List:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void showBookAddedToCart(String title) {
        System.out.println("Book '" + title + "' has been added to your cart.");
    }

    public void showCheckoutMessage() {
        System.out.println("Proceeding to checkout...");
    }

    public void showOutOfStockMessage(String title) {
        System.out.println("Sorry, the book '" + title + "' is out of stock.");
    }
}
