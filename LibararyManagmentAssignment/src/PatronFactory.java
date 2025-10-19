class PatronFactory {
    public Patron createPatron(String id, String name) { return new Patron(id, name); }
}