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

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType().getTypeName(), "java.util.Map<java.lang.String, bookstoread.Book>");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Map<String, Book> books = new HashMap<>();
        books.put("Java Efetivo", new Book("Java Efetivo", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8)));
        books.put("Programação em Baixo Nível", new Book("Programação em Baixo Nível", "Igor Zhirkov", LocalDate.of(2018, Month.APRIL, 12)));
        books.put("Código Limpo", new Book("Código Limpo", "Fulano de Tal", LocalDate.of(2018, Month.DECEMBER, 25)));
        books.put("Segredos do Ninja Javascript", new Book("Segredos do Ninja Javascript", "John Resig", LocalDate.of(2009, Month.MARCH, 15)));
        return books;
    }
}
