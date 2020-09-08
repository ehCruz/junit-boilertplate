package bookstoread.log;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogginTestExecutionExceptionhandler implements TestExecutionExceptionHandler {

    private Logger logger = Logger.getLogger(LogginTestExecutionExceptionhandler.class.getName());

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        logger.log(Level.INFO, "Exception Thrown ", throwable);
        throw throwable;
    }
}
