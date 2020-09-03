package bookstoread;

import java.util.*;
import java.util.stream.Collectors;

public class BookShelf {

    private List<String> books = new ArrayList<>();

    public List<String> books() {
        return Collections.unmodifiableList(this.books);
    }

    public void addBook(String... bookName) {
        this.books.addAll(Arrays.asList(bookName));
    }

    public List<String> arrange() {
        return this.books.stream().sorted().collect(Collectors.toList());
    }
}
