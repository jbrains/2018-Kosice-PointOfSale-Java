package ca.jbrains.java;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.stream.Stream;

// CONTRACT
// Block on Reader and consume all its input as text.
// I'm not sure about things like Unicode handling. The defaults just seem to work.
// I assume that if you give BufferedReader a source of binary content, then bad
// things will happen, so I don't recommend doing that with lines().
//
// lines() returns a Stream of lines not ending in line separators (chomped).
// lines() simply returns all the lines and then stops when the source has no more content to consume.
//
// As of 2018-10-13 these tests only check for normal operations with well-behaved
// sources of text, such as coming from a StringReader. We expect the same behavior
// when connecting to stdin through an InputStreamReader.
//
// An end-of-file line separator does not create an extra empty line at the end of the stream.
//
// According to the documentation of BufferedReader, lines() might throw UncheckedIOException,
// for which we do not yet have any tests.
public class ReaderBasedTextSource {
    private BufferedReader bufferedReader;

    public ReaderBasedTextSource(Reader source) {
        this.bufferedReader = new BufferedReader(source);
    }

    // CONTRACT
    // Might throw UncheckedIOException as you work with the stream.
    public Stream<String> streamLines() {
        return bufferedReader.lines();
    }
}
