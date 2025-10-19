import java.util.*;

class MostBorrowedRecommendation implements RecommendationStrategy {
    // Very simple: recommend most borrowed books in the branch that the patron hasn't borrowed yet
    @Override
    public List<Book> recommend(LibraryBranch branch, Patron targetPatron, int maxResults) {
        Map<String, Integer> borrowCount = new HashMap<>();
        for (InventoryRecord r : branch.allInventoryRecords()) {
            borrowCount.put(r.getBook().getIsbn(), r.getBorrowedCopies());
        }
        Set<String> seen = new HashSet<>(targetPatron.getBorrowingHistory());
        List<InventoryRecord> recs = new ArrayList<>(branch.allInventoryRecords());
        recs.sort((a,b) -> Integer.compare(b.getBorrowedCopies(), a.getBorrowedCopies()));
        List<Book> out = new ArrayList<>();
        for (InventoryRecord r : recs) {
            if (out.size() >= maxResults) break;
            if (!seen.contains(r.getBook().getIsbn())) out.add(r.getBook());
        }
        return out;
    }
}
