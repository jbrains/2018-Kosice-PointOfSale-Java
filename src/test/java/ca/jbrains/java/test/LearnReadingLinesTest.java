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
        Assert.assertThat(
                linesFrom("this is one line of text without a line separator."),
                CoreMatchers.equalTo(List.of("this is one line of text without a line separator.")));
    }

    @Test
    public void readOneLineFromABlobOfTextEndingWithALineSeparator() throws Exception {
        Assert.assertThat(
                linesFrom(endWithLineSeparator("this is one line of text ending in a line separator.")),
                CoreMatchers.equalTo(List.of("this is one line of text ending in a line separator.")));

    }

    @Test
    public void readMultipleLinesEndingWithSeveralEmptyLines() throws Exception {
        Assert.assertThat(
                linesFrom(new StringBuilder()
                        .append(endWithLineSeparator("a non-empty line of text"))
                        .append(endWithLineSeparator("a non-empty line of text"))
                        .append(endWithLineSeparator("a non-empty line of text"))
                        .append(endWithLineSeparator(""))
                        .append(endWithLineSeparator(""))
                        .append(endWithLineSeparator(""))
                        .append(endWithLineSeparator(""))
                        .toString()),
                CoreMatchers.equalTo(List.of("a non-empty line of text", "a non-empty line of text", "a non-empty line of text", "", "", "", "")));
    }

    @Test
    public void readNothing() throws Exception {
        Assert.assertThat(
                linesFrom(""),
                CoreMatchers.equalTo(List.<String>empty()));
    }

    @Test
    public void readOnlyOneEmptyLine() throws Exception {
        Assert.assertThat(
                linesFrom(System.lineSeparator()),
                CoreMatchers.equalTo(List.of("")));
    }

    private List<String> linesFrom(String text) {
        return List.ofAll(new ReaderBasedTextSource(new StringReader(text)).parseIntoLines());
    }

    // REFACTOR Maybe move to a generic text library?
    public static String endWithLineSeparator(String text) {
        return String.format("%s%s", text, System.lineSeparator());
    }
}
