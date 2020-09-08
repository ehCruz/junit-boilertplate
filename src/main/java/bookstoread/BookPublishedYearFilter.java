package bookstoread;

import java.time.LocalDate;

public class BookPublishedYearFilter implements BookFilter {

    private LocalDate startDate;
    private boolean isAfter = false;

    static BookPublishedYearFilter After(int year) {
        return build(year, true);
    }

    static BookPublishedYearFilter Before(int year) {
        return build(year, false);
    }

    private static BookPublishedYearFilter build(int year, boolean isAfter) {
        BookPublishedYearFilter filter = new BookPublishedYearFilter();
        filter.startDate = LocalDate.of(year,12,31);
        filter.isAfter = isAfter;
        return filter;
    }

    @Override
    public boolean apply(final Book book) {
        if (isAfter) {
            return book.getPublishedOn().isAfter(startDate);
        }
        return book.getPublishedOn().isBefore(startDate);
    }
}
