package ca.jbrains.java.test;

import ca.jbrains.java.ReaderBasedTextSource;
import io.vavr.collection.List;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

public class LearnReadingLinesTest {

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
        Assert.assertThat(
                List.ofAll(new ReaderBasedTextSource(new StringReader(text)).streamLines()),
                CoreMatchers.is(expectedLines));
    }

    // REFACTOR Maybe move to a generic text library?
    public static String endWithLineSeparator(String text) {
        return String.format("%s%s", text, System.lineSeparator());
    }
}
