import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("LibrarySystem");
        logger.setLevel(Level.INFO);

        // Create library and branches
        Library library = new Library("City Library");
        LibraryBranch downtown = new LibraryBranch("Downtown");
        LibraryBranch uptown = new LibraryBranch("Uptown");
        library.addBranch(downtown);
        library.addBranch(uptown);

        // Create sample books & patrons using factories
        BookFactory bookFactory = new BookFactory();
        PatronFactory patronFactory = new PatronFactory();

        Book b1 = bookFactory.createBook("978-0134685991", "Effective Java", Arrays.asList("Joshua Bloch"), 2018, Arrays.asList("programming","java"));
        Book b2 = bookFactory.createBook("978-1491950357", "Designing Data-Intensive Applications", Arrays.asList("Martin Kleppmann"), 2017, Arrays.asList("databases","systems"));
        Book b3 = bookFactory.createBook("978-0201633610", "Design Patterns", Arrays.asList("Erich Gamma","Richard Helm","Ralph Johnson","John Vlissides"), 1994, Arrays.asList("design","patterns"));

        downtown.addBook(b1, 3);
        downtown.addBook(b2, 2);
        uptown.addBook(b3, 1);

        Patron p1 = patronFactory.createPatron("P-1001", "Alice");
        Patron p2 = patronFactory.createPatron("P-1002", "Bob");
        library.registerPatron(p1);
        library.registerPatron(p2);

        // Search
        System.out.println("Search results for 'Design':");
        for (Book b : downtown.searchByTitle("Design")) System.out.println(" - " + b);

        // Checkout
        try {
            library.checkoutBook(downtown.getName(), "978-0134685991", p1.getId());
            library.checkoutBook(downtown.getName(), "978-0134685991", p2.getId());
            library.checkoutBook(downtown.getName(), "978-0134685991", p2.getId()); // third copy
        } catch (LibraryException e) {
            logger.warning(e.getMessage());
        }

        // Reserve a book that's out
        try {
            library.checkoutBook(downtown.getName(), "978-1491950357", p1.getId());
            library.checkoutBook(downtown.getName(), "978-1491950357", p2.getId()); // now all copies out
        } catch (LibraryException e) {
            logger.warning(e.getMessage());
        }

        // p1 reserves book b2
        downtown.reserveBook("978-1491950357", p1);

        // Return one copy to trigger reservation notification
        try {
            library.returnBook(downtown.getName(), "978-1491950357", p2.getId());
        } catch (LibraryException e) {
            logger.warning(e.getMessage());
        }

        // Recommendation example
        RecommendationStrategy simple = new MostBorrowedRecommendation();
        RecommendationEngine engine = new RecommendationEngine(simple);
        List<Book> recs = engine.recommend(downtown, p1, 5);
        System.out.println("Recommendations for " + p1.getName() + ":");
        for (Book r : recs) System.out.println(" * " + r.getTitle());

        // Transfer book between branches
        try {
            library.transferBook(downtown.getName(), uptown.getName(), "978-0134685991", 1);
        } catch (LibraryException e) {
            logger.warning(e.getMessage());
        }

        System.out.println("Demo finished.");
    }
}