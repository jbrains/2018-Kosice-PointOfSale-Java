package ca.jbrains.pos.test;

import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;

public class TokenizeCommandsByRemovingWhitespaceTest {
    private CommandLexer removingWhitespaceCommandLexer = new RemovingWhitespaceCommandLexer();

    @Test
    public void removeLeadingWhitespace() throws Exception {
        assertThatTextTokenizesAs(" \t::command::", List.of("::command::"));
    }

    @Test
    public void removeTrailingWhitespace() throws Exception {
        assertThatTextTokenizesAs("::command::  \t  \f  ", List.of("::command::"));
    }

    @Test
    public void gracefullyHandleEmbeddedLineBreaks() throws Exception {
        assertThatTextTokenizesAs("::first::\n::second::", List.of("::first::", "::second::"));
    }

    private void assertThatTextTokenizesAs(String text, List<String> tokens) {
        Assert.assertEquals(
                tokens,
                removingWhitespaceCommandLexer.tokenize(text).toList());
    }
}
