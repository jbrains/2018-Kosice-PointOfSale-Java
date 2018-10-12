package ca.jbrains.pos.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SanitizeCommandTest {
    @Test
    public void removeLeadingWhitespace() throws Exception {
        Assert.assertEquals(commands("::command::"), commands(sanitize(" \t::command::")));
    }

    @Test
    public void removeTrailingWhitespace() throws Exception {
        Assert.assertEquals(commands("::command::"), commands(sanitize("::command::  \t  \f  ")));
    }

    @Test
    public void gracefullyHandleEmbeddedLineBreaks() throws Exception {
        Assert.assertEquals(commands("::first::", "::second::"), commands(sanitize("::first::\n::second::")));
    }

    private static List<String> commands(String... items) {
        return Arrays.asList(items);
    }

    private static List<String> commands(Stream<String> commandStream) {
        return commandStream.collect(Collectors.toList());
    }

    private Stream<String> sanitize(String text) {
        return Stream.of(text.split(System.lineSeparator())).map(String::trim);
    }
}
