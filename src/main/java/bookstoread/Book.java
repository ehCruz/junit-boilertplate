package bookstoread;

import java.time.LocalDate;
import java.util.Objects;

public class Book implements Comparable<Book> {

    private String title;
    private String author;
    private LocalDate publishedOn;
    private LocalDate startedReadingOn;
    private LocalDate finishedReadingOn;


    public Book(String title, String author, LocalDate publishedOn) {
        this.title = title;
        this.author = author;
        this.publishedOn = publishedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public int compareTo(Book book) {
        return this.title.compareTo(book.title);
    }

    public void startedReadingOn(LocalDate start) {
        this.startedReadingOn = start;
    }

    public void finishedReadingOn(LocalDate finish) {
        this.finishedReadingOn = finish;
    }

    public boolean isRead() {
        return this.startedReadingOn != null && this.finishedReadingOn != null;
    }

    public boolean inProgress() {
        return this.startedReadingOn != null && this.finishedReadingOn == null;
    }
}
