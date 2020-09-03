package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@DisplayName(value = "<= BookShelf Class Tests =>")
public class BookShelfSpec {

    BookShelf shelf;

    @BeforeEach
    void setUpShelf() {
        this.shelf = new BookShelf();
    }

    @DisplayName(value = "<= Shelf is empty when there is no book added =>")
    @Test
    void shelfEmptyWhenNoBookAdd() throws Exception {
        List<String> books = this.shelf.books();
        assertTrue(books.isEmpty(), "BookShelf should be empty.");
    }

    @DisplayName(value = "<= Shelf must conatain two books =>")
    @Test
    void shelfContainsTwoBooksWhenTwoBooksAdded() throws Exception {
        this.shelf.addBook("Java Efetivo", "Programação em Baixo Nível");
        assertEquals(2, this.shelf.books().size(), () -> "Bookshelf should contain two itens.");
    }

    @DisplayName(value = "<= Shelf must be empty if no books were added =>")
    @Test
    void emptyBookShelfWhenAddIsCalledWithoutBooks() throws Exception {
        this.shelf.addBook();
        assertTrue(this.shelf.books().isEmpty(), () -> "Book shelf must be empty.");
    }

    @DisplayName(value = "<= Books from bookshelf must be immutable for the client =>")
    @Test
    void booksReturnedFromBookshelfIsImmutableForClient() {
        this.shelf.addBook("Java Efetivo", "Programação em Baixo Nível");
        List<String> books = this.shelf.books();
        try {
            books.add("Segredos do Ninja Javascript");
            fail(() -> "Should not be able to add a new book to the books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException");
        }
    }

    @DisplayName(value = "<= Books must be arranged lexicographically by book title =>")
    @Test
    void bookshelfArrangedByBookTitle() {
        this.shelf.addBook("Harry Potter and Philosopher Stone", "O fim da Eternidade", "A máquina do tempo");
        List<String> books = this.shelf.arrange();
        assertEquals(Arrays.asList("A máquina do tempo", "Harry Potter and Philosopher Stone", "O fim da Eternidade"),
                books, () -> "Books in a bookshelf should be arranged lexicographically by book title");
    }

    @DisplayName(value = "<= Books In BookShelf Are In Insertion Order After Calling Arrange =>")
    @Test
    void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
        this.shelf.addBook("Harry Potter and Philosopher Stone", "O fim da Eternidade", "A máquina do tempo");
        this.shelf.arrange();
        List<String> books = this.shelf.books();
        assertEquals(Arrays.asList("Harry Potter and Philosopher Stone", "O fim da Eternidade", "A máquina do tempo"),
                books, () -> "Books in a bookshelf should be arranged in insertion order.");
    }
}
