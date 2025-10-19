import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

class Library {
    private final String name;
    private final Map<String, LibraryBranch> branches = new HashMap<>();
    private final Map<String, Patron> patrons = new HashMap<>();
    private final Logger logger = Logger.getLogger("Library");

    public Library(String name) { this.name = name; }

    public void addBranch(LibraryBranch branch) { branches.put(branch.getName(), branch); logger.info("Added branch: " + branch.getName()); }
    public LibraryBranch getBranch(String name) { return branches.get(name); }

    public void registerPatron(Patron p) { patrons.put(p.getId(), p); logger.info("Registered patron: " + p.getName()); }
    public Patron getPatron(String id) { return patrons.get(id); }

    // High-level operations (delegates to branch)
    public void checkoutBook(String branchName, String isbn, String patronId) throws LibraryException {
        LibraryBranch b = branches.get(branchName);
        Patron p = patrons.get(patronId);
        if (b == null) throw new LibraryException("Branch not found: " + branchName);
        if (p == null) throw new LibraryException("Patron not found: " + patronId);
        b.checkout(isbn, p);
        logger.info(String.format("%s checked out %s from %s", p.getName(), isbn, branchName));
    }

    public void returnBook(String branchName, String isbn, String patronId) throws LibraryException {
        LibraryBranch b = branches.get(branchName);
        Patron p = patrons.get(patronId);
        if (b == null) throw new LibraryException("Branch not found: " + branchName);
        if (p == null) throw new LibraryException("Patron not found: " + patronId);
        b.returnBook(isbn, p);
        logger.info(String.format("%s returned %s to %s", p.getName(), isbn, branchName));
    }

    public void transferBook(String fromBranch, String toBranch, String isbn, int count) throws LibraryException {
        LibraryBranch src = branches.get(fromBranch);
        LibraryBranch dst = branches.get(toBranch);
        if (src == null || dst == null) throw new LibraryException("Branch not found");
        src.transferTo(isbn, dst, count);
        logger.info(String.format("Transferred %d copies of %s from %s to %s", count, isbn, fromBranch, toBranch));
    }
}
