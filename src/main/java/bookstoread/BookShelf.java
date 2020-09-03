package bookstoread;

import java.time.Year;
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
}
