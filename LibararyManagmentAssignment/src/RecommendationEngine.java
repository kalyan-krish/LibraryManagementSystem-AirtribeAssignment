import java.util.List;

class RecommendationEngine {
    private RecommendationStrategy strategy;
    public RecommendationEngine(RecommendationStrategy strategy) { this.strategy = strategy; }
    public void setStrategy(RecommendationStrategy s) { this.strategy = s; }
    public List<Book> recommend(LibraryBranch branch, Patron p, int maxResults) { return strategy.recommend(branch, p, maxResults); }
}
