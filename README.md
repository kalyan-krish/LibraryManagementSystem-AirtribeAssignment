# LibraryManagementSystem-AirtribeAssignment

1. Functional Requirements:

a)Book Management:
    Implement a Book class with attributes such as title, author, ISBN, and publication year.

    Create a system to add, remove, and update books in the library inventory.

    Implement a search functionality to find books by title, author, or ISBN.

b)Patron Management

  Design a Patron class to represent library members.

  Implement functionality to add new patrons and update their information.

  Create a system to track patron borrowing history.

c)Lending Process

  Implement book checkout and return functionalities.

d)Inventory Management

  Keep track of available and borrowed books.


2) Additional or Optional Extension:

   a) Multi-branch Support

    Modify your system to support multiple library branches.

    Design a system for transferring books between branches.

   b) Reservation System

    Allow patrons to reserve books that are currently checked out.

    Implement a notification system for when reserved books become available.

   c) Recommendation System

    Implement a book recommendation system based on patron borrowing history and preferences.


### Design Patterns Used

* **Factory Pattern:** For creating Book and Patron objects.
* **Observer Pattern:** For notifying patrons about reserved books.
* **Strategy Pattern:** For recommendation engine.

Interaction flow and relationships

1) Book Class

  Represents a book with title, authors, ISBN, publicationYear, and optional tags.

  Supports getters, setters, and toString().

2) Patron Class

  Represents a library member.

  Tracks currentlyBorrowed books and borrowingHistory.

  Implements Observer via notify() to receive reservation notifications.

3) InventoryRecord Class

    Tracks total copies, borrowed copies, and available copies for each book in a branch.

    Provides methods for checkout, return, add/remove copies.

4) LibraryBranch Class

    Manages branch-specific inventory and reservations.

    Provides checkout(), returnBook(), reserveBook(), and transferTo() methods.

    Implements Observer pattern: notifies patrons in reservation queue when books become available.

5) Library Class

    Manages multiple branches and patrons.

    Provides high-level operations like checkoutBook(), returnBook(), and transferBook().

    Delegates branch-specific tasks to LibraryBranch.

    Factories (BookFactory & PatronFactory)

    Implements Factory Pattern for creating Book and Patron objects.

6) Recommendation Engine

    Uses Strategy Pattern: RecommendationStrategy interface with pluggable strategies.

    Example: MostBorrowedRecommendation recommends popular books not yet borrowed by a patron.

7) Reservation & Notification

Patrons can reserve unavailable books.

LibraryBranch keeps a queue of reservations per book and notifies next patron when a copy is returned.

8) Exceptions

LibraryException handles errors like missing books, branches, or patrons.

9) Logging

Uses java.util.logging to log key events: book added, checkout, return, reservation, transfer.


class Diagram:

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/f15ec1b3-e88d-4d7f-901a-9b453403e694" />






  
