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
        Assert.assertThat(
                lines("this is one line of text without a line separator."),
                CoreMatchers.is(List.of("this is one line of text without a line separator.")));
    }

    @Test
    public void readOneLineFromABlobOfTextEndingWithALineSeparator() throws Exception {
        Assert.assertThat(
                lines(
                        endWithLineSeparator("this is one line of text without a line separator.")
                ),
                CoreMatchers.is(List.of("this is one line of text without a line separator.")));

    }

    private String endWithLineSeparator(String text) {
        return String.format("%s%s", text, System.lineSeparator());
    }

    private List<String> lines(String blobOfText) {
        return List.ofAll(new BufferedReader(new StringReader(blobOfText)).lines());
    }
}
