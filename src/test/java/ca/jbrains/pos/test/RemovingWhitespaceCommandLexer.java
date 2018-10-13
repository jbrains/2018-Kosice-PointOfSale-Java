package ca.jbrains.pos.test;

import io.vavr.collection.Stream;

public class RemovingWhitespaceCommandLexer implements CommandLexer {
    @Override
    public Stream<String> tokenize(String text) {
        String[] lines = text.split(System.lineSeparator());
        return Stream.of(lines).map(String::trim);
    }
}
