import java.util.*;
import java.util.logging.Logger;

class LibraryBranch {
    private final String name;
    private final Map<String, InventoryRecord> inventory = new HashMap<>(); // isbn -> record
    private final Map<String, Queue<Patron>> reservations = new HashMap<>();
    private final Logger logger = Logger.getLogger("Branch");

    public LibraryBranch(String name) { this.name = name; }
    public String getName() { return name; }

    public void addBook(Book book, int copies) {
        InventoryRecord rec = inventory.computeIfAbsent(book.getIsbn(), k -> new InventoryRecord(book));
        rec.addCopies(copies);
        logger.info(String.format("Added %d copies of %s to %s", copies, book.getIsbn(), name));
    }

    public void removeBook(String isbn) {
        inventory.remove(isbn);
        logger.info("Removed book " + isbn + " from " + name);
    }

    public List<Book> searchByTitle(String q) {
        String lower = q.toLowerCase();
        List<Book> out = new ArrayList<>();
        for (InventoryRecord r : inventory.values()) {
            if (r.getBook().getTitle().toLowerCase().contains(lower)) out.add(r.getBook());
        }
        return out;
    }
    public List<Book> searchByAuthor(String q) {
        String lower = q.toLowerCase();
        List<Book> out = new ArrayList<>();
        for (InventoryRecord r : inventory.values()) {
            for (String a : r.getBook().getAuthors()) if (a.toLowerCase().contains(lower)) { out.add(r.getBook()); break; }
        }
        return out;
    }
    public Book searchByIsbn(String isbn) { InventoryRecord r = inventory.get(isbn); return r == null ? null : r.getBook(); }

    public void checkout(String isbn, Patron p) throws LibraryException {
        InventoryRecord rec = inventory.get(isbn);
        if (rec == null) throw new LibraryException("Book not available in inventory: " + isbn);
        if (rec.getAvailableCopies() <= 0) {
            // offer reservation
            logger.info("No copies available for " + isbn + "; offering reservation");
            reserveBook(isbn, p);
            throw new LibraryException("No copies available for " + isbn + ". Patron added to reservation queue.");
        }
        rec.checkoutCopy();
        p.borrowBook(isbn);
        logger.info(p.getName() + " borrowed " + isbn + " from " + name);
    }

    public void returnBook(String isbn, Patron p) throws LibraryException {
        InventoryRecord rec = inventory.get(isbn);
        if (rec == null) throw new LibraryException("Book not in branch inventory: " + isbn);
        rec.returnCopy();
        p.returnBook(isbn);
        logger.info(p.getName() + " returned " + isbn + " to " + name);
        // if reservations exist, notify next patron
        Queue<Patron> q = reservations.get(isbn);
        if (q != null && !q.isEmpty()) {
            Patron next = q.poll();
            if (next != null) {
                next.notify(String.format("The book '%s' (ISBN %s) is now available at branch %s.", rec.getBook().getTitle(), isbn, name));
            }
        }
    }

    public void reserveBook(String isbn, Patron p) {
        reservations.computeIfAbsent(isbn, k -> new LinkedList<>()).add(p);
        logger.info(String.format("Patron %s reserved %s at %s", p.getName(), isbn, name));
        // p can be notified later when returned
    }

    public void transferTo(String isbn, LibraryBranch dest, int count) throws LibraryException {
        InventoryRecord srcRec = inventory.get(isbn);
        if (srcRec == null || srcRec.getAvailableCopies() < count) throw new LibraryException("Not enough copies to transfer");
        srcRec.removeCopies(count);
        dest.receiveTransfer(isbn, srcRec.getBook(), count);
    }

    private void receiveTransfer(String isbn, Book book, int count) {
        InventoryRecord rec = inventory.computeIfAbsent(isbn, k -> new InventoryRecord(book));
        rec.addCopies(count);
        logger.info(String.format("Received %d copies of %s at %s", count, isbn, name));
    }

    // helper for recommendation engine
    public Collection<InventoryRecord> allInventoryRecords() { return inventory.values(); }
}
