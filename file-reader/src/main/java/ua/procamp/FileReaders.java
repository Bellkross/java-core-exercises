package ua.procamp;

import ua.procamp.exceptions.FileReaderException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

/**
 * {@link FileReaders} privides an API that allow to read whole file into a {@link String} by file name.
 */
public class FileReaders {

    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    public static String readWholeFile(String fileName) {
        Stream<String> stringStream = openFileLinesStream(getPathFromFileName(fileName));
        return stringStream.collect(Collectors.joining("\n"));
    }

    private static Path getPathFromFileName(String fileName) {
        Objects.requireNonNull(fileName);
        URL fileUrl = FileReaders.class.getClassLoader().getResource(fileName);
        try {
            if (isNull(fileUrl)) {
                throw new FileReaderException("Invalid file URL");
            }
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new FileReaderException("Invalid file URL", e);
        }
    }

    private static Stream<String> openFileLinesStream(Path filePath) {
        try {
            return Files.lines(filePath);
        } catch (IOException e) {
            throw new FileReaderException("Cannot create stream of file lines!", e);
        }
    }

}
