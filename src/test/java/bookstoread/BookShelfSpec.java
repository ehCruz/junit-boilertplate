package bookstoread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookShelfSpec {

    @Test
    public void shelfEmptyWhenNoBookAdd() throws Exception {
        BookShelf shelf = new BookShelf();
        List<String> books = shelf.books();
        Assertions.assertTrue(books.isEmpty(), "BookShelf should be empty.");
    }
}
