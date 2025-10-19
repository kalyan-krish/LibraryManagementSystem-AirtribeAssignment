class InventoryRecord {
    private final Book book;
    private int totalCopies;
    private int borrowedCopies;

    // In a real system we'd track copy IDs; simplified here
    public InventoryRecord(Book book) {
        this.book = book;
    }

    public Book getBook() { return book; }
    public int getTotalCopies() { return totalCopies; }
    public int getBorrowedCopies() { return borrowedCopies; }
    public int getAvailableCopies() { return totalCopies - borrowedCopies; }

    public void addCopies(int n) { totalCopies += n; }
    public void removeCopies(int n) { totalCopies -= n; if (totalCopies < 0) totalCopies = 0; }
    public void checkoutCopy() { if (getAvailableCopies() <= 0) throw new IllegalStateException("No available copies"); borrowedCopies++; }
    public void returnCopy() { if (borrowedCopies > 0) borrowedCopies--; }
}
