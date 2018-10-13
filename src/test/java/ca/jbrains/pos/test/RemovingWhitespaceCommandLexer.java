package ca.jbrains.pos.test;

import io.vavr.collection.Stream;

public class RemovingWhitespaceCommandLexer {
    public Stream<String> tokenize(String text) {
        String[] lines = text.split(System.lineSeparator());
        return Stream.of(lines).map(String::trim);
    }
}
