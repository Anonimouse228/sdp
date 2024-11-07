package service;

import database.DatabaseConnection;
import model.Book;
import model.Customer;
import model.Order;
import model.OrderImpl;
import model.decorator.DiscountedOrder;
import model.decorator.ExpeditedShippingOrder;
import model.decorator.GiftWrappedOrder;
import observer.Admin;
import observer.InventoryManager;
import observer.Observer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookstoreService {
    private final InventoryManager inventoryManager;
    private Connection connection;


    public Order createOrderWithDecorators(List<Book> books, boolean applyDiscount, boolean giftWrap, boolean expediteShipping) {
        Order order = new OrderImpl(books);

        if (applyDiscount) {
            order = new DiscountedOrder(order, 10); // 10% discount
        }
        if (giftWrap) {
            order = new GiftWrappedOrder(order);
        }
        if (expediteShipping) {
            order = new ExpeditedShippingOrder(order);
        }

        System.out.println(order.getDescription() + " | Total Price: $" + order.calculateTotalPrice());
        return order;
    }

    public BookstoreService() {
        this.inventoryManager = new InventoryManager();


        Admin admin1 = new Admin("Alice");
        Customer customer1 = new Customer("Bob");

        inventoryManager.addObserver(admin1);
        inventoryManager.addObserver((Observer) customer1);
    }


    public void addBookToInventoryManager(Book book) {
        inventoryManager.addBook(book);
    }


    public void updateBookStock(Book book, int newStock) {
        inventoryManager.updateStock(book, newStock);
    }
    
    public BookstoreService(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO Books (title, author, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setDouble(3, book.getPrice());
            statement.setInt(4, book.getStock());
            statement.executeUpdate();
            System.out.println("Book added: " + book.getTitle());
        }
    }

    public Book findBookById(int bookId) throws SQLException {
        String query = "SELECT * FROM Books WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Book(
                            resultSet.getInt("book_id"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("stock")
                    );
                }
            }
        }
        return null;
    }

    public void addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO Customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.executeUpdate();
            System.out.println("Customer added: " + customer.getName());
        }
    }

    public void placeOrder(int customerId, List<Book> books) throws SQLException {
        String orderQuery = "INSERT INTO Orders (customer_id) VALUES (?)";
        String orderItemQuery = "INSERT INTO Order_Items (order_id, book_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";

        try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false); // Start transaction
            orderStatement.setInt(1, customerId);
            orderStatement.executeUpdate();

            ResultSet generatedKeys = orderStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                try (PreparedStatement orderItemStatement = connection.prepareStatement(orderItemQuery)) {
                    for (Book book : books) {
                        orderItemStatement.setInt(1, orderId);
                        orderItemStatement.setInt(2, book.getId());
                        orderItemStatement.setInt(3, 1); // Assuming quantity is 1 for simplicity
                        orderItemStatement.setDouble(4, book.getPrice());
                        orderItemStatement.executeUpdate();
                    }
                }
            }
            connection.commit();
            System.out.println("Order placed for customer ID: " + customerId);
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<Book> getInventory() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock")
                ));
            }
        }
        return books;
    }
}
