import java.time.LocalDate;
import java.util.*;

class Patron implements NotificationListener {
    private final String id;
    private String name;
    private final List<String> borrowingHistory = new ArrayList<>(); // list of ISBNs
    private final Map<String, LocalDate> currentlyBorrowed = new HashMap<>(); // isbn->borrowDate

    public Patron(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void borrowBook(String isbn) {
        currentlyBorrowed.put(isbn, LocalDate.now());
        borrowingHistory.add(isbn);
    }

    public void returnBook(String isbn) {
        currentlyBorrowed.remove(isbn);
    }

    public List<String> getBorrowingHistory() { return Collections.unmodifiableList(borrowingHistory); }
    public Map<String, LocalDate> getCurrentlyBorrowed() { return Collections.unmodifiableMap(currentlyBorrowed); }

    @Override
    public void notify(String message) {
        // In real system, push email / SMS; here we log
        System.out.println("[NOTIFICATION to " + name + "] " + message);
    }
}
