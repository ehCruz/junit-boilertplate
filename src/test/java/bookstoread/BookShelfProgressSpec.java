package bookstoread;

import bookstoread.exceptions.BookShelfCapacityReachedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Bookshelf Progress")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfProgressSpec {

    BookShelf shelf;
    Book javaEfetivo;
    Book programacaoBaixoNivel;
    Book segredosdoNinjaJavascript;
    Book cleanCode;

    @BeforeEach
    void setUpShelf(Map<String, Book> books) throws BookShelfCapacityReachedException {
        this.shelf = new BookShelf();
        this.javaEfetivo = books.get("Java Efetivo");
        this.programacaoBaixoNivel = books.get("Programação em Baixo Nível");
        this.cleanCode = books.get("Código Limpo");
        this.segredosdoNinjaJavascript = books.get("Segredos do Ninja Javascript");
        this.shelf.addBook(javaEfetivo, programacaoBaixoNivel, cleanCode, segredosdoNinjaJavascript);
    }

    @DisplayName("Progress should be 0% completed and 100% to read when no book is read")
    @Test
    void progess100PercentUnread() {
        Progress progress = shelf.progress();
        assertThat(progress.getCompleted()).isEqualTo(0);
        assertThat(progress.getToRead()).isEqualTo(100);
    }

    @DisplayName("Progress should be 25% completed and 75% to read when 1 book is finished and 3 were not read yet.")
    @Test
    void progressWithCompletedAndToReadPercentages() {
        this.javaEfetivo.startedReadingOn(LocalDate.of(2020, Month.JANUARY, 1));
        this.javaEfetivo.finishedReadingOn(LocalDate.of(2020, Month.JANUARY, 31));

        Progress progress = shelf.progress();
        assertThat(progress.getCompleted()).isEqualTo(25);
        assertThat(progress.getToRead()).isEqualTo(75);
    }

    @DisplayName("Progress should be 25% completed with 1 book read, 25% in progress with 1 book on reading and 50% to read , " +
            "when 2 were not read yet.")
    @Test
    void progressWithCompletedInProgressAndToReadPercentages() {
        this.javaEfetivo.startedReadingOn(LocalDate.of(2020, Month.JANUARY, 1));
        this.javaEfetivo.finishedReadingOn(LocalDate.of(2020, Month.JANUARY, 31));
        this.cleanCode.startedReadingOn(LocalDate.of(2020, Month.JULY, 31));

        Progress progress = shelf.progress();
        assertThat(progress.getCompleted()).isEqualTo(25);
        assertThat(progress.getInProgress()).isEqualTo(25);
        assertThat(progress.getToRead()).isEqualTo(50);
    }


    @DisplayName("Progress should be 0% completed with none book read, 25% in progress with 1 book in progress, " +
            "75% when 3 not read yet.")
    @Test
    void progressWithInProgressAndToReadPercentages() {
        this.cleanCode.startedReadingOn(LocalDate.of(2020, Month.JULY, 31));

        Progress progress = shelf.progress();
        assertThat(progress.getCompleted()).isEqualTo(0);
        assertThat(progress.getInProgress()).isEqualTo(25);
        assertThat(progress.getToRead()).isEqualTo(75);
    }

}
