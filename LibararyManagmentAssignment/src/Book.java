import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Book {
    private final String isbn;
    private String title;
    private List<String> authors;
    private int publicationYear;
    private List<String> tags; // optional

    public Book(String isbn, String title, List<String> authors, int year, List<String> tags) {
        this.isbn = isbn;
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.publicationYear = year;
        this.tags = tags == null ? new ArrayList<>() : new ArrayList<>(tags);
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<String> getAuthors() { return Collections.unmodifiableList(authors); }
    public void setAuthors(List<String> authors) { this.authors = new ArrayList<>(authors); }
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int year) { this.publicationYear = year; }
    public List<String> getTags() { return Collections.unmodifiableList(tags); }
    public void setTags(List<String> tags) { this.tags = new ArrayList<>(tags); }

    @Override
    public String toString() {
        return String.format("%s — %s (%d)", isbn, title, publicationYear);
    }
}