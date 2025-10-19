import java.util.List;

interface RecommendationStrategy {
    List<Book> recommend(LibraryBranch branch, Patron targetPatron, int maxResults);
}
