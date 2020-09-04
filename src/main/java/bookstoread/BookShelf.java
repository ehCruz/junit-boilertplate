package bookstoread;

import java.time.Year;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookShelf {

    private List<Book> books = new ArrayList<>();

    public List<Book> books() {
        return Collections.unmodifiableList(this.books);
    }

    public void addBook(Book... bookName) {
        this.books.addAll(Arrays.asList(bookName));
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

        int booksToRead = this.books.size() - booksRead;
        int percentageCompleted = booksRead * 100 / this.books.size();
        int percentageInProgress = booksInProgress * 100 / this.books.size();
        int percentageToRead = 100 - (percentageCompleted + percentageInProgress);

        return new Progress(percentageCompleted, percentageToRead, percentageInProgress);
    }
}
