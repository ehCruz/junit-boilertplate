package bookstoread;

import bookstoread.exceptions.BookShelfCapacityReachedException;

import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookShelf {

    private int capacity = 30;

    private List<Book> books = new ArrayList<>();

    public BookShelf() {}

    public BookShelf(int capacity) {
        this.capacity = capacity;
    }

    public List<Book> books() {
        return Collections.unmodifiableList(this.books);
    }

    public void addBook(Book... books) throws BookShelfCapacityReachedException {
        for(Book book: books) {
            if (this.books.size() == capacity) {
                throw new BookShelfCapacityReachedException(String.format("BookShelf " +
                        "capacity of %d is reached. You can't add more books.", this.capacity));
            }
            this.books.add(book);
        }
    }

    public List<Book> arrange() {
        return this.arrange(Comparator.naturalOrder());
    }

    public List<Book> arrange(Comparator<Book> criteria) {
        return this.books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public Map<Year, List<Book>> groupByPublicationYear() {
        return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
    }

    public <K> Map<K, List<Book>> groupBy(Function<Book, K> criteria) {
        return this.books.stream()
                .collect(Collectors.groupingBy(criteria));
    }

    public Progress progress() {
        int booksRead = Long.valueOf(this.books.stream().filter(Book::isRead).count()).intValue();
        int booksInProgress = Long.valueOf(this.books.stream().filter(Book::inProgress).count()).intValue();

        int percentageCompleted = booksRead * 100 / this.books.size();
        int percentageInProgress = booksInProgress * 100 / this.books.size();
        int percentageToRead = 100 - (percentageCompleted + percentageInProgress);

        return new Progress(percentageCompleted, percentageToRead, percentageInProgress);
    }

    public List<Book> findBooksByTitle(String searchTerm) {
        return this.findBooksByTitle(searchTerm, book -> true);
    }

    public List<Book> findBooksByTitle(String searchTerm, BookFilter bookFilter) {
        return this.books
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                .filter(bookFilter::apply)
                .collect(Collectors.toList());
    }
}
