package ca.jbrains.pos.test;

import io.vavr.collection.List;
import org.junit.Assert;
import org.junit.Test;

public class TokenizeCommandsByRemovingWhitespaceTest {
    private CommandLexer removingWhitespaceCommandLexer = new RemovingWhitespaceCommandLexer();

    @Test
    public void removeLeadingWhitespace() throws Exception {
        Assert.assertEquals(
                List.of("::command::"),
                tokensFrom(" \t::command::"));
    }

    @Test
    public void removeTrailingWhitespace() throws Exception {
        Assert.assertEquals(
                List.of("::command::"),
                tokensFrom("::command::  \t  \f  "));
    }

    @Test
    public void gracefullyHandleEmbeddedLineBreaks() throws Exception {
        Assert.assertEquals(
                List.of("::first::", "::second::"),
                tokensFrom("::first::\n::second::"));
    }

    private List<String> tokensFrom(String text) {
        return removingWhitespaceCommandLexer.tokenize(text).toList();
    }
}
