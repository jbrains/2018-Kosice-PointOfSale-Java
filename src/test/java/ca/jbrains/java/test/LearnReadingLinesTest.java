package ca.jbrains.java.test;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LearnReadingLinesTest {
    @Test
    public void readOneLineWithoutALineSeparator() throws Exception {
        Assert.assertThat(
                lines("this is one line of text without a line separator."),
                CoreMatchers.is(Arrays.asList("this is one line of text without a line separator.")));
    }

    private List<String> lines(String blobOfText) {
        return new BufferedReader(new StringReader(blobOfText)).lines().collect(Collectors.toList());
    }
}
