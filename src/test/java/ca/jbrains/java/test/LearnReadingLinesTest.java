package ca.jbrains.java.test;

import io.vavr.collection.List;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
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
                endWithLineSeparator("this is one line of text without a line separator."),
                List.of("this is one line of text without a line separator."));

    }

    private void checkHowTextStreamsAsLines(String text, List<String> expectedLines) {
        Assert.assertThat(lines(text), CoreMatchers.is(expectedLines));
    }

    private String endWithLineSeparator(String text) {
        return String.format("%s%s", text, System.lineSeparator());
    }

    private List<String> lines(String blobOfText) {
        return List.ofAll(new BufferedReader(new StringReader(blobOfText)).lines());
    }
}
