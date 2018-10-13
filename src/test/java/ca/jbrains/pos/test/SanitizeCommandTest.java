package ca.jbrains.pos.test;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        return List.of(items);
    }

    private static io.vavr.collection.List<String> commands(Stream<String> commandStream) {
        return commandStream.toList();
    }

    public static io.vavr.collection.Stream<String> sanitize(String text) {
        return new RemovingWhitespaceCommandLexer().tokenize(text);
    }

    public static class RemovingWhitespaceCommandLexer {
        public io.vavr.collection.Stream<String> tokenize(String text) {
            String[] lines = text.split(System.lineSeparator());
            return io.vavr.collection.Stream.of(lines).map(String::trim);
        }
    }
}
