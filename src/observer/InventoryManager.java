package observer;

import model.Book;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager implements Subject {
    private List<Observer> observers;
    private List<Book> books;

    public InventoryManager() {
        this.observers = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        notifyObservers("New book added: " + book.getTitle());
    }

    public void updateStock(Book book, int newStock) {
        book.setStock(newStock);
        notifyObservers("Stock updated for " + book.getTitle() + ": New stock is " + newStock);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
