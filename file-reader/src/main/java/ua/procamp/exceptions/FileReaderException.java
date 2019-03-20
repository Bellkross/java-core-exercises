package ua.procamp.exceptions;

public class FileReaderException extends RuntimeException {
    public FileReaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FileReaderException(final String message) {
        super(message);
    }
}
