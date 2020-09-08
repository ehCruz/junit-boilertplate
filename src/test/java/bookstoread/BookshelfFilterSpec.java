package bookstoread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bookshelf Filter Spec")
public class BookshelfFilterSpec {

    private Book javaEfetivo;
    private Book programacaoBaixoNivel;

    @BeforeEach
    void setUp() {
        javaEfetivo = new Book("Java Efetivo", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        programacaoBaixoNivel = new Book("Programação em Baixo Nível", "Igor Zhirkov", LocalDate.of(2018, Month.APRIL, 12));
    }

    @DisplayName("Book Published Filter Spec")
    @Nested
    class BookPublishedFilterSpec {

        @DisplayName("Validate Book Published Date Post Asked Year")
        @Test
        void validateBookPublishedDatePostAskedYear() {
            BookFilter filter = BookPublishedYearFilter.After(2012);
            assertTrue(filter.apply(programacaoBaixoNivel));
        }

        @DisplayName("Validate Book Published Date Before Asked Year")
        @Test
        void validateBookPublishedDateBeforeAskedYear() {
            BookFilter filter = BookPublishedYearFilter.Before(2012);
            assertTrue(filter.apply(javaEfetivo));
        }
    }

    @DisplayName("Composite Book Filter Spec")
    @Nested
    class CompositeFilterSpec {

        @DisplayName("Composite criteria is based on multiple filters")
        @Test
        void shouldFilterOnMultipleCriteria() {
            CompositeFilter filter = new CompositeFilter();
            filter.addFilter(b -> false);
            assertFalse(filter.apply(javaEfetivo));
        }

        @DisplayName("Composite criteria invokes all filters")
        @Test
        void shouldInvokeAllFilters() {
            CompositeFilter compositeFilter = new CompositeFilter();

            BookFilter firstInvokedMockFilter = Mockito.mock(BookFilter.class);
            Mockito.when(firstInvokedMockFilter.apply(javaEfetivo)).thenReturn(true);
            compositeFilter.addFilter(firstInvokedMockFilter);

            BookFilter secondInvokedMockFilter = Mockito.mock(BookFilter.class);
            Mockito.when(secondInvokedMockFilter.apply(javaEfetivo)).thenReturn(true);
            compositeFilter.addFilter(secondInvokedMockFilter);

            assertTrue(compositeFilter.apply(javaEfetivo));
            Mockito.verify(firstInvokedMockFilter).apply(javaEfetivo);
            Mockito.verify(secondInvokedMockFilter).apply(javaEfetivo);
        }

    }
}
