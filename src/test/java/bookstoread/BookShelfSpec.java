package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("=== BookShelf Class Tests ===")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

    BookShelf shelf;
    Book javaEfetivo;
    Book programacaoBaixoNivel;
    Book segredosdoNinjaJavascript;
    Book cleanCode;

    @BeforeEach
    void setUpShelf(Map<String, Book> books) {
        this.shelf = new BookShelf();
        this.javaEfetivo = books.get("Java Efetivo");
        this.programacaoBaixoNivel = books.get("Programação em Baixo Nível");
        this.cleanCode = books.get("Código Limpo");
        this.segredosdoNinjaJavascript = books.get("Segredos do Ninja Javascript");
    }

    @DisplayName("IsEmpty Inner Test cases")
    @Nested
    class IsEmpty {

        @DisplayName("Shelf is empty when there is no book added")
        @Test
        void shelfEmptyWhenNoBookAdd() throws Exception {
            List<Book> books = shelf.books();
            assertTrue(books.isEmpty(), "BookShelf should be empty.");
        }

        @DisplayName("Shelf must be empty if no books were added")
        @Test
        void emptyBookShelfWhenAddIsCalledWithoutBooks() throws Exception {
            shelf.addBook();
            assertTrue(shelf.books().isEmpty(), () -> "Book shelf must be empty.");
        }

    }

    @DisplayName("Books are added to the bookshelf")
    @Nested
    class BooksAreAdd {

        @DisplayName("Shelf must contain two books")
        @Test
        void shelfContainsTwoBooksWhenTwoBooksAdded() throws Exception {
            shelf.addBook(javaEfetivo, programacaoBaixoNivel);
            assertEquals(2, shelf.books().size(), () -> "Bookshelf should contain two itens.");
        }

        @DisplayName("Books from bookshelf must be immutable for the client")
        @Test
        void booksReturnedFromBookshelfIsImmutableForClient() {
            shelf.addBook(javaEfetivo, programacaoBaixoNivel);
            List<Book> books = shelf.books();
            try {
                books.add(segredosdoNinjaJavascript);
                fail(() -> "Should not be able to add a new book to the books");
            } catch (Exception e) {
                assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException");
            }
        }
    }

    @DisplayName("Books were arrenged inside the bookshelf")
    @Nested
    class BooksArrenged {
        @DisplayName("Books must be arranged lexicographically by book title")
        @Test
        void bookshelfArrangedByBookTitle() {
            shelf.addBook(programacaoBaixoNivel, javaEfetivo, segredosdoNinjaJavascript);
            List<Book> books = shelf.arrange();
            assertEquals(Arrays.asList(javaEfetivo, programacaoBaixoNivel, segredosdoNinjaJavascript),
                    books, () -> "Books in a bookshelf should be arranged lexicographically by book title");
        }

        @DisplayName("Books In BookShelf Are In Insertion Order After Calling Arrange")
        @Test
        void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
            shelf.addBook(programacaoBaixoNivel, javaEfetivo, segredosdoNinjaJavascript);
            shelf.arrange();
            List<Book> books = shelf.books();
            assertEquals(Arrays.asList(programacaoBaixoNivel, javaEfetivo, segredosdoNinjaJavascript),
                    books, () -> "Books in a bookshelf should be arranged in insertion order.");
        }

        @DisplayName("Bookshelf Arrenged By User Provided Criteria")
        @Test
        void bookshelfArrengedByUserProvidedCriteria() {
            shelf.addBook(programacaoBaixoNivel, javaEfetivo, segredosdoNinjaJavascript);
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> books = shelf.arrange(reversed);
            assertThat(books).isSortedAccordingTo(reversed);
        }

        @DisplayName("Bookshelf Arranged By Descending Date Of Publishing")
        @Test
        void bookshelfArrangedByDescendingDateOfPublishing() {
            shelf.addBook(javaEfetivo, programacaoBaixoNivel, segredosdoNinjaJavascript);
            Comparator<Book> publishedDateDesc = Comparator.comparing(Book::getPublishedOn).reversed();
            List<Book> books = shelf.arrange(publishedDateDesc);
            assertThat(books).isSortedAccordingTo(publishedDateDesc);
        }

        @DisplayName("Bookshelf Arranged By Ascending Date Of Publishing")
        @Test
        void bookshelfArrangedByAscendingDateOfPublishing() {
            shelf.addBook(javaEfetivo, programacaoBaixoNivel, segredosdoNinjaJavascript);
            Comparator<Book> publishedDateAsc = Comparator.comparing(Book::getPublishedOn);
            List<Book> books = shelf.arrange(publishedDateAsc);
            assertThat(books).isSortedAccordingTo(publishedDateAsc);
        }

    }

    @DisplayName("Books inside the bookshelf are grouped by a criteria")
    @Nested
    class BooksGroupBy {

        @DisplayName("Books inside a bookshelf are grouped by Publication Year")
        @Test
        void groupBooksInsideBookShelfByPublicationYear() {
            shelf.addBook(javaEfetivo, programacaoBaixoNivel, segredosdoNinjaJavascript, cleanCode);
            Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2018))
                    .containsValues(Arrays.asList(programacaoBaixoNivel, cleanCode));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2008))
                    .containsValues(Collections.singletonList(javaEfetivo));

            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2009))
                    .containsValues(Collections.singletonList(segredosdoNinjaJavascript));
        }

        @DisplayName("Books inside bookshelf are grouped according to user provided criteria(group by author name)")
        @Test
        void groupBooksByUserProviedCriteria() {
            shelf.addBook(javaEfetivo, programacaoBaixoNivel);
            Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);

            assertThat(booksByAuthor)
                    .containsKey("Joshua Bloch")
                    .containsValues(Collections.singletonList(javaEfetivo));

            assertThat(booksByAuthor)
                    .containsKey("Igor Zhirkov")
                    .containsValues(Collections.singletonList(programacaoBaixoNivel));
        }
    }
}
