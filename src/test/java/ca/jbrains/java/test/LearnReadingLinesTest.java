package ca.jbrains.java.test;

import io.vavr.collection.List;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.stream.Stream;

public class LearnReadingLinesTest {
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
    public static Stream<String> streamLinesFromSource(Reader source) {
        return new BufferedReader(source).lines();
    }

    @Test
    public void readOneLineWithoutALineSeparator() throws Exception {
        checkHowTextStreamsAsLines(
                "this is one line of text without a line separator.",
                List.of("this is one line of text without a line separator."));
    }

    @Test
    public void readOneLineFromABlobOfTextEndingWithALineSeparator() throws Exception {
        checkHowTextStreamsAsLines(
                endWithLineSeparator("this is one line of text ending in a line separator."),
                List.of("this is one line of text ending in a line separator."));

    }

    @Test
    public void readMultipleLinesEndingWithSeveralEmptyLines() throws Exception {
        checkHowTextStreamsAsLines(
                new StringBuilder()
                        .append(endWithLineSeparator("a non-empty line of text"))
                        .append(endWithLineSeparator("a non-empty line of text"))
                        .append(endWithLineSeparator("a non-empty line of text"))
                        .append(endWithLineSeparator(""))
                        .append(endWithLineSeparator(""))
                        .append(endWithLineSeparator(""))
                        .append(endWithLineSeparator(""))
                        .toString(),
                List.of("a non-empty line of text", "a non-empty line of text", "a non-empty line of text", "", "", "", "")
        );
    }

    @Test
    public void readNothing() throws Exception {
        checkHowTextStreamsAsLines("", List.empty());
    }

    @Test
    public void readOnlyOneEmptyLine() throws Exception {
        checkHowTextStreamsAsLines(System.lineSeparator(), List.of(""));
    }

    private void checkHowTextStreamsAsLines(String text, List<String> expectedLines) {
        Assert.assertThat(lines(text), CoreMatchers.is(expectedLines));
    }

    // REFACTOR Maybe move to a generic text library?
    public static String endWithLineSeparator(String text) {
        return String.format("%s%s", text, System.lineSeparator());
    }

    // REFACTOR Maybe move to a general text library?
    public static List<String> lines(String blobOfText) {
        return List.ofAll(streamLinesFromSource(new StringReader(blobOfText)));
    }
}
