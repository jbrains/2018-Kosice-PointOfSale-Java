package ca.jbrains.pos.test;

import io.vavr.collection.Stream;

public interface CommandLexer {
    Stream<String> tokenize(String text);
}
