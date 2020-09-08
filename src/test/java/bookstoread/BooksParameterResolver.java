package bookstoread;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BooksParameterResolver implements ParameterResolver {

    private final Map<String, Book> books;

    public BooksParameterResolver() {
        books = this.getBooks();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType().getTypeName(), "java.util.Map<java.lang.String, bookstoread.Book>");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ExtensionContext.Store store = extensionContext.
                getStore(ExtensionContext.Namespace.create(Book.class));
        return store.getOrComputeIfAbsent("books", k -> getBooks());
    }

    private Map<String, Book> getBooks() {
        HashMap<String, Book> b = new HashMap<>();
        b.put("Java Efetivo", new Book("Java Efetivo", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8)));
        b.put("Programação em Baixo Nível", new Book("Programação em Baixo Nível", "Igor Zhirkov", LocalDate.of(2018, Month.APRIL, 12)));
        b.put("Código Limpo", new Book("Código Limpo", "Fulano de Tal", LocalDate.of(2018, Month.DECEMBER, 25)));
        b.put("Segredos do Ninja Javascript", new Book("Segredos do Ninja Javascript", "John Resig", LocalDate.of(2009, Month.MARCH, 15)));
        return b;
    }
}
