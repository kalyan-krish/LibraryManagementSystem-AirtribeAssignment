import java.util.List;

class BookFactory {
    public Book createBook(String isbn, String title, List<String> authors, int year, List<String> tags) {
        return new Book(isbn, title, authors, year, tags);
    }
}

