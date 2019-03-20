package ua.procamp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * {@link FileStats} provides an API that allow to get character statistic based on text file. All whitespace characters
 * are ignored.
 */
public class FileStats {

    private final Map<Character, Integer> charsHistogram;

    private FileStats(final Map<Character, Integer> charsHistogram) {
        this.charsHistogram = charsHistogram;
    }
    /**
     * Creates a new immutable {@link FileStats} objects using data from text file received as a parameter.
     *
     * @param fileName input text file name
     * @return new FileStats object created from text file
     */
    public static FileStats from(String fileName) {
        Stream<String> stringStream = openFileLinesStream(getPathFromFileName(fileName));
        Map<Character, Integer> histogram = stringStream
                .flatMapToInt(String::chars)
                .filter(c -> c != ' ')
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), summingInt(ch -> 1)));
        return new FileStats(histogram);
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a text file
     */
    public int getCharCount(char character) {
        return charsHistogram.get(character);
    }

    /**
     * Returns a character that appeared most often in the text.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        return charsHistogram.keySet().stream().max(Comparator.comparing(charsHistogram::get))
                .orElseThrow(() -> new FileStatsException("Attempt to query empty file stats!"));
    }

    /**
     * Returns {@code true} if this character has appeared in the text, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the text, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        return charsHistogram.containsKey(character);
    }

    private static Path getPathFromFileName(String fileName) {
        Objects.requireNonNull(fileName);
        URL fileUrl = FileStats.class.getClassLoader().getResource(fileName);
        try {
            if (isNull(fileUrl)) {
                throw new FileStatsException("Invalid file URL");
            }
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new FileStatsException("Invalid file URL", e);
        }
    }

    private static Stream<String> openFileLinesStream(Path filePath) {
        try {
            return Files.lines(filePath);
        } catch (IOException e) {
            throw new FileStatsException("Cannot create stream of file lines!", e);
        }
    }
}
